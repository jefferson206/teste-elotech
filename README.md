# teste-elotech
Back-end em Java de teste para a Elotech

### Objetivo

* Criar uma API Rest de um cadastro de Pessoas, utilizando Spring Boot e Java

### Requisitos:

* Possuir ao menos os endpoints: GET(Buscar uma única Pessoa), GET (Busca paginada opção de filtro para retornar várias pessoas), POST, PUT, DELETE
* O cadastro de pessoa deve ter os campos: Id, Nome, CPF, Data de nascimento.
* A pessoa deve possuir uma lista de contatos (relacionamento um para muitos) com os campos: Id, Nome, Telefone e Email.
* Os dados devem ser persistidos utilizando um banco de dados relacional.

### Validações:

* Todos os campos são obrigatórios, tanto da pessoa como do contato
* A Pessoa deve possuir ao menos um contato
* O CPF deve ser um CPF válido
* A Data de nascimento não pode ser uma data futura
* Validar sintaxe do email do contato

## Requisitos técnicos:

* Deverão ser criados testes unitários
* Publicar o código em repositório público

### É opcional e será um diferencial:

* Implementar o front-end para consumir a API.
* Desejável que seja em ReactJS ou Angular
* Publicar a aplicação na internet utilizando algum provedor, para que possa ser acessado sem necessidade de rodar o projeto local


#### URL para Postman 
> http://localhost:8080/teste-elotech
#### URIs 
> /pessoas

> /contatos

#### Exemplo de Post Contato
    {    
        "nome": "Jefferson",
        "telefone": "4432633322",
        "email": "a@a.com.br"
    }

#### Exemplo de Post Pessoa
    {
        "nome": "Teste",
        "cpf": "53108328077",
        "dataDeNascimento": "04/02/2000",
        "contato": [
            {
                "id": 3,
                "nome": "Jefferson",
                "telefone": "4432633322",
                "email": "a@a.com.br"
            }
        ]
    }
    
