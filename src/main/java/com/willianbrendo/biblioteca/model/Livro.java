package com.willianbrendo.biblioteca.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "livros")
public class Livro {

	// A anotação @Id define o campo como o identificador único do documento no MongoDB.
    // O tipo String é comum para IDs do MongoDB (ObjectId).
    @Id
    private String id;

    // Atributos do Livro
    private String titulo;
    private String autor;
    private String isbn; // International Standard Book Number - bom para unicidade

    // Novo campo para controlar se o livro está disponível para empréstimo.
    private boolean disponivel;

    // Construtor vazio (necessário para o Spring Data/MongoDB)
    public Livro() {
        // Por padrão, um novo livro está disponível.
        this.disponivel = true;
    }

    // Construtor com campos (opcional, mas útil)
    public Livro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponivel = true;
    }

    // --- Getters e Setters ---
    // Métodos para acessar e modificar os atributos.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
