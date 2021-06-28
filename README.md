# product-backend

Prerequisites:
1) Java 11
2) Local redis setup
3) Mongo database

App description:
   SignUp facility has to be provided for new user.
   A user has to made authenticated using token while successful login and token has to be removed while logout.
   App consists of various products.
   An authenticated user can create a new product, update an existing product, view an exising product ,delete an existig product & view all products by pagination.
   An authenticated user can also search product by tag & view product distribution tagwise
   
App Features :
1) Signup , Login , Logout 
2) Authenticated users can make CRUD operations of a product
3) Authenticated users can view all products,search products by tag ,view product distribution tagwise

API list:
* signUp
* login
* logout
* createProduct
* getProduct
* updateProduct
* deleteProduct
* getProducts
* searchProductByTag
* getProductDistributionByTag

1) signUp	

Http Method : POST
EndPoint : http://localhost:8080/signUp
Task : For registration of new user

Request:
{
"email": "",
"password": "",
"city": ""
}

Response:
 String respresenting signed up successfully (or) user already exists

2) login

Http Method : POST
EndPoint : http://localhost:8080/login
Task :For login of new user

Request:
{
"email": "",
"password": ""
}

Response:
 String respresenting logged in successfully (or) user already exists (or) password is wrong

3) logout

Http Method : POST
EndPoint : http://localhost:8080/logout
Task : For logout of current user

Request:
{
"email": ""
}

Response:
 String respresenting logged out successfully (or) user already exists (or) logged out please login again

Common Interceptor for upcoming apis:
Interceptor validation (using preHandle)
* we use interceptor for validating token passed in header
* if token passed in header is in redis , the user can perform actions
* if token passed in header is not in redis , we send 401 unauthorised

Note: productName is considered as unique 

4) createProduct

Http Method : POST
EndPoint : http://localhost:8080/product/LG_Fridge_Model2018
Task : For creating new product

 Request:
{
"quantity": 60,
"price": "40000",
"tags": [
"fridges",
"50*60 inch",
"cooling",
"sensor light",
"5 ton"
]
}

Header:
token : UUID

Response:
 String respresenting product  successfully  added(or) product already exists 

5) getProduct

Http Method : GET
EndPoint : http://localhost:8080/product/LG_Fridge_Model2018
Task : For reading current product

Header:
token : UUID

Response:
 returns the product corresponding to productName in pathvariable

5) updateProduct

Http Method : PUT
EndPoint : http://localhost:8080/product/LG_Fridge_Model2018
Task : For updating current product

 Request:
{
"quantity": 60,
"price": "40000",
"tags": [
"fridges",
"50*60 inch",
"cooling",
"sensor light",
"5 ton"
]
}
Header:
token : UUID

Response:
 returns the product successfully updated (or) product does not exist

6) deleteProduct

Http Method : DELETE
EndPoint : http://localhost:8080/product/LG_Fridge_Model2018
Task: For deleting current product

Header:
token : UUID

Response:
 returns the product successfully deleted (or) product does not exist

7) getProducts

Http Method : GET
EndPoint : http://localhost:8080/products?pageNumber=0&pageSize=3
Task :For product listing by pagination

Request parameters:
pageNumber : int
pageSize : int

Header:
token : UUID

Response:
 returns the number of products till date according to pagination


7) getProductDistributionByTag

Http Method : GET
EndPoint : http://localhost:8080/getProductDistributionByTag
Task : For getting product distribution based on tag

Header:
token : UUID

Response:
 returns the number of products across each tag
