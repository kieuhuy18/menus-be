# Menus Backend

## Mô tả
Backend cho hệ thống Menus. 

## Công nghệ sử dụng
- Java 21
- Spring Boot 3
- MongoDB
- Maven

## Cấu trúc thư mục
```
menus-be/
├── User/
│   ├── src/main/java/com/app/user/UserApplication.java
│   ├── src/main/java/com/app/user/controller/
│   ├── src/main/java/com/app/user/service/
│   ├── src/main/java/com/app/user/repository/
│   ├── src/main/java/com/app/user/entity/
│   ├── src/main/java/com/app/user/dto/
│   ├── src/main/java/com/app/user/exception/
│   └── src/main/resources/application.yaml
└── pom.xml
```

## Chức năng chính
- Quản lý người dùng (tạo, sửa, xóa, xem chi tiết, phân trang)
- Quản lý vai trò (Role) và quyền hạn (Permission)
- Chuẩn hóa response qua ApiResponse (phân trang chỉ xuất hiện ở API List)

## Hướng dẫn cài đặt & chạy
1. Clone project:
   ```bash
   git clone https://github.com/username/project-name.git
   cd menus-be
   ```

2. Build module User:
   ```bash
   cd User
   ./mvnw clean package -DskipTests
   ```

3. Chạy chương trình:
   ```bash
   ./mvnw spring-boot:run
   ```

## Cấu hình Database
- MongoDB mặc định: `mongodb://localhost:27017/my-shared-db`
- Có thể thay đổi qua biến môi trường `SPRING_DATA_MONGODB_URI`
- File cấu hình: [application.yaml](User/src/main/resources/application.yaml)
  ```yaml
  server:
    port: 8081
  spring:
    application:
      name: user
    data:
      mongodb:
        uri: ${SPRING_DATA_MONGODB_URI:mongodb://localhost:27017/my-shared-db}
  ```

## Test nhanh với Postman
- Base URL: `http://localhost:8081`
- List user (có phân trang, trang bắt đầu từ 1, mặc định 10 phần tử/trang): `GET /users?page=1&size=10`
- Detail user (không có pageNumber/pageLimit): `GET /users/{id}`
- Create user: `POST /users` (Body JSON: `{"userName":"nguoidung","userEmail":"email@example.com","password":"123456"}`)

