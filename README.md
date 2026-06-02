# Insurance Service API

## Giới thiệu

Insurance Service là hệ thống quản lý bảo hiểm được xây dựng bằng Spring Boot, hỗ trợ quản lý người dùng, hợp đồng bảo hiểm, yêu cầu bồi thường, thanh toán trực tuyến và thông báo thời gian thực.

Hệ thống được thiết kế theo mô hình RESTful API với cơ chế xác thực JWT, phân quyền người dùng và tích hợp các dịch vụ bên thứ ba như VNPay, Cloudinary, Gmail SMTP.

---

## Công nghệ sử dụng

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring Validation
* Spring WebSocket
* Spring Mail
* MapStruct
* Lombok

### Database

* PostgreSQL

### Authentication & Authorization

* JWT Authentication
* OAuth2 Resource Server
* Role-based Authorization

### Third-party Services

* VNPay Payment Gateway
* Cloudinary
* Gmail SMTP

### Documentation

* Swagger (OpenAPI)

### Build Tool

* Maven

---

## Các chức năng chính

### Người dùng

* Đăng ký tài khoản
* Đăng nhập bằng JWT
* Cập nhật thông tin cá nhân
* Xem danh sách gói bảo hiểm
* Mua hợp đồng bảo hiểm
* Thanh toán trực tuyến qua VNPay
* Quản lý hợp đồng bảo hiểm
* Tạo yêu cầu bồi thường
* Upload tài liệu minh chứng
* Quản lý tài khoản ngân hàng nhận tiền bồi thường
* Nhận thông báo realtime qua WebSocket
* Nhận email thông báo trạng thái yêu cầu bồi thường

### Quản trị viên

* Quản lý người dùng
* Quản lý gói bảo hiểm
* Quản lý hợp đồng bảo hiểm
* Quản lý yêu cầu bồi thường
* Duyệt hoặc từ chối yêu cầu bồi thường
* Thực hiện chi trả bồi thường
* Gửi thông báo cho khách hàng
* Quản lý danh mục ngân hàng (Master Data)

---

## Luồng nghiệp vụ chính

### Mua bảo hiểm

```text
User
 ↓
Xem gói bảo hiểm
 ↓
Tạo hợp đồng
 ↓
Thanh toán VNPay
 ↓
Hợp đồng được kích hoạt
```

### Quy trình bồi thường

```text
User
 ↓
Tạo yêu cầu bồi thường
 ↓
Upload tài liệu minh chứng
 ↓
Admin xem xét
 ↓
Phê duyệt/Từ chối
 ↓
Chi trả tiền bồi thường
 ↓
Gửi Email thông báo
 ↓
Gửi WebSocket Notification
```

---

## Kiến trúc hệ thống

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

### Cấu trúc thư mục

```text
src/main/java
├── configuration
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── mapper
├── repository
├── service
├── utils
└── validator
```

---

## Cài đặt & chạy project

### 1. Clone project

```bash
git clone https://github.com/huylam0703/InsuranceService.git
cd InsuranceService
```

### 2. Cấu hình biến môi trường

```env
DB_URL=
DB_USERNAME=
DB_PASSWORD=

JWT_SIGNER_KEY=

CLOUDINARY_CLOUD_NAME=
CLOUDINARY_API_KEY=
CLOUDINARY_API_SECRET=

VNP_TMNCODE=
VNP_HASH_SECRET=
VNP_PAY_URL=
VNP_RETURN_URL=

MAIL_USERNAME=
MAIL_PASSWORD=
```

### 3. Build project

```bash
mvn clean install
```

### 4. Chạy ứng dụng

```bash
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại:

```text
http://localhost:8080
```

---

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI Docs:

```text
http://localhost:8080/v3/api-docs
```

---

## Tính năng nổi bật

* JWT Authentication & Authorization
* Role-based Access Control
* VNPay Payment Integration
* Cloudinary File Upload
* WebSocket Realtime Notification
* Email Notification System
* Bank Master Data Initialization
* User Bank Account Management
* Claim Compensation Workflow
* RESTful API Design
* DTO Validation & Exception Handling

---

## Hướng phát triển

* Docker & Docker Compose
* CI/CD với GitHub Actions
* AWS Deployment
* Unit Testing
* Integration Testing
* Claim Appeal Workflow
* Payment Refund Workflow
* Redis Caching
* RabbitMQ Event Processing

---

## Tác giả

**Huy Lâm Nhật**

GitHub: https://github.com/huylam0703

Project Repository: https://github.com/huylam0703/InsuranceService
