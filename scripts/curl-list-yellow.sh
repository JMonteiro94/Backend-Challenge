#!/bin/bash

date=$1
curl http://localhost:8080/challenge/list-yellow?date=${date}&page=0&size=20&sort=pickupZone_title
