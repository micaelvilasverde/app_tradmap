API Rest para o app TradeMapClone destinado a Mentoria da Digital Inovation One
API para simular funcionalidades do TradeMap com integração ao Kafka para mostrar em tempo real valores das ações da B3.

Tecnologias utilizadas na API:

Spring Boot 2
Spring Data
Spring for Kafka
PostgreSQL
Aplicações envolvidas:

API Rest: escrita em Java e Spring Boot com endpoints para simular funcionalidades do TradeMap
API para coletar ações da B3: escrita em Python com Flask utilizando a lib do Yahoo Finances (https://finance.yahoo.com/)
Docker:
Arquivo do docker compose para subir o broker Kafka
Arquivo do docker compose para subir o Postgres
Arquivo do docker compose para subir a API em Flask


Arquitetura do Projeto  
![arquitetura-projeto](https://github.com/user-attachments/assets/ed216b17-be62-4faf-8b10-87cf16c1ce15)

Como executar a aplicação
Broker do Kafka (dentro da pasta arquivos-docker):
$ docker-compose -f docker-compose-kafka.yml up -d
Postgres (dentro da pasta arquivos-docker):
$ docker-compose -f docker-compose-postgres.yml up -d
API Flask com YahooFinances (dentro da pasta api-flask-consulta-yahoo-b3:
$ docker-compose up -d
Dentro da pasta api-flask-consulta-yahoo-b3 ainda possui um script em Python para consumir dados do Kafka para você testar a sua aplicação.

$ python3 consumer-teste-kafka.py
Kafka Connect com MQTT
Para que o aplicativo móvel receba as informaçẽos da API em tempo real, utilizamos o MQTT, assim priorizamos a velocidade e a economia de recursos na troca de informações.

Para este processo utilizamos o Kafka Connect para automatizar a transição das informações do Kafka para o Broker MQTT que pode ser qualquer um que suporte o protocolo.

A instalação pode ser feita conforme os passos logo abaixo:

Acessar o container do Kafka Connect com o Docker:
$ docker exec -it kafka-connect bash
Instalar o Kafka connect do MQTT dentro do container:
$ confluent-hub install confluentinc/kafka-connect-mqtt:latest
A instalação deve ser realizada no path: /etc/kafka-connect/jars

Após a instalação o container ou o Kafka Connect deve ser reiniciado.

A imagem abaixo exemplifica a instalação do Connect do MQTT.


Depois sair do Container com o comando exit e reiniciar o container:
$ docker restart kafka-connect
Com o auxilio do Postman (ou outra parecida), fazer uma requisição POST no endpoint http://localhost:8083/connectors com o JSON do repositório no caminho: arquivos-docker/config-kafka-connect-mqtt.json
$ curl -X POST -H "Content-Type: application/json" -d @arquivos-docker/config-kafka-connect-mqtt.json http://localhost:8083/connectors
Obs: o tópico do MQTT será igual o do Kafka configurado no JSON do connect.
