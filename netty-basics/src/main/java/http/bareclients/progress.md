### first step
* client supports basic http requests and responses, passing result to outer caller by completablefuture.
* separate client for resource management from connection which deals with one actual socket transport.
* one connection per request.
### second step 
* thrift serialization support 
* set deserialization into handler

### third step
* connection pooling
* dns auto probing, update target host address when micro service config changes

### better http api wrapper