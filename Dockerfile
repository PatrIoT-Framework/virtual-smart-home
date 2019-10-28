FROM patriotframework/simulator-base:1.8

COPY target/smart-home-virtual-1.0-SNAPSHOT-jar-with-dependencies.jar ./app.jar

CMD java -jar /app.jar
