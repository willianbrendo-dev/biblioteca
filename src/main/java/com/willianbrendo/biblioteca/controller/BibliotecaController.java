package com.willianbrendo.biblioteca.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.willianbrendo.biblioteca.model.Emprestimo;
import com.willianbrendo.biblioteca.model.Livro;
import com.willianbrendo.biblioteca.service.BibliotecaService;

@RestController
@RequestMapping("/api/v1/biblioteca")
public class BibliotecaController {

	private final BibliotecaService service;

    @Autowired
    public BibliotecaController(BibliotecaService service) {
        this.service = service;
    }
    
    // POST /api/v1/biblioteca/livros
    // Cadastra um novo livro. O corpo da requisição JSON é mapeado para o objeto Livro.
    @PostMapping("/livros")
    public ResponseEntity<Livro> cadastrarLivro(@RequestBody Livro livro) {
        // Chama o método do serviço e retorna a resposta com status HTTP 201 (Created).
        Livro novoLivro = service.cadastrarLivro(livro);
        return new ResponseEntity<>(novoLivro, HttpStatus.CREATED);
    }
    
    // GET /api/v1/biblioteca/livros
    // Busca todos os livros.
    @GetMapping("/livros")
    public List<Livro> getTodosLivros() {
        return service.buscarTodosLivros();
    }
    
    // GET /api/v1/biblioteca/livros/{id}
    // Busca um livro por ID. O {id} na URL é capturado pelo @PathVariable.
    @GetMapping("/livros/{id}")
    public Livro getLivroPorId(@PathVariable String id) {
        return service.buscarLivroPorId(id);
    }
    
    // POST /api/v1/biblioteca/emprestimos
    // Realiza o empréstimo de um livro. O corpo da requisição espera um JSON com { "livroId": "...", "nomeLeitor": "..." }.
    @PostMapping("/emprestimos")
    public ResponseEntity<Emprestimo> realizarEmprestimo(@RequestBody Map<String, String> request) {
        String livroId = request.get("livroId");
        String nomeLeitor = request.get("nomeLeitor");

        // Chama o serviço. Se a regra de negócio falhar (livro indisponível), o serviço lançará uma exceção HTTP.
        Emprestimo novoEmprestimo = service.realizarEmprestimo(livroId, nomeLeitor);
        return new ResponseEntity<>(novoEmprestimo, HttpStatus.CREATED);
    }
    
    // PUT /api/v1/biblioteca/emprestimos/devolver/{livroId}
    // Registra a devolução de um livro.
    @PutMapping("/emprestimos/devolver/{livroId}")
    public Emprestimo registrarDevolucao(@PathVariable String livroId) {
        return service.registrarDevolucao(livroId);
    }
    
 // GET /api/v1/biblioteca/emprestimos
    // Busca todos os empréstimos (histórico)
    @GetMapping("/emprestimos")
    public List<Emprestimo> getTodosEmprestimos() {
        return service.buscarTodosEmprestimos();
    }
}
