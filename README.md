# BcAppServer
Todo application server, produces API for comunication with client.
Based on  Maven, Spring Boot 1.5.1, Derby 10.13, OrmLite 5.0, Spring Security. You need Maven installed, after that run mvn package (in project directory) to install project dependencies and compile project. To start the server run java -jar target/BcApp-1.0-SNAPSHOT.jar in project directory.

Database will contain some mock object and two user accounts - one with admin role (Login: Admin password: a) 
and one with user role (Login: User password: a). You can disable this feature by deleting line 13 in https://github.com/Jakub-Vacek/BcAppServer/blob/master/src/main/java/Core/Application.java 
