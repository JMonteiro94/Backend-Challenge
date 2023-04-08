# Scripts

- run.sh - small script to run backend-challenge jar and postgres db in docker
- curl-top-five-zones.sh - to send top five zones request.
  - command: sh curl-top-five-zones.sh "order" where order can be dropoffs or pickups (if not order is provided or order string is invalid defaults to pickups)
e.g. 
```
sh curl-top-five-zones.sh dropoffs
```
- curl-zone-trips.sh: to send zone trips request.
  - command: sh curl-zone-trips.sh "zone id" "date" where zone id is the zone location id from excel and date should be in format Year-Month-Day
```
sh curl-zone-trips.sh 1 2018-01-12
```
- curl-list-yellow.sh: to send get yellow trips requests with pagination, filter and sort.
  - command: sh curl-list-yellow.sh "date" where date should be in format Year-Month-Day
  ```
  sh curl-list-yellow.sh 2018-01-12
  ```
  - More options can be added to this request. For that, customize the curl as follows:
    - to sort the result append: 
      - for property id use "sort=id"
      - for nested property use "sort=pickupZone_title"
      - for ascending or descending use "sort=id,asc" or "sort=id,desc"
    - if you want a specific page, update the number in "page=0"
    - if you want a specific page size, update the number in "size=20"
```
curl http://localhost:8080/challenge/list-yellow?date=${date}&page=0&size=20&sort=pickupZone_title
```