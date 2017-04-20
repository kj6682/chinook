# CHINOOK


**chinook** is a service for media stuff (hops - Hypermedia Object Personally Selected) with a flavour of BackboneJS
  
run the application and just follow **http://localhost:8080/swagger-ui.html#!/hop-rest-controller/** to have a serius Swagger experience

you need to run the db before you start this stuff:
```
pg_ctl -D /usr/local/var/postgres -l /usr/local/var/postgres/server.log start
```

and possibly have a look at the db console :
```
psql  -h localhost -d catalog -p 5432
```

##use
**mvn spring-boot:run**
   to run the bootiful app

**mvn spring-boot:run -Pvalidate**
      to run the bootiful app with security without data refresh (users are not created, hops are not cleaned up)
      the maven profile 'secure-validate' triggers the spring profile 'validate'
      this is the default profile

**mvn spring-boot:run -Pcreate**
      to run the bootiful app with security and data refresh (users created, hops are cleaned up)
      the maven profile 'secure-validate' triggers the spring profile 'validate'
      
**mvn spring-boot:run -Pno-security**
      to run the bootiful app without security
      the maven profile 'unsecure' triggers the spring profile 'unsecure'

**mvn spring-boot:run -Dspring.active.profiles=integration**
   to run the bootiful app with some integration tests



---  

###Hop
This is the JPA repository for managing Hops.
The Hopster microservice is bound to MongoDb in order to keep thing as simple as possible. 
In case a different base should be needed, we will provide a specific version of the service

###HopRestController 
As its names states clearly, this class is the RestController for the Hop application.
In case the hopster microservice should provide also other interfaces, by mvc and templates for instance, we will provide the appropriated controller.
This class wraps the service and exposes it in a defined protocol to clients.
No business logic must be implemented in this class.
It is not necessary to expose this class as public, so keep it package private, like all the others.  

###HopService 
This class is responsible for serving the "business" of the Hop model.
It must be protocol agnostic and bridge the model stored in a repository to the client interface.  

###HopRepository
This is the JPA repository.
The Hopster microservice is bound to MongoDb in order to keep thing as simple as possible.
In case a different base should be needed, we will provide a specific version of the service

###HopsterApplication
This is the main bootiful Application.
It embeds its configuration in order to keep things clean and simple.

#Notes
In order to avoid annoying .idea files in my commits (and despite a quite good .gitignore) I had to proceed to this:
```
git rm -r --cached .idea
```

---
That's all folks, this describes all the application by now.
