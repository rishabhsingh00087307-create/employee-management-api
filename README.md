# Employee Management REST API

This Spring Boot project implements the second MSE question: an `Employee` model and REST endpoints to create, list, retrieve, update, and delete employees.

## Requirements

- JDK 17 or later
- Spring Tools 4 (STS) or Eclipse with Spring support
- Internet access on the first Maven import so Maven can download dependencies

## Open in STS

1. Extract `EmployeeManagementAPI.zip` to a folder.
2. In STS, choose **File → Import → Maven → Existing Maven Projects**.
3. Browse to the extracted `EmployeeManagementAPI` folder, select its `pom.xml`, then click **Finish**.
4. Wait until Maven dependencies finish downloading.
5. Open `EmployeeManagementApplication.java`, right-click in the editor, and choose **Run As → Spring Boot App** (or **Java Application**).
6. Keep the app running. It listens at `http://localhost:8080`.

## Endpoints

| Operation | HTTP method | URL | Successful response |
|---|---|---|---|
| Add employee | POST | `http://localhost:8080/employees` | 201 Created |
| List all employees | GET | `http://localhost:8080/employees` | 200 OK |
| Get employee by ID | GET | `http://localhost:8080/employees/1` | 200 OK; 404 if missing |
| Update employee | PUT | `http://localhost:8080/employees/1` | 200 OK; 404 if missing |
| Delete employee | DELETE | `http://localhost:8080/employees/1` | 204 No Content; 404 if missing |

For POST and PUT, use Postman (or another REST client), choose **Body → raw → JSON**, and send:

```json
{
  "id": 1,
  "name": "Aman Kumar",
  "department": "IT",
  "salary": 45000
}
```

For PUT, the ID in the URL is used. JSON entries require a positive salary. Duplicate IDs on POST return 409 Conflict.

The example stores employees in memory so the REST behavior is easy to learn. Data clears when the app stops; no database setup is needed for this question.

## Run from a terminal (optional)

From the extracted project folder:

```text
mvn spring-boot:run
```
