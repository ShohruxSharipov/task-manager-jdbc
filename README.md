# Java CLI Task Manager

A command-line task manager built in Java using JDBC and PostgreSQL.

## Features
- User registration and authentication
- Password hashing
- Task creation and listing
- Mark tasks as done
- Filter tasks (done / todo)
- Clean CLI table output
- DAO-based architecture

## Technologies
- Java
- PostgreSQL
- JDBC
- SQL
- Environment Variables

## Commands
ls           # list all tasks
ls done      # list completed tasks
ls todo      # list unfinished tasks
add          # add new task
rm <id>      # mark task as done
logout

Setup:
1. Create PostgreSQL database
2.Set environment variables:
  DB_URL=jdbc:postgresql://localhost:5432/TestDB
  DB_USER=postgres
  DB_PASSWORD=your_password
3.Run Main.java

Notes:
This project was created to practice backend development concepts such as
JDBC, SQL, and clean architecture.
