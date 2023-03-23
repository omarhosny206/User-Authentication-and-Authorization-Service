# User Authentication and Authorization Service 🚀

## Service Functionality:

- Login and Signup services.
- Each user has a specific role either ***ROLE_ADMIN*** or ***ROLE_CUSTOMER***
- Username and Password based Authentication.
- Role based **Authorization**.
- Used **JWT**(Json Web Token) for **Authentication** *&* **Authorization**.

## **Start using it now** 🚀🚀
You can see the API documentation through: [link](http://localhost:8080/swagger-ui/index.html)


```
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           ├── config
    │   │           │   ├── BeanConfig.java
    │   │           │   └── SecurityConfig.java
    │   │           ├── controller
    │   │           │   ├── LoginController.java
    │   │           │   ├── SignupController.java
    │   │           │   └── UserController.java
    │   │           ├── dto
    │   │           │   ├── CustomUser.java
    │   │           │   ├── ErrorDto.java
    │   │           │   ├── LoginRequest.java
    │   │           │   ├── LoginResponse.java
    │   │           │   ├── SignupRequest.java
    │   │           │   └── UserDto.java
    │   │           ├── exception
    │   │           │   ├── ApiError.java
    │   │           │   ├── CustomAuthenticationEntryPoint.java
    │   │           │   ├── CustomExceptionHandler.java
    │   │           │   └── CustomException.java
    │   │           ├── filter
    │   │           │   └── JwtAuthenticationFilter.java
    │   │           ├── model
    │   │           │   ├── Role.java
    │   │           │   └── User.java
    │   │           ├── repository
    │   │           │   ├── RoleRepository.java
    │   │           │   └── UserRepository.java
    │   │           ├── service
    │   │           │   ├── impl
    │   │           │   │   ├── LoginServiceImpl.java
    │   │           │   │   ├── RoleServiceImpl.java
    │   │           │   │   ├── SignupServiceImpl.java
    │   │           │   │   └── UserServiceImpl.java
    │   │           │   ├── LoginService.java
    │   │           │   ├── RoleService.java
    │   │           │   ├── SignupService.java
    │   │           │   └── UserService.java
    │   │           ├── UserAuthenticationAndAuthorizationServiceApplication.java
    │   │           └── util
    │   │               ├── AuthenticationUser.java
    │   │               └── JwtUtil.java
    │   └── resources
    │       └── application.properties
```

---
## **Database Design**
![db_design](https://user-images.githubusercontent.com/58389695/227189157-812f7b71-b540-473a-b215-65fdfb4903dd.PNG)

## Tech Stack:
- Programming Language: Java 17
- Backend Framework: Spring Boot v3.0.1
- ORM: Hibernate 
- Database Engine: PostgreSQL
- API Documentation: Swagger via OpenApi 3.0
- Build Tool: Maven
- Logger: SLF4J


![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
[![Maven](https://badgen.net/badge/icon/maven?icon=maven&label)](https://https://maven.apache.org/)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)


## **Features to add in the future** 🚀:
- Adding OAuth 2.0.
- Signup with Google, Facebook and other platforms.
