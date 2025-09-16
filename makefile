
build: project.class

project.class: project.java
	javac project.java

run: project.class
	java -cp .:mssql-jdbc-11.2.0.jre11.jar project

clean:
	rm project.class
