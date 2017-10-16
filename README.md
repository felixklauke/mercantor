# mercantor
A simple but effective network attached service registry with optional load balancing capabilities used for service discovery in a microservice environment.

Master: 

[![Build Status](https://travis-ci.org/FelixKlauke/mercantor.svg?branch=master)](https://travis-ci.org/FelixKlauke/mercantor)

Dev:    

[![Build Status](https://travis-ci.org/FelixKlauke/mercantor.svg?branch=dev)](https://travis-ci.org/FelixKlauke/mercantor)

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

```json
{
  "serviceKey": "someId",
  "basePath": "http://api.example.com",
  "role": "sudoku-resolver",
  "serviceExpiration": 42,
  "serviceExpirationTimeUnit": "SECONDS"  
}
```

# Load Balancing
The internal service registry of the server is capable of two different modes to lookup services you registered by their role:
- Random
- Round Robin
- Single

_Random:_ The query will return a random known service with the given role. If you have four known services for the same role the query will return (Example) [s0, s2, s0, s3, s1, s0, s3, s4...]

_Round Robin:_ The query will return one service after another. If you have 4 known services for the same role the query will return [s0, s1, s2, s3, s0, s1, s2...] 

_Single:_ The query will always return the one single service. There cannot be multiple services for one role.

# Example
Server:
```java
MercantorServerConfig mercantorServerConfig = new MercantorServerConfigBuilder()
        .setHost("127.0.0.1")
        .setPort(8081)
        .setServiceExpiration(30)
        .setServiceExpirationTimeUnit(TimeUnit.SECONDS)
        .setServiceLookupMode(ServiceLookupMode.SINGLE)
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

Registering a new service: 
```java
mercantorClient.registerService("http://localhost", "boss");
```

Removing a service: 
```java
IService service = ...;

mercantorClient.removeService(service);
```

Querying a service:
```java
ListenableFuture<IService> boss = mercantorClient.getService("boss");

try {
    IService service = boss.get();
} catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
 }
```

# How does this work?
All Services will have to register themselves using the client API. The server will be configured
with an expiration time for all registered services. When the client registers a service the server 
will deliver some information containing the expiration for the service and the model that represents the service.

The server will check for bleeding services every 1 second.

When the server returned the register information for the client he will monitor
the clients heartbeats that should be sent in the window of the expiration interval. If a client doesn't send
a heartbeat in time the server will forget him.
