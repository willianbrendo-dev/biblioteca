package com.willianbrendo.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
		
// --- Exibição dos Endpoints no Console ---
        
        // O caminho base que definimos no Controller é: /api/v1/biblioteca
        final String BASE_PATH = "/api/v1/biblioteca";

        System.out.println("==========================================");
        System.out.println("🚀 API da Biblioteca iniciada com sucesso!");
        System.out.println("📚 Acesse: http://localhost:8080");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("📋 Endpoints disponíveis:");
        System.out.println();
        
        System.out.println("📖 LIVROS:");
        System.out.println("GET    " + BASE_PATH + "/livros - Lista todos os livros");
        System.out.println("POST   " + BASE_PATH + "/livros - Cadastra novo livro (Body: Livro)");
        System.out.println("GET    " + BASE_PATH + "/livros/{id} - Busca livro por ID");
        // Nota: Outros endpoints (PUT, DELETE, busca customizada) exigem a expansão do Service e Controller.
        System.out.println();
        
        System.out.println("📋 EMPRÉSTIMOS:");
        System.out.println("POST   " + BASE_PATH + "/emprestimos - Realiza empréstimo (Body: { \"livroId\", \"nomeLeitor\" })");
        System.out.println("PUT    " + BASE_PATH + "/emprestimos/devolver/{livroId} - Registra devolução do livro");
        System.out.println("GET    " + BASE_PATH + "/emprestimos - Lista todos os empréstimos (Histórico)");
        System.out.println();
        
        System.out.println("==========================================");
	}

}
