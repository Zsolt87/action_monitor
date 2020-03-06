I. Location
------------------------
https://github.com/Zsolt87/action_monitor/

II. Build process
------------------------
1.) Create JAR and start application:
	a.) git clone https://github.com/Zsolt87/action_monitor/
	b.) cd action_monitor
	c.) mvnw clean
	d.) mvnw package
	e.) java -jar target/action_monitor-0.0.1-SNAPSHOT.jar

2.) Start application without bulding JAR
	a.) cd into action_monitor/
	b.) mvnw spring-boot:run
	
3.) Execute tests
	a.) cd into action_monitor/
	b.) mvnw test
	
III. Limitations
------------------------
1.) No authentication nor authorization
2.) In memory database loses messages in case of server restart
3.) H2 and Surfire versions were downgraded because of code issues and new authentication procedure introduced on H2 side

IV. Testing
------------------------
./surfire-report.html