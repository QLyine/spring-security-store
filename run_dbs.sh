#!/bin/bash


docker run --rm --name mysql-user -d \
    -p 3306:3306 \
    -e MYSQL_ROOT_PASSWORD=root \
    -e MYSQL_USER="user-db-user" \
    -e MYSQL_PASSWORD="user-db-password" \
    -e MYSQL_DATABASE="user-db" \
    mysql:8

docker run --rm --name mysql-product -d \
    -p 3307:3306 \
    -e MYSQL_ROOT_PASSWORD=root \
    -e MYSQL_USER="product-db-user" \
    -e MYSQL_PASSWORD="product-db-password" \
    -e MYSQL_DATABASE="product-db" \
    mysql:8

docker run --rm --name mysql-order -d \
    -p 3308:3306 \
    -e MYSQL_ROOT_PASSWORD=root \
    -e MYSQL_USER="order-db-order" \
    -e MYSQL_PASSWORD="order-db-password" \
    -e MYSQL_DATABASE="order-db" \
    mysql:8
