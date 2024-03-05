# docker

> dckr_pat_aW_E9kZKNTi33Ga-A_Hd7oMpPbA

```yml
version: "3.8"
services:
  postgres:
    container_name: postgres
    image: postgres:latest
    environment:
      POSTGRES_USER: root
      POSTGRES_PASSWORD: root
    ports:
      - "5432:5432"
    restart: no
  mongo:
    image: "mongo:latest"
    container_name: mongodb
    ports:
      - "27017:27017"
  pulsar:
    command: "bin/pulsar standalone"
    container_name: pulsar
    image: "apachepulsar/pulsar:3.1.2"
    hostname: pulsar
    ports:
      - "9080:8080"
      - "6650:6650"
    tty: true
    stdin_open: true
  consul:
    image: consul
    environment:
      - CONSUL_BIND_INTERFACE=eth0
    ports:
      - "8500:8500"
    container_name: consul
  redis:
    image: redis
    ports:
      - "6379:6379"
    container_name: redis
    tty: true
    stdin_open: true

```
```yml

version: '3.9'
services:
    pulsar-manager:
        image: 'apachepulsar/pulsar-manager:v0.3.0'
        environment:
            - SPRING_CONFIGURATION_FILE=/pulsar-manager/pulsar-manager/application.properties
        ports:
            - '7750:7750'
            - '9527:9527'
        tty: true
        stdin_open: true

```
