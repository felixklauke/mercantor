# mercantor
A simple but effective network attache service registry used for service discovery in a microservice environment.

Master: 
[![Build Status](https://travis-ci.org/FelixKlauke/mercantor.svg?branch=master)](https://travis-ci.org/FelixKlauke/mercantor)

Dev:    
[![Build Status](https://travis-ci.org/FelixKlauke/mercantor.svg?branch=dev)](https://travis-ci.org/FelixKlauke/mercantor)

# How does this work?
Mercanot is built on a REST base. Whenever a service starts it has to register itself with a POST Request against mercantor. Mercantor
will assign a unique id to the service and hold it as a available service. In an internval of 30 seconds a service has to send a heartbeat
to a service. If no heartbeat is sent the service will be makred as "bleeding" will be removed when the next request against the server 
fails.
