# hop


**hop** is a service for media stuff  
forget I enumerate the API here, just follow **http://localhost:8080/swagger-ui.html#!/hop-rest-controller/** to have a serius Swagger experience


##use
**mvn spring-boot:run**
   to run the bootiful app
   
**mvn spring-boot:run -Dspring.active.profiles=integration**
   to run the bootiful app with some integration tests


---  

####Hop
This is the JPA repository for managing Hops.
The Hopster microservice is bound to MongoDb in order to keep thing as simple as possible. 
In case a different base should be needed, we will provide a specific version of the service

####HopRestController 
As its names states clearly, this class is the RestController for the Hop application.
In case the hopster microservice should provide also other interfaces, by mvc and templates for instance, we will provide the appropriated controller.
This class wraps the service and exposes it in a defined protocol to clients.
No business logic must be implemented in this class.
It is not necessary to expose this class as public, so keep it package private, like all the others.  

####HopService 
This class is responsible for serving the "business" of the Hop model.
It must be protocol agnostic and bridge the model stored in a repository to the client interface.  

####HopRepository
This is the JPA repository.
The Hopster microservice is bound to MongoDb in order to keep thing as simple as possible.
In case a different base should be needed, we will provide a specific version of the service

####HopsterApplication
This is the main bootiful Application.
It embeds its configuration in order to keep things clean and simple.

---
That's all folks, this describes all the microservice by now.
