# Java Projects Evaluation

This repository contains Java projects demonstrating various programming concepts and frameworks.

## Projects Included

### 1. Gestion de Stock (Stock Management)
- **Location**: `gestion-stock/`
- **Description**: A Java application for managing inventory of computer products
- **Technologies**: Java, Hibernate, MySQL, Maven
- **Features**: Product management, order processing, category management

### 2. Projet Management (Project Management)
- **Location**: `projet/`
- **Description**: A project management system for tracking employees, projects, and tasks
- **Technologies**: Java, Hibernate, MySQL, Maven
- **Features**: Employee management, project tracking, task assignment

### 3. État Civil (Civil Status Management)
- **Location**: `etat-civil/`
- **Description**: A civil status management system for tracking people, marriages, and relationships
- **Technologies**: Java, Hibernate, MySQL, Maven, JPA
- **Features**: Person management, marriage tracking, inheritance relationships

## Getting Started

Each project is a standalone Maven project. To run any project:

1. Navigate to the project directory
2. Run `mvn compile` to compile the project
3. Run `mvn exec:java -Dexec.mainClass="ma.projet.Main"` to execute the main class

## Requirements

- Java 8 or higher
- MySQL Server
- Maven 3.6+

## Database Setup

Each project requires a MySQL database. The database names are:
- `gestion_stock` for the stock management project
- `projet_management` for the project management system
- `etat_civil` for the civil status management system

## Author

Created as part of Java programming evaluation and learning.