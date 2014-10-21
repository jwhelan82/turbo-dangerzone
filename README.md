turbo-dangerzone
================

Requirements tracking tool

Platform:
Java 7 

Build:
Maven 3.0.3

Proposed tech:

Database
MongoDB - http://www.mongodb.org/

Environment
JBoss AS 7 - http://jbossas.jboss.org/
Hibernate OGM - http://hibernate.org/ogm/

Delivery
The application will be invoked using RESTful web services. 
A Web UI will be created for users to use the application.

Wishlist
Allowing 3rd party users to use the application via invoking the RESTful services directly

Build Steps
============

After installing Maven, open command window / bash shell and navigate to the Requisitor directory.

Confirm Maven is installed correctly by running:  mvn --version

To build, run:  mvn clean compile

Cleaning is optional of course. 

Maven will also produce a JAR of the application if you run: mvn install

This will JAR the classes and move them to the target directory.  Note that install also includes the compilation step, so most times you'll just do: mvn clean install

Maven will try to connect to external dependencies if necessary, however this can be disabled by using the flag -o e.g. mvn -o clean install

