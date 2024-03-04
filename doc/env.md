# docker

```docker-compose.yml
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
  # mysql:
  #   container_name: mysql
  #   image: mysql:latest
  #   environment:
  #     MYSQL_ROOT_PASSWORD: root
  #   ports:
  #     - "3306:3306"
  #     - "33060:33060"
  #   restart: no
  # mariadb:
  #   container_name: mariadb
  #   image: mariadb:latest
  #   environment:
  #     MARIADB_ROOT_PASSWORD: root
  #   ports:
  #     - "13306:3306"
  #   restart: no
  mongo:
    image: "mongo:latest"
    container_name: mongodb
    ports:
      - "27017:27017"
  pulsar:
    command: "bin/pulsar standalone"
    image: "apachepulsar/pulsar:3.1.2"
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
