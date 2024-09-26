# Hotel Booking API

This is a RESTful API service using Spring Boot for a hotel management aggregator application. The service allows users to register, login, manage hotels, and book rooms.

## Features

- User Registration and Login
- JWT-based Authentication
- Role-based Authorization (CUSTOMER, HOTEL_MANAGER, ADMIN)
- Hotel Management (Create, Read, Update, Delete)
- Room Booking
- Booking Cancellation

## Technologies Used

- Java 21
- Spring Boot 
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JSON Web Tokens)
- Maven

## Getting Started

1. Clone the repository
2. Configure MySQL database settings in `application.properties`
3. Run the application using `mvn spring-boot:run`
4. Access the API at `http://localhost:8080`

## API Endpoints

- POST /api/auth/register - User registration
- POST /api/auth/login - User login
- GET /api/hotels - Get all hotels (public)
- POST /api/hotels - Create a new hotel (ADMIN only)
- PUT /api/hotels/{id} - Update hotel details (HOTEL_MANAGER only)
- DELETE /api/hotels/{id} - Delete a hotel (ADMIN only)
- POST /api/hotels/{hotelId}/book - Book a room (CUSTOMER only)
- DELETE /api/bookings/{bookingId} - Cancel a booking (HOTEL_MANAGER only)

## Testing

Run unit tests using `mvn test`

## Security

- Passwords are encrypted using BCrypt
- JWT tokens are used for authentication
- Role-based access control is implemented

## Error Handling

Custom exception handling is implemented to return appropriate HTTP status codes and error messages.

## Logging

Logging is implemented using SLF4J and Logback.


Postman collection url - https://api.postman.com/collections/21583443-44f8c400-18c6-4ec0-ac69-1ccaea7f5adb?access_key=PMAT-01J8PWZ3PW8MKDT7XMGXKPHRFT
