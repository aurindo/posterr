#Posterr

###

Sections:
 - Stack
 - Features
 - How to run
 - Simulating load the home page
 - Simulating load the user profile page
 - Endpoints
 - Improvements
 - ?

###Stack
 - Java 11
 - H2
 - SpringBoot
 - Gradle
 
 **NOTE:**
 This application uses H2 as database.
 
#### Features

-  Write a post [Original, Repost, Quoted post]
-  Follow/unfollow users
-  Fetch posts
-  Fetch user info
-  Check if user follow another user 
 
 **Note:**
 This application does not create, delete, list or update users.  

#### To run the application on the local machine

First time:

	$ ./gradlew clean build test bootRun

Second time and further:

    $ ./gradlew bootRun
	


	
	
	
### Discovery Server
- It is a server to register automatically the address of microservices, with this is more simple for one microservice localize another.
```
	$ cd discovery
	$ mvn clean isntall spring-boot:run
```
### Gateway Server
- It is a server to make a gateway to unify all API calls in a unique address.
```
	$ cd discovery
	$ mvn clean isntall spring-boot:run
```
### Challenge Service
 - This service contain an API to make possible create a new food order.

1. Add a new customer
```
curl -H "Content-Type: application/json" -X POST -d '{
	"name":"John"
    "login": "admin",
    "password": "password"
}' http://localhost:8085/users/sign-up
```

2. Login
```
curl -H "Content-Type: application/json" -X POST -d '{
    "login": "admin",
    "password": "password"
}' http://localhost:8085/login
```
*Save the token!!*

3. List products
```
curl -H "Content-Type: application/json" \
-H "Authorization: Bearer <TOKEN>" \
-X GET -d  http://localhost:8085/products
```

4. Create an order
```
curl -H "Content-Type: application/json" \
-H "Authorization: Bearer <TOKEN>" \
-X POST -d '{
	"customer":{
	"id": 1
	},
	  "productList":[{
	    "id": 1
	  }, {
	    "id": 2
	  }]
	}'  http://localhost:8085/customerOrder
```
