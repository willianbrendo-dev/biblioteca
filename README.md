# 📚 API de Biblioteca (Spring Boot + MongoDB)

Uma API REST minimalista e bem organizada para gerenciamento de biblioteca, desenvolvida com **Spring Boot** e **MongoDB**. Este projeto é ideal para consolidar a arquitetura em camadas (Model, Repository, Service, Controller) e a aplicação de regras de negócio.

## 🚀 Funcionalidades Chave

* ✅ **Cadastro de Livros:** Permite cadastrar, listar e buscar livros por ID.
* ✅ **Controle de Empréstimos:** Registro de quem pegou o livro e a data de devolução prevista.
* ✅ **Regra de Negócio Implementada:** Não permite o empréstimo de um livro que já esteja emprestado (indisponível).

## 💡 Próximos Passos (Sugestões de Expansão)

Para aprimorar o projeto para o seu GitHub, considere adicionar estas funcionalidades:

* Implementar métodos de atualização (`PUT`) e exclusão (`DELETE`) para Livros.
* Adicionar validação de dados de entrada usando o **Jakarta Bean Validation**.
* Criar *endpoints* para listar apenas empréstimos ativos e empréstimos atrasados.

---

## 🛠️ Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3.x**
* **Spring Web** (para criar a API REST)
* **Spring Data MongoDB** (para persistência)
* **MongoDB**
* **Maven**

---

## ⚙️ Configuração e Execução

### Pré-requisitos

1.  **Java 17** ou superior instalado.
2.  **MongoDB** rodando (verifique se está acessível pela porta e URL configurada).
3.  **Maven** instalado.

### Instalação e Setup

1.  Clone o seu repositório Git:
    ```bash
    git clone <seu-repositorio>
    cd nome-do-seu-projeto
    ```
2.  Configure a conexão com o **MongoDB** no arquivo `src/main/resources/application.properties`:
    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/minha_biblioteca
    ```
    *Se estiver usando o MongoDB Atlas, substitua pela sua URI de conexão.*

3.  Execute a aplicação via Maven:
    ```bash
    mvn spring-boot:run
    ```

A API estará disponível em: **`http://localhost:8080`**

---

## 📋 Endpoints da API

O caminho base (Base Path) para todos os endpoints é: **`/api/v1/biblioteca`**

### 📖 Livros

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/livros` | Cadastra um novo livro (Body: JSON do Livro). |
| `GET` | `/livros` | Lista todos os livros cadastrados. |
| `GET` | `/livros/{id}` | Busca um livro específico pelo seu ID. |

### 📋 Empréstimos

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/emprestimos` | **Realiza um novo empréstimo**. |
| `PUT` | `/emprestimos/devolver/{livroId}` | **Registra a devolução** de um livro. |
| `GET` | `/emprestimos` | Lista o histórico completo de empréstimos. |

---

## 📝 Exemplos de Uso

### 1. Cadastrar um Livro (POST)

**`POST http://localhost:8080/api/v1/biblioteca/livros`**
```json
{
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "isbn": "978-0132350884"
}
