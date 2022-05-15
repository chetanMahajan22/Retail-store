
Running port : 8000
####User Service

- Create user - POST - /users
- Get all users - GET = /users
- Get users - GET - /users/{id}
- Delete user - DELETE -  /users/{id}
#### Billing service
- Get Bills - GET - /bills
- Get Bill - GET - /bills/{id}
- create bill - POST - /bills/
- add product to bill -POST -/bills/{id}

####  Models
##### User
* name
* id
* Type
* registrationDate

##### Bill
* id
* products

##### Product
* name
* price
* type

#### Basic UI for accessing Application End points 
Swagger-ui
URL : http://localhost:8000/swagger-ui