## **Refactoring Charity Registration Application**

### 🛠️ Goal

Refactor the existing **Charity Registration** application to leverage **Spring Data JPA**. This will improve maintainability, reduce boilerplate code, and ensure more efficient, declarative data access.

---

### ✅ Objectives

* Migrate from manual **JDBC** or traditional **JPA `EntityManager`**-based queries to **Spring Data JPA** repositories.
* Use **interface-based** repository definitions for common CRUD operations.
* Implement **custom queries** using `@Query` annotations where necessary.
* Maintain separation of concerns between the service and data access layers.
* Preserve existing functionality for charity CRUD operations and file attachment management.

---

### 🔁 Before Refactoring

Previously, the application used:

* Manually written SQL statements in DAO classes.
* Direct use of `EntityManager` or `JdbcTemplate`.
* Tedious boilerplate for basic operations like `findById`, `save`, or `delete`.

---

### 🔁 After Refactoring

Using **Spring Data JPA**:

* Repositories extend `JpaRepository<Charity, Long>` or `CrudRepository`.
* Common methods like `findAll()`, `save()`, `deleteById()` are inherited.
* Custom finder methods like `findByCharityName()` or `findByTaxId()` can be declared using Spring Data conventions.
* Complex joins (e.g., Charity with associated Programs or Attachments) can be handled with `@Query` and `JOIN FETCH`.

---

### 📦 Benefits

* **Less boilerplate** – no need to write CRUD SQL or manage transactions manually.
* **Better testability** – repository interfaces are easy to mock.
* **Improved readability** – business logic is no longer cluttered with persistence logic.
* **Flexible queries** – supports both derived query methods and JPQL/native queries.

---

### 💡 Next Steps

We’ll begin by:

1. Creating repository interfaces such as `CharityRepository`, `ProgramRepository`, and `RegistrationRepository`.
2. Refactoring service classes to delegate persistence operations to these repositories.
3. Ensuring that integration with controllers and unit tests remains intact.

