**Library Management System**
Spring Boot backend for managing a small library — books, users, loans, and reviews.

**Overview**
The system supports two roles: Reader and Librarian.
All actions in the application are role-based.

**Reader**
Readers can:

- view all available books in the library
- browse reviews (for all books or a selected one)
- borrow books:
    loanDate = LocalDate.now()
    dueDate = loanDate + 30 days
- return books they have borrowed (their own loans only)
- post reviews with a rating from 0 to 10

**Librarian**
Librarians can do everything a Reader can, plus:

- manage users (create, update, delete, view all)
- manage books, loans, and reviews
- create loan records manually
    set or modify loanDate and dueDate to any valid values
- create loans (borrows and returns) and reviews for other users

**Validation**
Inputs are validated using Jakarta validation constraints
Additional business logic is enforced in the service layer, including:
- no negative values (e.g. book copies, years)
- valid review rating range (0–10)
- correct date logic (e.g. dueDate cannot be before loanDate)

**Error Handling**
All errors follow a consistent JSON structure:

{
  "message": "Specific error detail here",
  "timestamp": "Sun Apr 19 12:28:01 CEST 2026"
}

Handled exceptions include:
- duplicated entries
- insufficient permissions (wrong role)
- resource not found
- invalid input or business rule violations

A global exception handler (@RestControllerAdvice) is used to format responses

**Project Structure**
The project follows a layered architecture:

- Controllers – handle HTTP requests
- Services – contain business logic and validation
- Repositories – handle database access 
- DTOs – exposed to users (API layer)
- Entities – mapped to the SQL database
- Mappers – convert between DTOs and entities

**Security**
Authentication and authorization are handled using JWT (JSON Web Tokens)
Role-based access control (Reader vs Librarian)
Token generation and validation handled by a dedicated JWT service
Passwords stored in the database are hashed
