# **Getting Started**

### **What this is**

This is a simple template to facilitate the development startup of our Backend Challenge.
Provided in this project is a stripped down Springboot application, with a Postgres database, orchestrated with Docker.

### **Requirements**

 - Maven
 - Java 17
 - Docker

### **Start-up**

Run the following commands in the command line:

    - mvn clean install
    - docker build -t challenge-1.0.0.jar .
    - docker-compose up

### **Instructions**

Please do not code on this repo, just fork-it or build a new one based on this

# Implementation Details

## Structure

This solution has the following packages and files:

- Bootstrap package has the class to load the csv files on application startup.
- Config packages has additional config files.
- Domain package has the classes related with problem domain.
- Repository packages has the jpa interface for each domain class
- Service package has the services classes that connect the servlet to all the necessary classes to return a specific output
    1. TaxiTripsServiceImpl - Class that implements TaxiTripsService interface and is responsible to handle the 3 endpoints desired trips statistics.
- Util package has the helper classes to run groovy scripts, parse html and read files.
    1. FileReaderUtil - responsible for loading csv files
    2. CsvParser - class that parses a trip and zone csv line
- Web package has the controller for the 3 endpoints
    1. TripController has the endpoint definition for the yellow trips request
    2. ZoneController has the endpoints definition for top 5 zones and zone trips requests
    3. Dto package has the dto records for the requests responses


The integration tests make sure the 3 endpoints work as expected. Decided to not write unit 
because these cover more classes.

The dev profile was created for development purposes and used h2 memory for faster development but with memory limitations.

## How to run the application

1. Create a folder at project root named data
2. Paste the 3 files (zones.csv, green_tripdata_2018-01_01-15.csv and yellow_tripdata_2018-01_01-15.csv) inside data folder
    - The application will search for files with those names. If the files are named differently the file load to database will fail.
3. Make sure JAVA_HOME is pointing to java 17
4. Run run.sh script with command. 
   - Make sure the file has executable permissions otherwise use command chmod e.g. chmod 555 ./scripts/run.sh
```
./scripts/run.sh
```
5. The application takes around 5 minutes to start and load all files
6. After the application starts, run the scripts that start with curl. All scripts should run with success.
    - Make sure the file has executable permissions otherwise use command chmod e.g. chmod 555 "script name"
```
./scripts/curl-zone-trips.sh 1 2018-01-12
```
```
./scripts/curl-top-five-zones.sh dropoffs
```
```
./scripts/curl-list-yellow.sh 2018-01-12
```


Swagger UI can be accessed at:
```
http://localhost:8080/challenge/swagger-ui/index.html
```