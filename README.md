# Employee Management System
A spring boot application which provides implementation of REST APIs for a Employee Management System(EMS).

## Table of Contents
- [Database](#database)
    - [Designation](#designation)
    - [Employee](#employee)
- [Example Data](#example-data)
    - [Designation](#designation)
    - [Employee](#employee)
- [API](#api)

For documentations, navigate to [JavaDocs](https://github.com/khalsa-ji/EmployeeManagementSystem/blob/master/JavaDocs). It provides documentation for each and every class, their constructors, methods, etc.

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


## API

#### GET /api/v1/employees

Returns a list of all the employees

Request
```
GET /api/v1/employees
```

Response
```json
[
    {
        "jobTitle": "Director",
        "id": 1,
        "managerId": -1,
        "name": "Thor"
    },
    {
        "jobTitle": "Manager",
        "id": 4,
        "managerId": 1,
        "name": "Captain America"
    },
    {
        "jobTitle": "Manager",
        "id": 2,
        "managerId": 1,
        "name": "Iron Man"
    },
    {
        "jobTitle": "Lead",
        "id": 8,
        "managerId": 4,
        "name": "Ant Man"
    },
    {
        "jobTitle": "Lead",
        "id": 3,
        "managerId": 1,
        "name": "Hulk"
    },
    {
        "jobTitle": "Developer",
        "id": 10,
        "managerId": 3,
        "name": "Black Widow"
    },
    {
        "jobTitle": "Developer",
        "id": 7,
        "managerId": 4,
        "name": "Falcon"
    },
    {
        "jobTitle": "DevOps",
        "id": 6,
        "managerId": 2,
        "name": "Vision"
    },
    {
        "jobTitle": "QA",
        "id": 5,
        "managerId": 2,
        "name": "War Machine"
    },
    {
        "jobTitle": "Intern",
        "id": 9,
        "managerId": 2,
        "name": "Spider Man"
    }
]
```

### POST /api/v1/employees

Registers(adds) a new employee

Body
```json
{
  "name": "String Required - Employee Name",
  "jobTitle": "String Required - Employee Designation",
  "managerId": "Integer Optional - Manager Employee ID, Required if current employee is not Director"
}
```

Request
```
POST /api/v1/employees
body: {
    "name": "ABC XYZ",
    "jobTitle": "Manager",
    "managerId": 1
}
```

Response

```json
{
    "id": 11,
    "name": "ABC XYZ",
    "jobTitle": "Manager",
    "manager": {
        "id": 1,
        "name": "Thor",
        "jobTitle": "Director"
    },
    "colleagues": [
        {
            "id": 4,
            "name": "Captain America",
            "jobTitle": "Manager"
        },
        {
            "id": 2,
            "name": "Iron Man",
            "jobTitle": "Manager"
        },
        {
            "id": 3,
            "name": "Hulk",
            "jobTitle": "Lead"
        }
    ]
}
```

### GET /api/v1/employees/{ID}

Returns an employee having specified ID as its employee id

Request
```
GET /api/v1/employees/2
```

Response
```json
{
    "employee": {
        "jobTitle": "Manager",
        "id": 2,
        "name": "Iron Man"
    },
    "manager": {
        "jobTitle": "Director",
        "id": 1,
        "name": "Thor"
    },
    "colleagues": [
        {
            "jobTitle": "Manager",
            "id": 4,
            "name": "Captain America"
        },
        {
            "jobTitle": "Lead",
            "id": 3,
            "name": "Hulk"
        }
    ],
    "subordinates": [
        {
            "jobTitle": "DevOps",
            "id": 6,
            "name": "Vision"
        },
        {
            "jobTitle": "QA",
            "id": 5,
            "name": "War Machine"
        },
        {
            "jobTitle": "Intern",
            "id": 9,
            "name": "Spider Man"
        }
    ]
}
```

#### PUT /api/v1/employees/${ID}

Updates or replaces an existing employee

Body
```json
{
  "name": "String Required - Employee Name",
  "jobTitle": "String Required - Employee Designation",
  "managerId": "Integer Optional - Manager Employee ID, Required if current employee is not Director",
  "replace": "Boolean Optional - Replace old employee with current employee"
}
```

Request
```
PUT /api/v1/employees/3
body: {
    "name": "Black Panther",
    "jobTitle": "Lead",
    "managerId": 1,
    "replace": true
}
```

Response
```json
{
    "id": 12,
    "name": "Black Panther",
    "jobTitle": "Lead",
    "manager": {
        "id": 1,
        "name": "Thor",
        "jobTitle": "Director"
    },
    "colleagues": [
        {
            "id": 4,
            "name": "Captain America",
            "jobTitle": "Manager"
        },
        {
            "id": 2,
            "name": "Iron Man",
            "jobTitle": "Manager"
        }
    ],
    "subordinates": [
      {
        "id":10,
        "name":"Black Widow",
        "jobTitle":"Developer"
      }
    ]
}
```

### DELETE /api/v1/employees/${ID}

Deletes an employee having employee id as ID

Request
```
DELETE /api/v1/employees/10
```

Response
```
OK
```

### GET /api/v1/designations

Returns a list of all the designations

Request
```
GET /api/v1/designations
```

Response
```json
[
    {
        "designationID": 1,
        "designation": "Director"
    },
    {
        "designationID": 2,
        "designation": "Manager"
    },
    {
        "designationID": 3,
        "designation": "Lead"
    },
    {
        "designationID": 4,
        "designation": "Developer"
    },
    {
        "designationID": 5,
        "designation": "DevOps"
    },
    {
        "designationID": 6,
        "designation": "QA"
    },
    {
        "designationID": 7,
        "designation": "Intern"
    }
]
```
