# Microserviço de Cadastro de pedidos - Java Spring
Este repositório contém a primeira etapa de um projeto de microserviços desenvolvido em Java com Spring, com foco na comunicação assíncrona entre serviços e na aplicação de boas práticas de observabilidade e documentação.
## Descrição

A API oferece endpoints para cadastrar pedidos — contendo descrição e uma lista de itens (nome e quantidade) — e para listar todos os pedidos armazenados no banco de dados. Após o cadastro, os dados são publicados em uma fila do RabbitMQ e consumidos por um segundo microserviço, responsável pelo processamento assíncrono das informações.

## Tecnologias  Utilizadas

- **Java + Spring Boot** – Framework principal da aplicação.
- **RabbitMQ** com **CloudAMQP** – Comunicação assíncrona entre serviços.
- **PostgreSQL** – Persistência dos dados.
- **Lombok** – Uso da anotação `@Slf4j` para geração de logs.
- **Springdoc OpenAPI (Swagger)** – Documentação dos endpoints da API.
- **Spring Boot Actuator** – Monitoramento da aplicação.
- Integração entre **Actuator e Swagger** para exposição de métricas via documentação da API.
- **JUnit 5 + Mockito** – Testes Unitarios

## Requisitos

- Java 21
- Maven
- PostgreSQL

## Executando o Projeto

1. Clone o repositório 1:

```bash
git https://github.com/bispobr/Spring-java-microservice-pedido.git
```
2. Clone o repositório 2:

```bash
git https://github.com/bispobr/Spring-java-microservice-processamento.git
```

3. Altere o arquivo de configuração **application.properties** com as credenciais de login do PostgreSQL e endereços Rabbitmq do seu ambiente.


## Como usar

1. Inicie a aplicação
2. A API está acessível através do endereço http://localhost:8081
3. A documentação da API está acessível através do Link http://localhost:8081/swagger-ui/index.html#/
4. O endpoint de saúde e métricas do Actuator está acessível através do Link http://localhost:8081/actuator/health

## API Endpoints
API contem os seguintes endpoints:

```http request
Post /pedidos - cadastra um novo pedido.
Content-Type: application/json
{
 "descricao": "xxxxxxx",
 "itens": [
   {
	"nome": "xxxxxx",
	"quantidade":00
   },
   {
	"nome": "xxxxxx",
	"quantidade":00
	}
  ]
}
```

| Parâmetro   | Tipo      | Descrição                           |
| :---------- |:----------| :---------------------------------- |
| `descricao` | `String`  | **Obrigatório**.  Descrição do pedido 
| `nome` | `String`  | **Obrigatório**.  nome do item
| `quantidade` | `Integer` | **Obrigatório**. Quantidade de item

```http request
GET /pedidos - retorna todos os pedidos.

```
