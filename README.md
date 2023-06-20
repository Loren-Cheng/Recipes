# Recipes 菜單


## 目錄
1. [Introduction](#introduction)
2. [Technologies](#technologies)
3. [Setup](#setup)
4. [Features](#features)
5. [Status](#status)

## Introduction
這是一個Jetbrain Academy上，後端開發人員教學中的一個專案。  
用來學習及使用Spring boot、JSON、REST API、Spring Boot Security、H2 數據庫(實作時以Mysql代替)、LocalDateTime、Lombok及其他主題。


## Technologies
* Spring Boot      2.7.5
* Spring data jpa  2.7.5
* MySQL            8.0.29
* lombok           1.18.22

## Setup
說明如何設置專案環境，例如：

1. 下載專案：
2. 在你的IDE（如IntelliJ IDEA）中開啟專案。
3. 建立並配置您的MySQL資料庫。
4. 更新`src/main/resources/application.properties`檔案，填入你的MySQL設定。

## Features
* 使用者認證
* 資料庫CRUD操作
* RESTful API

## Status
已經完成，通過Jetbrains測試集。

Entity:  
<img width="461" alt="image" src="https://github.com/Loren-Cheng/Recipes/assets/73529790/601e6c15-f957-4a2a-9ec4-2532460bb300">  
Interface:  
Repositoy:  
Controller:  


### Stage 1/5: First recipe  
實作了簡易的JSON API端點來讀取、新增菜單  
* POST /api/recipe  
  * 接收JSON格式的菜單並覆蓋目前的菜單  
* GET /api/recipe  
  * 回傳當前的菜單  
  
  
### Stage 2/5: Multiple recipes  
增進多個菜單的功能，調整現有的端點  
* POST /api/recipe/new 
  * 創建一個菜單並回傳這個含有id的JSON菜單，後續以此id來取得菜單。    
  * 新增成功時，伺服器應返回HTTP狀態碼200(ok)  
  
    (receives a recipe as a JSON object and returns a JSON object with one id field.  
    This is a uniquely generated number by which we can identify and retrieve a recipe later.  
    The status code should be 200 (Ok).)  
  
* GET /api/recipe/{id}  
  * 回傳指定id的json菜單，成功取得資源時回覆狀態碼200,失敗時回覆404  
    returns a recipe with a specified id as a JSON object (where {id} is the id of a recipe).  
    The server should respond with the 200 (Ok) status code.  
    If a recipe with a specified id does not exist, the server should respond with 404 (Not found).  
  
  
### Stage 3/5: Store a recipe
Add a database to store and delete recipes.
* Store all recipes permanently in a database: 
    * after a server restart, all added recipes should be available to a user;
* Implement a new DELETE /api/recipe/{id} endpoint.  
  * It deletes a recipe with a specified {id}.  
  * The server should respond with the 204 (No Content) status code.  
  * If a recipe with a specified id does not exist, the server should return 404 (Not found);  
* The service should accept only valid recipes  
  * all fields are obligatory, name and description shouldn't be blank, and JSON arrays should contain at least one item.  
  * If a recipe doesn't meet these requirements, the service should respond with the 400 (Bad Request) status code.  


### Stage 4/5: Sort & update
Retrieve recipes by their category/name and update them if you need to.  

In this stage, the recipe structure should contain two new fields:  

category represents a category of a recipe.  
The field has the same restrictions as name and description.  
It shouldn't be blank;  
date stores the date when the recipe has been added (or the last update).  
You can use any date/time format, for example 2021-09-05T18:34:48.227624 (the default LocalDateTime format), but the field should have at least 8 characters.  
Also, the service should support the following endpoints:  

PUT /api/recipe/{id} 
    receives a recipe as a JSON object and updates a recipe with a specified id. 
    Also, update the date field too. 
    The server should return the 204 (No Content) status code. 
    If a recipe with a specified id does not exist, the server should return 404 (Not found). 
    The server should respond with 400 (Bad Request) if a recipe doesn't follow the restrictions indicated above 
    (all fields are required, string fields can't be blank, arrays should have at least one item);
GET /api/recipe/search  
    takes one of the two mutually exclusive query parameters:  
        category –  
          if this parameter is specified, it returns a JSON array of all recipes of the specified category.  
          Search is case-insensitive, sort the recipes by date (newer first);  
        name –  
          if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter.  
          Search is case-insensitive, sort the recipes by date (newer first).  
    If no recipes are found, the program should return an empty JSON array.  
    If 0 parameters were passed, or more than 1, the server should return 400 (Bad Request).  
    The same response should follow if the specified parameters are not valid. If everything is correct, it should return 200 (Ok).  



### Stage 5/5: More chefs to the table
Improve the service to support registration and multiple users.  
    New endpoint POST /api/register  
        receives a JSON object with two fields: email (string), and password (string).  
        If a user with a specified email does not exist, the program saves (registers) the user in a database and responds with 200 (Ok).  
        If a user is already in the database, respond with the 400 (Bad Request) status code.  
        Both fields are required and must be valid: email should contain @ and . symbols, password should contain  
        at least 8 characters and shouldn't be blank.  
        If the fields do not meet these restrictions, the service should respond with 400 (Bad Request).  
        Also, do not forget to use an encoder before storing a password in a database. BCryptPasswordEncoder is a good choice.  
    Include the Spring Boot Security dependency and configure access to the endpoints  
        – all implemented endpoints (except /api/register) should be available only to the registered  
        and then authenticated and authorized via HTTP Basic auth users.  
        Otherwise, the server should respond with the 401 (Unauthorized) status code.  
    Add additional restrictions  
        – only an author of a recipe can delete or update a recipe.  
        If a user is not the author of a recipe, but they try to carry out the actions mentioned above,  
        the service should respond with the 403 (Forbidden) status code.  
