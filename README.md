# MICROSERVIÇOS ASSOCIATE E PARTY
## INFORMAÇÕES IMPORTANTES
 - Para plena execução dos microserviços, é necessário inicializar o servidor eureka, ambos os microserviços, além do conteiner do Kafka. 
 - A arquitetura hexagonal foi utilizada no projeto.
 - URI base microserviço Party: http://localhost:8081/ 
 - URI base microserviço Associate: http://localhost:8082/ 

### Swagger
[Swagger - MS-ASSOCIATE](https://github.com/Jonatas-Gomes/Politics/blob/master/associate/src/main/resources/openapi-associate.yaml) 

[Swagger - MS-PARTY](https://github.com/Jonatas-Gomes/Politics/blob/master/party/src/main/resources/openapi-party.yaml) 

### Postman Collection
[Coleção - MS-ASSOCIATE](https://github.com/Jonatas-Gomes/Politics/blob/master/associate/src/main/resources/Microservice%20Associate.postman_collection.json) 

[Coleção - MS-PARTY](https://github.com/Jonatas-Gomes/Politics/blob/master/party/src/main/resources/Microservice%20Party.postman_collection.json) 

## TECNOLOGIAS UTILIZADAS NO PROJETO
- Kafka 
- OPEN-FEIGN 
- Eureka Server
- ModelMapper
- Lombok
- PostGre
- Junit 5 
- Postman
- Spring Boot
- Docker - Containers contendo o kafka, o kafkadrop e o zookpeer

### Configuração Docker Compose
- Arquivo docker está localizado na pasta raiz do projeto.
  ![KafkaContainerConfiguration](https://user-images.githubusercontent.com/57242457/215167206-2ef78767-37fe-40a1-b0df-69cfee53eef7.png)
  <img width="660" alt="dockeri" src="https://user-images.githubusercontent.com/57242457/232541704-e6a17ff7-816f-4836-93a2-d427e64fd69c.png">
 ### Inicialização dos containers
  <img width="660" alt="dockeri" src="https://user-images.githubusercontent.com/57242457/232541704-e6a17ff7-816f-4836-93a2-d427e64fd69c.png">
  
  ## Ms-Party
   - Responsável pelo cadastro e gerenciamento dos partídos políticos, bem como afiliações aos partídos.
 ### Endpoints 
 - #### GET 
  ``` curl --location 'localhost:8081/parties'```
-  #### GET Response
    <img width="608" alt="image" src="https://user-images.githubusercontent.com/57242457/232546599-e9ff7be6-e7e0-4c89-a831-960edd54db2d.png">
- #### GET ID
 ```
  curl --location 'localhost:8081/parties/p16820'
  
 ```
- #### GET ID Response
 <img width="397" alt="image" src="https://user-images.githubusercontent.com/57242457/232554746-9392b43f-9fd3-4a40-a24f-a42186062c8f.png">
 
- #### POST
   
 ```
 curl --location 'http://localhost:8081/parties' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data '{
  "partyName": "Partido Verde",
  "acronym": "PV",
  "ideology": "Right",
  "foundationDate": "2002-08-09"
}'
  
 ```
<img width="637" alt="image" src="https://user-images.githubusercontent.com/57242457/232550693-177f4047-1d4b-4f4b-b211-fb432316b73a.png">
 
- #### POST Response
 <img width="636" alt="image" src="https://user-images.githubusercontent.com/57242457/232549809-54c0737d-df2e-4039-9db7-52b03c273b8f.png">

- #### PUT
      
 ```
 curl --location --request PUT 'localhost:8081/parties/p09918' \
--header 'Content-Type: application/json' \
--data '{
    "partyName": "Partido Comunista do Brasil",
    "acronym": "PCdoB",
    "ideology": "Left",
    "foundationDate": "2022/04/25"
}'
  
 ```
 
 <img width="631" alt="image" src="https://user-images.githubusercontent.com/57242457/232551995-2edb015a-1212-4ea5-bef9-7e8fe7f774e0.png">
 
- #### PUT Response
 <img width="620" alt="image" src="https://user-images.githubusercontent.com/57242457/232552992-a68fd10b-ef97-4826-941b-64ce1437b07b.png">
 
 - #### DELETE
 ```
 curl --location --request DELETE 'localhost:8081/parties/p90627'

````
- #### GET ALL ASSOCIATES
 ```
 curl --location 'localhost:8081/parties/p16820/associates'
```` 
- #### GET ALL ASSOCIATES RESPONSE
 <img width="466" alt="image" src="https://user-images.githubusercontent.com/57242457/232556794-fccf3219-9354-41e5-9794-ff2e534faec8.png">
 
## MS- ASSOCIATE
- Responsável pelo cadastro e gerenciamento de associados, bem como solicitação de afiliação a um partido politico.
### ENDPOINTS

- #### GET
 ```
  curl --location 'localhost:8082/associates'
 ```
- #### GET RESPONSE
<img width="623" alt="image" src="https://user-images.githubusercontent.com/57242457/232560158-d2def8de-a84d-4dbf-b227-79bd3567316a.png">

- #### POST
 ```
 curl --location 'localhost:8082/associates' \
--header 'Content-Type: application/json' \
--data '{
    "fullName": "Mario Ferreira",
    "politicalOffice":"President",
    "birthday": "1998/12/31",
    "sex": "Male"
}'
 
 ```
 - #### POST RESPONSE
  <img width="621" alt="image" src="https://user-images.githubusercontent.com/57242457/232562941-66dba4da-cfe1-49a4-a341-0a5f842c8ac1.png">

- #### PUT
 ```
 curl --location --request PUT 'localhost:8082/associates/55' \
--header 'Content-Type: application/json' \
--data '{
    "fullName": "Maria da Conceição Júlia de Jesus",
    "politicalOffice":"Senator",
    "birthday": "1959-12-08",
    "sex": "Female"
}'
 
 ```
<img width="627" alt="image" src="https://user-images.githubusercontent.com/57242457/232563470-920cedbd-5b62-42ae-a705-304b52519a11.png">

- #### PUT RESPONSE
<img width="605" alt="image" src="https://user-images.githubusercontent.com/57242457/232563573-2998c571-fb78-41dd-b2a7-1fbf0405cc21.png">

- #### GET ID
 ```
 curl --location 'localhost:8082/associates/51'
 ```
- #### GET ID RESPONSE
<img width="584" alt="image" src="https://user-images.githubusercontent.com/57242457/232564034-8a07409a-b1b4-4a7f-a843-391ef208a115.png"> 

- #### DELETE
 ```
  curl --location --request DELETE 'localhost:8082/associates/13'
 ```
 - #### GET PARTIES
 ```
  curl --location 'localhost:8082/associates/parties'
 ```
 - #### GET PARTIES RESPONSE
 <img width="584" alt="image" src="https://user-images.githubusercontent.com/57242457/232565375-00d49cdb-b677-45eb-b836-39c023759ede.png"> 
 
 - #### POST BIND ASSOCIATE
  ```
 curl --location 'localhost:8082/associates' \
--header 'Content-Type: application/json' \
--data '{
    "fullName": "João Gomes",
    "politicalOffice":"President",
    "birthday": "1998/12/31",
    "sex": "Male"
}'
  
  ```
  <img width="652" alt="image" src="https://user-images.githubusercontent.com/57242457/232566578-2487e449-94d7-48fc-9c61-6907ca8377c5.png">
 
- #### POST BIND ASSOCIATE RESPONSE
<img width="590" alt="image" src="https://user-images.githubusercontent.com/57242457/232566461-eec6ca02-a24b-426b-a76b-d2b1991c0144.png">

- #### DELETE
```
curl --location --request DELETE 'localhost:8082/associates/51/parties/p16820'

```
 
