# mercantor
A simple but effective network attache service registry used for service discovery in a microservice environment.

Master: <br>
[![Build Status](https://travis-ci.org/FelixKlauke/mercantor.svg?branch=master)](https://travis-ci.org/FelixKlauke/mercantor)

Dev:    
[![Build Status](https://travis-ci.org/FelixKlauke/mercantor.svg?branch=dev)](https://travis-ci.org/FelixKlauke/mercantor)

# Usage
- Install [Maven](http://maven.apache.org/download.cgi)
- Clone this repo
- Install: ```mvn clean install```

**Maven dependencies**

_Mercantor:_
```xml
<dependency>
    <groupId>de.d3adspace</groupId>
    <artifactId>mercantor-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

# Example
```java
MercantorConfig mercantorConfig = new MercantorConfigBuilder()
        .setHost("127.0.0.1")
        .setPort(8081)
        .setServiceExpiration(30)
        .setServiceExpirationTimeUnit(TimeUnit.SECONDS)
        .setServiceExpirationCheckInterval(1)
        .setServiceExpirationCheckIntervalTimeUnit(TimeUnit.SECONDS)
        .createMercantorConfig();
        
IMercantor mercantor = MercantorFactory.createMercantor(mercantorConfig);
mercantor.start();
```

# How does this work?
Mercanot is built on a REST base. Whenever a service starts it has to register itself with a POST Request against mercantor. Mercantor
will assign a unique id to the service and hold it as a available service. In an internval of 30 seconds a service has to send a heartbeat
to a service. If no heartbeat is sent the service will be makred as "bleeding" will be removed when the next request against the server 
fails.
