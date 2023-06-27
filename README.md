# Recipes 食譜

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

1. 下載專案。
2. 在IntelliJ IDE中開啟專案。
3. 建立並設置您的MySQL資料庫(參考第4點application.properties裡的spring.datasource.url設定建立資料庫。
4. 更新`Recipes/Recipes/task/src/resources/application.properties`檔案，填入你的MySQL設定。
5. 執行RecipesApplication

## Features
* 使用者認證
* 資料庫CRUD操作
* RESTful API

## Status
已經完成，通過Jetbrains Academy測試集。

Entity:  
<img width="461" alt="image" src="https://github.com/Loren-Cheng/Recipes/assets/73529790/601e6c15-f957-4a2a-9ec4-2532460bb300">  
Interface:  
Repositoy:  
Controller:  


### Stage 1/5: First recipe  
實作了簡易的JSON API端點來讀取、新增食譜  
* POST /api/recipe  
  * 接收JSON格式的食譜並覆蓋目前的食譜  
* GET /api/recipe  
  * 回傳當前的食譜  
  
  
### Stage 2/5: Multiple recipes  
增進多個食譜的功能，調整現有的端點  
* POST /api/recipe/new 
  * 創建一個食譜並回傳這個含有id的JSON食譜，後續以此id來取得食譜。    
  * 新增成功時，伺服器應回傳HTTP狀態碼200(ok)  
  
    (receives a recipe as a JSON object and returns a JSON object with one id field.  
    This is a uniquely generated number by which we can identify and retrieve a recipe later.  
    The status code should be 200 (Ok).)  
  
* GET /api/recipe/{id}  
  * 回傳指定id的JSON食譜，成功取得資源時回覆狀態碼200,失敗時回覆404  
    
  
  
### Stage 3/5: Store a recipe
新增資料庫來存取食譜
* 在資料庫永久儲存所有食譜: 
    * 在重啟伺服器後，使用者仍能讀取所有儲存的食譜
* 實行一個新的Delete端點
  * 這個端點會刪除特定id的食譜
  * 刪除後伺服器回傳204(No Content)狀態碼
  * 若指定id不存在，則回傳404(Not found)狀態碼 
* 伺服器只接受有效的食譜
  * 所有欄位需填滿，name及description欄位不得為空，JSON陣列最少需含一組資料
  * 如果食譜不符合這些要求，伺服器需回覆400狀態碼  


### Stage 4/5: Sort & update
用category/name來篩迴食譜 
  
食譜必需包含2個新的屬性:  
* category欄位代表菜品的種類(例如甜點、飲料…)
  * 欄位限制跟name/description欄位一致  
  * 欄位不得為空  
* date欄位儲存食譜建立或更新的日期  
  * 可以使用任何date/time的格式，例如e 2023-06-26T13:34:48.227624(預設LocalDateTime格式)，但必需有8個以上的characters.   

增加下面2個端點:

* PUT /api/recipe/{id}  
    * 接收指定id的JSON食譜並更新receives a recipe as a JSON object and updates a recipe with a specified id.  
    * 更新date欄位   
    * 伺服器應回傳204(No Content)狀態碼  
    * 如果指定id的食譜不存存，伺服器應回傳404(Not found)狀態碼  
    * 如果更新的食譜不符合上面提到的欄位要求(所有欄位需填滿，string的相關欄位不得為空，JSON陣列最少需含一組資料)，伺服器回傳400(Bad Request)狀態碼  
* GET /api/recipe/search  
    * 使用兩個互斥查詢參數的任一個:  
       * category –  
          * 如果存在查詢的category,則回傳所有符合category條件的JSON食譜陣列  
          * 查詢不區分大小寫，由新排到舊  
       * name –  
         * 如果存在查詢的name，則回傳所有包含name的的JSON食譜陣列  
         * 查詢不區分大小寫，由新排到舊  
    * 如果沒有查到食譜，則回傳空的JSON陣列
    * 如果0個參數或大於1個參數來查詢，則回傳400(Bad Request)狀態碼  
    * 如果指定的參數無效，也應回傳400狀態碼
    * 如果一切正確，則回傳200(Ok)狀態碼  


### Stage 5/5: More chefs to the table
增加註冊及多使用者功能Improve the service to support registration and multiple users.  
* 新增端點 POST /api/register  
  * 接收JSON物件，並且需含email(string)及password(string)2欄位  
    * 如果指定email的使用者不存在，則程式儲存(註冊)該使用者到資料庫，並且回傳200(Ok)狀態碼  
    * 如果資料庫已存在指定使用者，則回傳400(Bad Request)狀態碼  
    * 2欄位需有效:email需包含"@"及"."， password需最少8位且不為空格  
    * 如果欄位不符合需求，伺服器需回傳400(Bad Request)狀態碼  
    * 在資料庫加密password,可使用BCryptPasswordEncoder  
* Import Spring Boot Security並且設設可存取端點  
  * 所有已實現的端點只對已註冊的用戶使用，然後通過HTTP Basic auth進行身份驗證及授權
  * 若未驗證，則回傳401(Unauthorized)狀態碼  
* 增加額外限制  
  * 僅有食譜作者可刪除或更新食譜  
  * 如果非食譜作者，但要刪除或更新食譜，則回傳403(Forbidden)狀態碼   
