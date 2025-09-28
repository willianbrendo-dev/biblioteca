package com.willianbrendo.biblioteca.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.willianbrendo.biblioteca.model.Emprestimo;

public interface EmprestimoRepository extends MongoRepository<Emprestimo, String>{

	// Busca empréstimos ativos (onde a dataDevolucaoReal é nula) para um Livro específico.
    // O Spring Data faz a busca: (livroId = ?0) AND (dataDevolucaoReal IS NULL).
    Emprestimo findByLivroIdAndDataDevolucaoRealIsNull(String livroId);
}
