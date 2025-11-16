# 1. Bắt đầu từ một image có sẵn Java
FROM eclipse-temurin:21-jdk

# 2. Đặt tên cho tệp .jar sẽ được build ra
ARG JAR_FILE=target/*.jar

# 3. Copy tệp .jar từ thư mục 'target' (sau khi Maven build) vào trong image
COPY ${JAR_FILE} app.jar

# 4. "Mở" cổng mà service này chạy (để Docker biết)
EXPOSE 8080

# 5. Lệnh để chạy service khi container khởi động
ENTRYPOINT ["java","-jar","/app.jar"]