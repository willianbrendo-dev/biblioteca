package com.willianbrendo.biblioteca.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.willianbrendo.biblioteca.model.Emprestimo;
import com.willianbrendo.biblioteca.model.Livro;
import com.willianbrendo.biblioteca.repository.EmprestimoRepository;
import com.willianbrendo.biblioteca.repository.LivroRepository;

@Service
public class BibliotecaService {

	// Injeção de Dependência dos Repositórios
    // O Spring se encarrega de criar e fornecer as instâncias.
    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;
    
    @Autowired // O construtor com @Autowired é o método preferido para injeção.
    public BibliotecaService(LivroRepository livroRepository, EmprestimoRepository emprestimoRepository) {
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
    }
    
    /**
     * Cadastra um novo livro.
     * @param livro O objeto Livro a ser salvo.
     * @return O Livro salvo com o ID gerado.
     */
    public Livro cadastrarLivro(Livro livro) {
        // Você pode adicionar regras, como verificar ISBN duplicado, aqui.
        if (livroRepository.findByIsbn(livro.getIsbn()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN já cadastrado.");
        }
        return livroRepository.save(livro);
    }
    
    /**
     * Busca todos os livros cadastrados.
     * @return Uma lista de Livros.
     */
    public List<Livro> buscarTodosLivros() {
        return livroRepository.findAll();
    }
    
    /**
     * Busca um livro por ID.
     * @param id O ID do livro.
     * @return O Livro encontrado.
     */
    public Livro buscarLivroPorId(String id) {
        // .orElseThrow() é um padrão Java 8 para lidar com Optionals (retorno do findById)
        // e lançar uma exceção se o objeto não for encontrado.
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado."));
    }
    
    /**
     * Realiza um empréstimo.
     * Possui a Regra de Negócio: não permitir empréstimo de livro indisponível.
     * A anotação @Transactional (do pacote springframework) garante que, se houver um erro,
     * as operações de banco de dados (Livro e Empréstimo) serão desfeitas.
     * @param livroId O ID do Livro a ser emprestado.
     * @param nomeLeitor O nome da pessoa que está pegando o livro.
     * @return O objeto Emprestimo criado.
     */
    @Transactional
    public Emprestimo realizarEmprestimo(String livroId, String nomeLeitor) {
        // 1. Busca o Livro
        Livro livro = buscarLivroPorId(livroId);

        // 2. Aplica a Regra de Negócio: Não pode emprestar se indisponível.
        if (!livro.isDisponivel()) {
            // Lança um erro HTTP 409 Conflict, indicando que o estado atual não permite a operação.
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O livro '" + livro.getTitulo() + "' não está disponível para empréstimo.");
        }

        // 3. Verifica se já existe um empréstimo ATIVO para o Livro.
        // É uma checagem redundante, mas mais robusta.
        Emprestimo emprestimoAtivo = emprestimoRepository.findByLivroIdAndDataDevolucaoRealIsNull(livroId);
        if (emprestimoAtivo != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O livro já possui um empréstimo ATIVO (ID: " + emprestimoAtivo.getId() + ").");
        }

        // 4. Cria o novo Empréstimo
        Emprestimo novoEmprestimo = new Emprestimo(livroId, nomeLeitor);
        Emprestimo emprestimoSalvo = emprestimoRepository.save(novoEmprestimo);

        // 5. Atualiza o status do Livro para indisponível
        livro.setDisponivel(false);
        livroRepository.save(livro); // Salva o Livro atualizado

        return emprestimoSalvo;
    }
    
    /**
     * Registra a devolução de um livro.
     * @param livroId O ID do livro devolvido.
     * @return O Emprestimo atualizado com a data de devolução real.
     */
    @Transactional
    public Emprestimo registrarDevolucao(String livroId) {
        // 1. Busca o empréstimo ativo para o livro.
        Emprestimo emprestimo = emprestimoRepository.findByLivroIdAndDataDevolucaoRealIsNull(livroId);

        if (emprestimo == null) {
            // Se não houver empréstimo ativo, algo está errado.
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não há empréstimo ativo para o Livro com ID: " + livroId);
        }

        // 2. Registra a data de devolução real.
        emprestimo.setDataDevolucaoReal(LocalDate.now());
        Emprestimo emprestimoAtualizado = emprestimoRepository.save(emprestimo);

        // 3. Atualiza o status do Livro para disponível.
        Livro livro = buscarLivroPorId(livroId);
        livro.setDisponivel(true);
        livroRepository.save(livro);

        return emprestimoAtualizado;
    }
    
    /**
     * Busca todos os empréstimos (ativos e inativos)
     * @return Uma lista de Empréstimos.
     */
     public List<Emprestimo> buscarTodosEmprestimos() {
        return emprestimoRepository.findAll();
    }
}
