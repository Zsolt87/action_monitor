I. Location
------------------------

II. Build process
------------------------
1.) Create JAR and start application:
	a.) cd into action_monitor/
	b.) mvnw clean
	c.) mvnw package
	d.) java -jar target/action_monitor-0.0.1-SNAPSHOT.jar

2.) Start application without bulding JAR
	a.) cd into action_monitor/
	b.) mvnw spring-boot:run
	
3.) Execute tests
	a.) cd into action_monitor/
	b.) mvnw test
	
III. Limitations:
------------------------
1.) No authentication not authorization
2.) In memory database loses messages in case of server restart