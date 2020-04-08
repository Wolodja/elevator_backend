## Elevator Simulation Backend

Java Spring Boot program that simulates the work of elevator logic engine.

There are two types of event that the program can handle: 
1. Request for elevator from floor in UP / DOWN direction
2. Request inside elevator


#### How to Run:

As the project is, the Spring app can be started as seen below.

build and run the code with Maven

    mvn package
    mvn spring-boot:run

or start the target JAR file 

    mvn package
    java -jar target/elevators-backend-0.0.1-SNAPSHOT.jar


#### How to Test

Application can be tested in different ways:
1. Run single get request:
   
   - Request elevator on floor in some direction (up/down):
   http://localhost:8081/rest/v1/requestElevator/{floor}/{direction}
   
     For example you want to request elevator 7 floor up 
     http://localhost:8081/rest/v1/requestElevator/7/up
   
   - Request inside elevator with elevatorId and requested floor
   http://localhost:8081/rest/v1/requestInsideElevator/{elevatorId}/{floor}
   
      For example in elevator 1 pressed button with 5 floor number 
      http://localhost:8081/rest/v1/requestInsideElevator/1/5

2. Run files with prepared requests: 
   - requestCase1.http
   - requestCase2.http    
   - requestCase3.http    
   - requestCase4.http    
   
###### The result can be checked in console or in generated log file (log/spring.log)