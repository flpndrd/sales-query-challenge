# Desafio — Consulta de Vendas

## 📄 Descrição

Este projeto implementa consultas a um sistema de vendas com Spring Boot, utilizando JPQL para geração de relatórios e sumários por vendedor.

## 🎯 Objetivo do desafio

Aplicar os conceitos de consultas com Spring Data JPA e JPQL, implementando filtros dinâmicos, paginação e agrupamento de resultados.

## 🛠 Tecnologias utilizadas

- Java 25
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate
- Banco de dados H2
- JUnit
- Mockito
- AssertJ

## 📐 Modelo de Domínio

- **Sale**: `saleId`, `visited`, `deals`, `amount` (`BigDecimal`), `date`
- **Seller**: `sellerId`, `name`, `email`, `phone`
- Relacionamento: Sale ↔ Seller (ManyToOne)

## 📦 Carga Inicial do Banco de Dados

A aplicação carrega automaticamente o banco de dados com 5 vendedores e 233 vendas, de 27/06/2021 a 16/06/2026.

## 🔎 Funcionalidades

A API disponibiliza os seguintes endpoints REST:

- GET /sales/report (relatório de vendas paginado, ordenado da venda mais recente para a mais antiga)
- GET /sales/summary (sumário de vendas por vendedor, ordenado pelo nome)

O relatório retorna as vendas em `content` e os dados da página em `page` (`size`, `number`, `totalElements`, `totalPages`).

### Parâmetros opcionais

- `minDate` — data inicial (padrão: 1 ano antes da data final)
- `maxDate` — data final (padrão: data atual do sistema)
- `name` — trecho do nome do vendedor, sem diferenciar maiúsculas de minúsculas, apenas para `/report` (padrão: vazio)

As datas usam o formato `AAAA-MM-DD`.

## ⚠️ Tratamento de Exceções

Os erros seguem o formato `ProblemDetail` (RFC 9457).

- `400 Bad Request` — data em formato inválido

## 🚀 Como Executar

1. Clonar o repositório
2. Importar o projeto em sua IDE como projeto Maven
3. Executar a classe principal da aplicação (anotada com `@SpringBootApplication`) pela IDE ou via terminal:

```bash
./mvnw spring-boot:run
```

## ✅ Validação

Após executar a aplicação, o banco de dados pode ser acessado via H2 Console para verificação das tabelas e dados inseridos.

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuário: sa
- Senha: (vazia)

## 🧪 Testes

Os testes unitários cobrem as datas padrão do serviço, e os testes de integração cobrem os exemplos do desafio e a data inválida:

```bash
./mvnw clean verify
```

## 📌 Exemplos de Requisições

### Relatório de vendas (últimos 12 meses)

```http
GET /sales/report
```

### Relatório de vendas com filtros

```http
GET /sales/report?minDate=2022-05-01&maxDate=2022-05-31&name=odinson
```

Retorna as vendas 125, 126 e 128. O PDF do desafio mostra os ids 9, 10 e 12, de uma versão anterior da carga do curso.

### Sumário de vendas por vendedor (últimos 12 meses)

```http
GET /sales/summary
```

### Sumário de vendas por vendedor com filtro de período

```http
GET /sales/summary?minDate=2022-01-01&maxDate=2022-06-30
```
