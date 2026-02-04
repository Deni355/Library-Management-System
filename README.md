# Library Management System

A command-line Java application for managing library operations including user registration, book cataloging, and borrowing/returning books. Data is persisted in a PostgreSQL database.

## Features

- **User Authentication** -- Login and register users by name
- **Book Management** -- Add books with title, author, and publication year
- **Borrow Books** -- Browse available books and borrow them
- **Return Books** -- View borrowed books and return them
- **Search Books** -- Search the catalog (in progress)

## Tech Stack

- **Java** -- Application logic and CLI interface
- **PostgreSQL** -- Relational database
- **JDBC** (postgresql-42.7.9) -- Database connectivity

## Project Structure

```
├── src/
│   ├── Main.java            # Entry point, menus, and user flow
│   ├── Database.java        # PostgreSQL connection config
│   ├── BookManager.java     # Book CRUD and borrow/return operations
│   └── UserManager.java     # User registration and lookup
├── db/
│   └── init.sql             # Database schema (books, users, borrowings)
├── lib/
│   └── postgresql-42.7.9.jar
└── README.md
```

## Database Schema

Three tables with foreign key relationships:

- **books** -- `id`, `title`, `author`, `year`, `available`
- **users** -- `id`, `first_name`, `last_name`, `created_at`
- **borrowings** -- `id`, `user_id` (FK), `book_id` (FK), `borrowed_at`, `returned_at`

## Prerequisites

- Java JDK 11+
- PostgreSQL 17+

## Setup

### 1. Database

```bash
# Start PostgreSQL (macOS)
brew services start postgresql@17

# Create the database and initialize the schema
createdb library_db
psql library_db -f db/init.sql
```

### 2. Configure Connection

Edit `src/Database.java` if your PostgreSQL host, port, database name, or credentials differ from the defaults (`localhost:5432/library_db`).

### 3. Compile and Run

```bash
# Compile
javac -cp lib/postgresql-42.7.9.jar:. src/*.java -d out/

# Run
java -cp lib/postgresql-42.7.9.jar:out Main
```