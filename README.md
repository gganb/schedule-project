# 🗓 Spring Boot 기반 일정 관리 웹 애플리케이션

Spring Boot 기반의 간단한 일정 관리 RESTful API 기반의 일정관리 웹 애플리케이션입니다. 사용자는 일정을 생성하고 관리할 수 있으며, 각 일정은 사용자 이름, 작업 내용, 비밀번호를 통해 관리할 수 있습니다.

---

<br>

## 🔧 사용한 기술

- Java 17.0.14  
- Spring Boot 3.4.4  
- Spring Web  
- JDBC (JdbcTemplate)  
- MySQL 8.0.28 
- Lombok  

<br>

## ✏ 일정 관리 API 명세서 

<img width="1281" alt="Image" src="https://github.com/user-attachments/assets/b27d0c26-70b4-43dc-afb6-858a4c7ac8d7" />

<br>

## ERD

<img width="578" alt="Image" src="https://github.com/user-attachments/assets/5bca6043-8ac7-42e7-a4f3-c7b15249f3c8" />

<br>

## 디렉터리 구조
```



```


## 🗃 데이터베이스 설정
실행 전에 다음과 같은 테이블을 생성해주세요

```CREATE TABLE schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(255),
    task VARCHAR(255),
    password VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```
<br>

## 기능
- 일정 생성 : 사용자 이름, 할 일, 비밀번호를 입력해 새 일정을 생성합니다.
- 전체 일정 조회 : 등록된 일정을 모든 일정/ 사용자 이름/ 날짜별로 조회할 수 있습니다.
- 일정 단건 조회 : 등록된 일정을 단건으로 조회할 수 있습니다.
- 일정 수정 : 일정을 선택하고 비밀번호를 확인한 뒤, 작성자의 이름과 할 일을 수정할 수 있습니다.
- 일정 삭제 : 일정을 선택하고 비밀번호를 확인한 뒤, 해당 일정을 삭제할 수 있습니다.

<br>

## API 엔드포인트
- 일정 생성 :  `POST /api/schedule`
- 전체 일정 조회 : `GET /api/schedule`
- 사용자별 일정 조회 : `GET /api/schedule/user/{userName}`
- id로 일정 조회 : `GET /api/schedule/{id}`
- 날짜로 일정 조회 : `GET /api/schedule/date?updatedAt=yyyy-MM-dd`
- 사용자 이름과 id로 일정 단건 조회 : `GET /api/schedule/user?=userName&id=id`
- 일정 수정 : `PATCH /api/schedule/{id}`
- 일정 삭제 : `DELETE /api/schedule/{id}`

<br>

## 향후 개선 계획
- 데이터베이스 분리  
  
