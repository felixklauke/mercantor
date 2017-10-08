# mercantor
A simple but effective network attache service registry used for service discovery in a microservice environment.

Master: 

[![Build Status](https://travis-ci.org/FelixKlauke/mercantor.svg?branch=master)](https://travis-ci.org/FelixKlauke/mercantor)

Dev:    

[![Build Status](https://travis-ci.org/FelixKlauke/mercantor.svg?branch=dev)](https://travis-ci.org/FelixKlauke/mercantor)

# API Docmentation
**Creating a new service**

Method / URL: `POST $BASE_URL/service`

Body:
```json
{
  "basePath": "http://api.example.com",
  "role": "sudoku-resolver"
}
```

Reponse:
```json
{
  "serviceKey": "someId",
  "basePath": "http://api.example.com",
  "role": "sudoku-resolver",
  "serviceExpiration": 42,
  "serviceExpirationTimeUnit": "SECONDS"  
}
```

**Updating a service / Sending a Heartbeat**

Method / URL: `PUT $BASE_URL/service/{serviceKey}`

**Removing a service** 

Method / URL: `DELETE $BASE_URL/service/{serviceKey}`

**Querying a service**

Method / URL: `GET $BASE_URL/service/{role}`

# Usage
- Install [Maven](http://maven.apache.org/download.cgi)
- Clone this repo
- Install: ```mvn clean install```

**Maven dependencies**

_Mercantor Server:_
```xml
<dependency>
    <groupId>de.d3adspace</groupId>
    <artifactId>mercantor-server</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

_Mercantor Client:_
```xml
<dependency>
    <groupId>de.d3adspace</groupId>
    <artifactId>mercantor-client</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

# Example
Server:
```java
MercantorServerConfig mercantorServerConfig = new MercantorServerConfigBuilder()
        .setHost("127.0.0.1")
        .setPort(8081)
        .setServiceExpiration(30)
        .setServiceExpirationTimeUnit(TimeUnit.SECONDS)
        .createMercantorServerConfig();
        
IMercantorServer mercantorServer = MercantorServerFactory.createMercantorServer(mercantorServerConfig);
mercantorServer.start();
```

Client: 
```java
MercantorClientConfig mercantorClientConfig = new MercantorClientConfigBuilder()
        .setServerHost("127.0.0.1")
        .setServerPort(8081)
        .createMercantorClientConfig();
        
IMercantorClient mercantorClient = MercantorClientFactory.createMercantorClient(mercantorClientConfig);
```

# How does this work?
Mercanot is built on a REST base. Whenever a service starts it has to register itself with a POST Request against mercantor. Mercantor
will assign a unique id to the service and hold it as a available service. In an internval of 30 seconds a service has to send a heartbeat
to a service. If no heartbeat is sent the service will be makred as "bleeding" will be removed when the next request against the server 
fails.
