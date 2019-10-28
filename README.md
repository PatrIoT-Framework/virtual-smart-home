# Virtual Smart Home

This project is simulation of Smart Home project and is used
as a demo project for PatrIoT framework.

## Build and run

In order to and run this application you need 

* Java and JDK in version 8
* Maven

### Building java app
```bash
$ mvn clean package
``` 

The command will produce two `jar` files in target 
directory.

* `smart-home-virtual-1.0-SNAPSHOT.jar` is thin `jar` file, which is executable only if located in target directory.
* `smart-home-virtual-1.0-SNAPSHOT-jar-with-dependencies.jar` fat jar with all dependencies contained in `jar` file, executable anywhere.

### Running app

You have two options to run the application, if you need 
only to test the package then you can execute the thin jar, 
while it is located within the target directory.

```bash
$ java -jar target/smart-home-virtual-1.0-SNAPSHOT.jar
```

If you need to put the `jar` file anywhere else, 
you can use the `jar` with dependencies

```bash
$ java -jar $SOMEPATH/smart-home-virtual-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Building docker image

In order to build docker image for the tests you need 
following commands.

```bash
$ mvn clean package
$ docker build . --tag "${IMAGE_TAG}"
```

This will produce image with tag specified by variable 
`IMAGE_TAG`

### Running docker image

The docker image is then executed like any other:

```bash
$ docker run ${IMAGE_TAG}
```

In order to have access to the opened port `8282` 
with REST API and port `9292` with Websocket, 
you can pass the `-p` parameters.

```bash
$ docker run -p 8282:8282 -p 9292:9292 ${IMAGE_TAG}
```

Then the applications REST API will be available at 
`http://localhost:8282`