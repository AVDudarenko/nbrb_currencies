# Currency Rate Microservice

This is a microservice built using Spring Boot to fetch and display currency exchange rates from the National Bank of
the Republic of Belarus (NBRB) API. The application stores the fetched data in an H2 in-memory database.

## Technologies Used

- Spring Boot
- Spring MVC
- Hibernate
- Spring Data JPA
- H2 Database
- Maven
- Mockito (for testing)

## Features

- Fetch and store currency exchange rates for a specific date.
- Retrieve exchange rates for a specific date and currency code.
- Centralized exception handling using @ControllerAdvice.

## Endpoints

### Load Rates for a Specific Date

- URL: /api/currency-rate/load/{date}
- Method: GET
- Description: Fetches and stores currency exchange rates for the specified date.
- **Response**:
    - **202 ACCEPTED**: Data loaded successfully.
    - **500 INTERNAL SERVER ERROR**: Failed to load data or an error occurred.

Get Rate for a Specific Date and Currency Code

- URL: /api/currency-rate/{date}/{currencyCode}
- Method: GET
- Description: Retrieves the exchange rate for the specified date and currency code.
- **Response**:
    - **200 OK**: Returns the exchange rate.
    - **404 NOT FOUND**: Rate not found for the specified date and currency code.
    - **500 INTERNAL SERVER ERROR**: An error occurred.

## Exception Handling

Centralized exception handling is implemented using GlobalExceptionHandler class annotated with @ControllerAdvice. This
handles specific exceptions like CurrencyRateException and general exceptions, providing appropriate HTTP responses.

## Setup and Running the Application

1. **Clone the repository**:

```bash
git clone https://github.com/your-username/currency-rate-microservice.git
cd currency-rate-microservice
```

2. **Build the project**:

```bash
mvn clean install
```

3. **Run the application**:

```bash
mvn spring-boot:run
```

4. **Access H2 Database Console**:

- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:currencydb
- Username: user
- Password: user

## Running Tests

Tests are written using JUnit and Mockito. To run the tests, execute the following command:

```bash
mvn test
```