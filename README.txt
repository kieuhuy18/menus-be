# Dự án Menus-BE

Đây là dự án backend cho hệ thống Menus, sử dụng tư duy Microservice với Spring Boot và Docker.

## 🚀 Yêu cầu Cài đặt

Trước khi chạy, bạn cần cài đặt:
* Git
* JDK 21 (hoặc cao hơn)
* Maven
* Docker Desktop

## 🛠️ Cách Khởi chạy Dự án

1.  **Clone dự án:**
    ```bash
    git clone <url_du_an>
    cd menus-be
    ```

2.  **Build các file .jar:**
    (Bắt buộc) Lệnh này sẽ build code Java thành các file .jar để Docker có thể sử dụng.
    ```bash
    mvn clean package
    ```

3.  **Khởi chạy hệ thống:**
    Lệnh này sẽ khởi động toàn bộ các service (Gateway, User, Mongo, v.v...)
    ```bash
    docker-compose up --build
    ```

Hệ thống sẽ chạy với các cổng sau:
* **API Gateway:** `http://localhost:8080`
* **Database (MongoDB):** `mongodb://localhost:27017`