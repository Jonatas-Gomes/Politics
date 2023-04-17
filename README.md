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
- ModelMapper
- Lombok
- MongoDB
- MySQL
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


  
   
