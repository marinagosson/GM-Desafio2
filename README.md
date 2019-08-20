# GM-Desafio2

Criar uma aplicação que realize importação de arquivos.

Gerentes de projeto poderão importar dados do seu time de desenvolvimento, e a partir daí manipulá-los.

### A API deve conter endpoints para realizar as seguintes ações:

1. Usuários
1.1 Cadastro de usuários

2. Projetos
2.1 Cadastro de Projetos
2.2 Listar projetos (Contendo os usuários envolvidos)
2.3 Arquivar projetos

3. Associação de usuários a projetos

4. Importação de projetos
4.1 Importação de projetos através de arquivos CSV

### Considerações:

* Cada projeto só poderá ter um Gerente de projetos.
* Durante a atribuição de um usuário a um projeto é necessário informar a função que será exercida.
* Aplicação client-side não é um requisito, mas se quiser fazer, fique a vontade.
* Deverão ser utilizados: Spring Boot e Spring Batch.
* Você poderá definir a flexibilidade e as limitações dessa API de consulta.
* Você pode utilizar qualquer base de dados e qualquer solução de cache.
* Consistência eventual não é um problema.
* Testes são extremamente importantes.

## Vamos lá ...

Para rodar o projeto é necessário criar um banco local MySql, com o nome do banco desafio2 e sem senha.
O projeto foi desenvolvido em Java, utilizando a ferramenta do eclipse. Lembre-se de configurar a máquina para realizar teste. 

Para realizar testes com outros arquivos csv, basta você inserir outro arquivo dentro de "resources/data" com seguinte nome: "gm-challenge.csv".

O projeto só ler um arquivo por vez, então sempre que você quiser alterar o arquivo basta parar o projeto e rodar novamente.

A porta de configuração é a padrão 8080, a URL de acesso fica: localhost:8080. 

# Documentação da API:

## Employee

### POST http://localhost:8080/employee

Criar um usuário e associar a um time

Body: 

``` 
{
	"name" : "Marina de Oliveira Gosson", 
	"email" : "marina.gosso1n@gmail.com", 
	"team" : {
		"id" : 2
	}
}
```

### GET http://localhost:8080/employees

Listar os usuários criados

Result:

```
[
    {
        "id": 1,
        "name": "Juan Carlos",
        "email": "juan.carlos@gmail.com",
        "team": null,
        "skills": [
            {
                "id": 24,
                "name": "XP"
            },
            {
                "id": 23,
                "name": "ITIL"
            },
            {
                "id": 22,
                "name": "Scrum"
            }
        ]
    }
]
	
 ```
 
 ## Project
 
 ### POST http://localhost:8080/project
 
 Criar um projeto. PM é o gerente de projeto e Employees os demais integrantes do time.
 
 Body:
 
 ```
 {
    "projectName": "Track Company X",
    "plannedStart": "20/08/2019", 
    "plannedEnd": "20/08/2019",
    "pm": {
    	"id":1
    }, 
		"employees":[{
    	"id":2
    }]

}
 ```
 
 ### GET http://localhost:8080/projects

Listar os projetos

Result:

```
[
{
        "id": 67,
        "projectName": "Track Company X",
        "plannedStart": "20/08/2019",
        "plannedEnd": "20/08/2019",
        "archived": false,
        "pm": {
            "id": 1,
            "name": "Juan Carlos",
            "email": "juan.carlos@gmail.com",
            "team": null,
            "skills": [
                {
                    "id": 24,
                    "name": "XP"
                },
                {
                    "id": 23,
                    "name": "ITIL"
                },
                {
                    "id": 22,
                    "name": "Scrum"
                }
            ]
        },
        "employees": [
            {
                "id": 2,
                "name": "Mr. Peanut Butter",
                "email": "butter@gmail.com",
                "team": {
                    "id": 59,
                    "name": "Quality Assurance"
                },
                "skills": [
                    {
                        "id": 22,
                        "name": "Scrum"
                    },
                    {
                        "id": 23,
                        "name": "ITIL"
                    },
                    {
                        "id": 24,
                        "name": "XP"
                    }
                ]
            }
        ]
    }
]
```
 
 ### PATCH http://localhost:8080/project/20
 
 Atualizar um projeto. Caso queira arquivar por exemplo.
 
 Body:
 
 ```
{
   "archived":true
}
 ```
 
 ## Skill
 
 ### POST http://localhost:8080/skill
 
 Criar uma habilidade.
 
 Body:
 
 ``` 
{
	"name": "AWS"
}
 ```
 
 ### GET http://localhost:8080/skills
 
Listar habilidades

Result:

```
[
    {
        "id": 19,
        "name": "E2E Tests"
    },
    {
        "id": 20,
        "name": "Functional Tests"
    },
    {
        "id": 21,
        "name": "API Integration Tests"
    }
]
```

## Team

### GET http://localhost:8080/teams
Listar os times.

Result:

```
[
    {
        "id": 59,
        "name": "Quality Assurance"
    },
    {
        "id": 60,
        "name": "Algorithm"
    }
]

```

### POST http://localhost:8080/team

Criar time

Body:

```

{
	"name": "Reports"
}

```

## Link do POSTMAN: 
https://www.getpostman.com/collections/cc22281dc2eec5813330






