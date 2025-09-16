🛒 E-Commerce Database & Portfolio System

This project is a Java and SQL Server–based application that simulates an e-commerce system, integrated with a developer portfolio front-end. It was built as a university project to combine backend database design, server interaction, and UI presentation into a single cohesive system.

🚀 Features

E-Commerce Backend

Product catalog with hundreds of real scraped entries

products_sql_server

Customers, orders, and order line items modeled in SQL

Province-based tax rates with realistic data

products_sql_server

Support for product views and purchase history tracking

Java Application

Core business logic implemented in project.java

SQL Server integration via Microsoft JDBC driver

Configurable authentication via auth.cfg

Portfolio Frontend

Personal portfolio system configurable via portfolio.js

portfolio

Sections for skills, education, work experience, projects, and achievements

Social media and contact info support

Automation & Build

makefile for building and running the Java app

Support for multiple JDBC drivers (jre11, jre18)

🛠️ Tech Stack

Java – application backend

SQL Server – relational database with schema + data seeding

products_sql_server

JDBC – database connectivity

HTML/CSS/JS (React-style config) – portfolio frontend

portfolio

Makefile – build automation

📂 Project Structure
/src
  ├── project.java        # Main Java source
  ├── project.class       # Compiled class
/sql
  ├── products_sql_server.sql  # Schema + dataset
/config
  ├── auth.cfg            # DB credentials
/lib
  ├── mssql-jdbc-11.2.0.jre11.jar
  ├── mssql-jdbc-11.2.0.jre18.jar
/frontend
  ├── portfolio.js        # Portfolio customization
makefile

⚙️ Setup & Installation

Clone the Repository

git clone https://github.com/yourusername/ecommerce-portfolio.git
cd ecommerce-portfolio


Set up SQL Server

Create a database cs3380.

Run products_sql_server.sql to create tables and load product data.

Configure Authentication

Edit auth.cfg with your SQL Server credentials:

url=jdbc:sqlserver://localhost:1433;databaseName=cs3380
user=yourUsername
password=yourPassword


Build & Run

make
java -cp .:lib/mssql-jdbc-11.2.0.jre11.jar project

📸 Portfolio UI

The portfolio.js file drives a customizable developer portfolio with:

Greeting & splash animation

Skills, education, and work history

Big projects, achievements, blogs, and talks

portfolio

👥 Team

Developed as part of a Database & Software Systems course project.
