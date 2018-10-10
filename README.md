# Myblog
RESTful API for Blog

##Task
Create a Restful API which will be used by the simple blog web application.
The application should have one user role - PUBLISHER.

#####REST resources to implement
*	Resource to authenticate a particular user. Users may be hardcoded.

*	Create, update, delete own blog posts.

*	Get the list of all blog posts. Filter option to get only own posts should be present.

*	Get a particular blog post by id.

The publisher should be able to update or remove only own posts. All REST resources must be secure except the authentication one.

######Required technologies 
Java 8 and any desired DB's and frameworks.

##Technologies
* Java 8
* Spring boot (Security,Data,IoC)
* Hibernate
* H2 database

##Installing

1. Clone this project on your computer
2. Run MyBlogApplication.java file in your favorite IDE
3. Install [Postman](https://www.getpostman.com/)
4. Run Postman and import "MyBlog.postman_collection.json"
