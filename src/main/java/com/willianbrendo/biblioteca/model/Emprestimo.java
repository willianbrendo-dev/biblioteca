package com.willianbrendo.biblioteca.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "emprestimos")
public class Emprestimo {

	// Identificador único do empréstimo.
    @Id
    private String id;

    // ID do livro que foi emprestado. Usamos o ID do Livro como referência.
    private String livroId;

    // Nome da pessoa que pegou o livro emprestado.
    private String nomeLeitor;

    // Data em que o livro foi emprestado.
    private LocalDate dataEmprestimo;

    // Data de devolução prevista.
    private LocalDate dataDevolucaoPrevista;

    // Data de devolução real (pode ser null se o livro ainda estiver emprestado).
    private LocalDate dataDevolucaoReal;

    // Construtor vazio
    public Emprestimo() {
        // Define a data de empréstimo como a data atual automaticamente.
        this.dataEmprestimo = LocalDate.now();
        // Define a devolução prevista para 7 dias após o empréstimo (Regra de Negócio de Exemplo)
        this.dataDevolucaoPrevista = this.dataEmprestimo.plusDays(7);
    }

    // Construtor principal para iniciar um novo empréstimo
    public Emprestimo(String livroId, String nomeLeitor) {
        this(); // Chama o construtor vazio para preencher as datas padrão
        this.livroId = livroId;
        this.nomeLeitor = nomeLeitor;
    }

    // --- Getters e Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLivroId() {
        return livroId;
    }

    public void setLivroId(String livroId) {
        this.livroId = livroId;
    }

    public String getNomeLeitor() {
        return nomeLeitor;
    }

    public void setNomeLeitor(String nomeLeitor) {
        this.nomeLeitor = nomeLeitor;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    // O set é útil para o Spring Data, mas em novos empréstimos, o construtor já define.
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    // Este é o setter mais importante, usado para registrar a devolução.
    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }
}
