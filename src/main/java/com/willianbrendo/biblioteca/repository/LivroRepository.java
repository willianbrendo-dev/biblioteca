package com.willianbrendo.biblioteca.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.willianbrendo.biblioteca.model.Livro;

public interface LivroRepository extends MongoRepository<Livro, String>{

	// Método de busca customizado. O Spring Data gera a implementação automaticamente
    // apenas pelo nome do método! Isso busca Livros por ISBN.
	Livro findByIsbn(String isbn);
}
