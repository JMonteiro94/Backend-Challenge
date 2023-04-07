#!/bin/bash

mvn clean install
docker build -t challenge-1.0.0.jar .
docker-compose up