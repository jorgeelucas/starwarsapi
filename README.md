
  
# starwars-api

API para desafio


## Arquitetura

* Maven
* Java 11
* Spring Boot
* Spring Data
* Spring JPA
* jUnit4
* Mockito
* H2

## Execução

### Clone

```console
git clone https://github.com/jorgeelucas/starwarsapi.git
cd starwarsapi
```

### Construção

Para construir o projeto com o Maven, executar os comando abaixo:

```shell
mvn clean install
```

O comando irá baixar todas as dependências do projeto e criar um diretório *target* com os artefatos construídos, que incluem o arquivo jar do projeto. Além disso, serão executados os testes unitários, e se algum falhar, o Maven exibirá essa informação no console.

### Execução

Para executar o projeto com o  **Maven Spring Boot Plugin**, executar os comando abaixo:

```shell
mvn spring-boot:run
```

O comando irá rodar o projeto e subir na porta **8080**

### Testes

```console
mvn test
```
## Requisições
- Todas as requisições estão documentadas no swagger. Após inicialização do projeto acesse o endereço: http://localhost:8080/swagger-ui.html
- Além disso, foi anexado junto ao projeto um COLLECTION do postman com todas as requisições e rotas, basta importar.
 
- Por favor, atentar a todas as observações das requisições.


<details><summary><b>Rotas dos rebeldes (Clique aqui)</b></summary>

> Utiliza-se o estilo RESTFull portanto todos os caminhos partem do path: **/api/v1/rebeldes**

1. Caminho=/ , Metodo=**POST**
	```
	Body:
	```	
    ```json
      {
        "nome": "string",
		"idade": 0,
		"genero": "FEMININO",
		"localizacao": {
			"latitude": "string",
			"longitude": "string",
			"base": "string"
		},
		"inventario": {
		"itensDeInventario": [
			{
				"item": {
					"id": "string"
				},
				"quantidade": 0
			},{
				"item": {
					"id": "string"
				},
				"quantidade": 0
			}]
		}
      }
    ```
    >    Obs.: Os itens do rebelde devem estar previamente cadastrados, foi feito 		dessa forma para um melhor controle de cada item e dos seus valores.
	>    
	>    Obs.: O genero foi escolhido um ENUM para representa-lo, então ele espera os valores (**MASCULINO**, **FEMININO**). Caso informe um tipo diferente desses gerará um erro inesperado.
	
3. caminho=*/localizacao/{id do rebelde}*, Método=**PUT**
	```
	Body:
	```	
    ```json
      {
        "latitude": "string",
		"longitude": "string",
		"base": "string"
      }
    ```
4. caminho=*/acusar-traidor?traidor={id do acusado}*, Método=[**PATCH**]
	```
	Acusa um rebelde de traição!!! NENHUM BODY E NECESSARIO
	```	
5. Caminho=*/negociar*, Método=**POST**
    ```json
      {
        "rebelde1": {
		"idRebelde": "STRING",
		"itensDeInventario": [
			{
				"item": {
					"id": "STRING",
					"nome": "Municao",
					"pontos": 3
				},
				"quantidade": 2
			}]
		},
		"rebelde2": {
			"idRebelde": "mateo",
			"itensDeInventario": [
			{
				"item": {
					"id": "STRING",
					"nome": "Comida",
					"pontos": 1
				},
				"quantidade": 6
			}]
		}
      }
    ```

</details>

<details><summary><b>Rotas dos relatórios (Clique aqui)</b></summary>

> Utiliza-se o estilo RESTFull portanto todos os caminhos partem do path: **/api/v1/relatorios**

1. /porcentagem-traidores [**GET**]
    ```
    Gera um json para possível relatório de porcentagem de traidores
    ```
3. /quantidade-recursos-por-rebelde [**GET**]
	```
	Gera um relatório com a quantidade de recursos por rebelde
	```
4. /pontos-perdidos [**GET**]
	```
	Gera o relatório da quantidade de pontos perdidos por causa de TRAIDORES!!!
	```	
</details>
<details><summary><b>Rotas dos itens (Clique aqui)</b></summary>

> Utiliza-se o estilo RESTFull portanto todos os caminhos partem do path: **/api/v1/itens**

1. Caminho=/, Método=**POST**
    ```
    Cadastra e retorna um item cadastrado com id;
    ```
    ```json
      {
        "nome": "string",
		"pontos": 0
      }
    ```
3. Caminho=/ [**GET**]
	```
	Obtem todos os itens cadastrados. NENHUM BODY E NECESSARIO
	```
</details>

## Detalhes


1. Para comunicação foi usado a arquitetura REST, baseado no Restful. Os serviços recebem e respondem JSON

2. Para banco de dados foi usado banco em memória h2, tanto para o projeto quanto para os testes.

3. Na documentação foi pensado no swagger por ser uma ferramenta de facil implementação e usabilidade.

4. Para testes foi usado Junit4, fazendo testes diretamente nos endpoints, testes de integração, para tentar simular mais uma chamada e as respostas mockados.

5. Nas responstas foi escolhido o objeto ResponseEntity do spring para gerenciar todo o objeto da resposta.

6. Para testes eu estava usando o INSOMNIA com os endpoints informados acima.

>Obrigado pela oportunidade :)
