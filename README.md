# Code Challenge Soution


### Statup Project
To run project please execute
```
./gradlew bootRun
```

To run all test please execute
```
./gradlew integrationTest
```


### Documentation
The whole documentation is available under 

* [Code challenge solution documentation](http://localhost:8080/swagger-ui/)


### Nomenclature 
Friend - user who would like follow specified user

Followers - people who follow specified user 


### Example usage 
On startup application there is no data initialized.

Adding User / Post
---
To add new user just send you first post and defined userId within request.

```
curl -X POST "http://localhost:8080/posts?message=Test%20Message&userId=1" -H "accept: */*" -d ""
```

after sending new post to, user with ID has been created.

Adding friend to specify user
---

Example of attaching new friend with ID:2 to User With ID:1

```
curl -X POST "http://localhost:8080/users/1/friends" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"friendId\": 2}"
```

Removing friend to specify user
---
Example of removing friend with ID:2 from User With ID:1

```
curl -X DELETE "http://localhost:8080/users/1/friends" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"friendId\": 2}"
```


Printing Timeline (printing all post of following users)
---

By sending pagination parameters you can customise length of response and sorting order 

Here is example for descending order for timelines  
```
curl -X GET "http://localhost:8080/users/1/timeline?page=0&size=10&sort=createdAt%2Cdesc" -H "accept: */*"
```


Printing Wall (printing all post of specified user)
---

By sending pagination parameters you can customise length of response and sorting order 

Here is example for descending order for wall posts  
```
curl -X GET "http://localhost:8080/users/1/wall?sort=createdAt%2Cdesc" -H "accept: */*"
```
