#!/bin/bash

zoneId=$1
date=$2
curl "http://localhost:8080/challenge/zone-trips?zone=${zoneId}&date=${date}"