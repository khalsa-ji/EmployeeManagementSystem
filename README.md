# Employee Management System
A spring boot application which provides implementation of REST APIs for a Employee Management System(EMS).

## Table of Contents
- [Database](#database)
    - [Designation](#designation)
    - [Employee](#employee)
- [Example Date](#example-data)
    - [Designation](#designation)
    - [Employee](#employee)

For documentations, navigate to [JavaDocs](https://github.com/khalsa-ji/EmployeeManagementSystem/blob/master/JavaDocs/index.html). It provides documentation for each and every class, their constructors, methods, etc.

To view class diagrams, navigate to [Class Diagrams](https://github.com/khalsa-ji/EmployeeManagementSystem/tree/master/Class%20Diagrams). It provides UML based diagrams consisting of packages, classes, interfaces, method prototypes, properties with their data types, dependency graphs, etc.

## Database

#### Designation
- designation_id: Long (Primary Key)
- level_id: Float
- designation: String

#### Employee
- employee_id: Long (Primary Key)
- job_id: Designation (Reference)
- manager_id: Long
- employee_name: String
- job_title: String

### Example Data

Designation

| designation_id | level_id | designation |
| -------------- | -------- | ----------- |
|        1       |    1.0   |   Director  |
|        2       |    2.0   |   Manager   |
|        3       |    3.0   |   Lead      |
|        4       |    4.0   |   Developer |
|        5       |    4.0   |   DevOps    | 
|        6       |    4.0   |   QA        |
|        7       |    5.0   |   Intern    |

Employee

| employee_id | employee_name   | manager_id | job_title | job_id |
| ----------- | --------------- | ---------- | --------- | ------ |
|      1      |     Thor        |     -1     | Director  |    1   |
|      2      |     Iron Man    |      1     | Manger    |    2   |
|      3      |     Hulk        |      1     | Lead      |    3   |
|      4      | Captain America |      1     | Manager   |    2   |
|      5      |    War Machine  |      2     | QA        |    6   |
|      6      |     Vision      |      2     | DevOps    |    5   |
|      7      |     Falcon      |      4     | Developer |    4   |
|      8      |     Ant Man     |      4     | Lead      |    3   |
|      9      |   Spider Man    |      2     | Intern    |    7   |
|     10      |   Black Widow   |      3     | Developer |    4   |

