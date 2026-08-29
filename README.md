# Order Service

Microsserviço responsável pelo cadastro de pedidos e pela publicação das informações para processamento assíncrono.

O serviço faz parte de um conjunto de microsserviços desenvolvido com Java e Spring Boot, utilizando RabbitMQ para comunicação assíncrona.

## Arquitetura

Fluxo simplificado:

```text
Cliente
   │
   ▼
Order Service
   │
   │ mensagem
   ▼
RabbitMQ
   │
   ▼
Processing Service
```

Após o cadastro de um pedido, as informações são publicadas em uma fila RabbitMQ para consumo pelo serviço de processamento.

## Responsabilidades

- Cadastrar pedidos
- Listar pedidos
- Validar os dados recebidos
- Publicar pedidos para processamento assíncrono
- Disponibilizar documentação da API
- Disponibilizar informações de saúde e métricas da aplicação

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring AMQP
- RabbitMQ / CloudAMQP
- PostgreSQL
- H2
- Springdoc OpenAPI
- Spring Boot Actuator
- JUnit 5
- Mockito

## Requisitos

- Java 21
- Maven
- PostgreSQL
- RabbitMQ ou CloudAMQP

## Configuração

As configurações de banco de dados e mensageria podem ser fornecidas por variáveis de ambiente.

| Variável | Descrição | Exemplo |
|---|---|---|
| `DB_URL` | URL de conexão com o PostgreSQL | `jdbc:postgresql://localhost:5432/microservice-pedido` |
| `DB_USERNAME` | Usuário do PostgreSQL | `postgres` |
| `DB_PASSWORD` | Senha do PostgreSQL | `senha` |
| `RABBITMQ_ADDRESSES` | Endereço do RabbitMQ ou CloudAMQP | `amqps://...` |
| `RABBITMQ_PROCESSING_QUEUE` | Nome da fila utilizada pelo serviço de processamento | `...` |

Os valores devem ser configurados de acordo com o ambiente utilizado.

## Executando

Clone o repositório:

```bash
git clone https://github.com/bispobr/Spring-java-microservice-pedido.git
cd Spring-java-microservice-pedido
```

Compile e execute:

```bash
./mvnw clean package
./mvnw spring-boot:run
```

A aplicação utiliza a porta `8081`.

## API

### Criar pedido

```http
POST /pedidos
Content-Type: application/json
```

Exemplo:

```json
{
  "descricao": "Pedido de exemplo",
  "itens": [
    {
      "nome": "Produto A",
      "quantidade": 2
    },
    {
      "nome": "Produto B",
      "quantidade": 1
    }
  ]
}
```

### Listar pedidos

```http
GET /pedidos
```

Retorna os pedidos cadastrados.

## Documentação da API

Com a aplicação em execução:

```text
http://localhost:8081/swagger-ui/index.html
```

## Actuator

Endpoint de saúde:

```text
http://localhost:8081/actuator/health
```

O Actuator também disponibiliza métricas da aplicação.

## Testes

Execute:

```bash
./mvnw test
```

## Serviços relacionados

- [User Service](https://github.com/bispobr/Spring-java-microservice-usuario)
- [Processing Service](https://github.com/bispobr/Spring-java-microservice-processamento)
- [Email Service](https://github.com/bispobr/Spring-java-microservice-email)

## Status

Projeto de estudo desenvolvido para praticar desenvolvimento de APIs REST com Spring Boot, persistência com PostgreSQL e comunicação assíncrona utilizando RabbitMQ.
