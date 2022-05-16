**Running port : 8000**

####Running Application:
1. Checkout the https://github.com/chetanMahajan22/Retail-store branch
2. Build using maven command - **mvn clean install**
3. Once built check for target directory under Retail-Store
4. It should have jar with name "retail-store-discount-calculator-0.0.1-SNAPSHOT.jar"
5. Docker Image will be created using above jar
6. Run docker command to build an image from Retail-store folder
   - **'docker build -t retail-store-discount-calculator:1.0 .'**
7. Once Docker image is built and available run docker-compose file to start application
    - **'docker-compose up'**
8.MongoDB connection from local system URL : mongodb://localhost:27017
8. Swagger UI URL : http://localhost:8000/swagger-ui.html
9. Use Basic authentication details for accesing application end points : **user : user , password : pass123**


**You will find RetailStore.postman_collection.json file under Retail-store folder. 
You can import this file and test appliaction end points using Postman**

Application End points
####Discount calculator
calculate Discounted amount - GET - /bills/discount/{user_id}/{bill_id}
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
