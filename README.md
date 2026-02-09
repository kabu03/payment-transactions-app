# Payment Transactions Web App

A full-stack web application to simulate payment transactions, built with Angular, Spring Boot, and PostgreSQL. This app allows users to log in, view their incoming and outgoing transactions, and add new transactions. Authentication and authorization are implemented using JWT (JSON Web Tokens), and only logged-in users can access or manipulate transaction data.

## Features

- **User Authentication**: Login required to access transaction data. JWT is used to authorize requests.
- **Incoming and Outgoing Transactions**: View transactions where the user is either the creditor or the debtor.
- **Add Transactions**: Form to create new transactions, specifying details like amount, currency, debtor, and creditor.
- **Responsive UI**: Built with Angular for a smooth user experience.

## Architecture

The app follows the **Onion Architecture**, ensuring a clean separation of concerns:
- **Controllers** handle the HTTP requests.
- **Services** implement business logic.
- **Repositories** interact with the database.
- **Entities** represent database models.

## Technologies

- **Frontend**: Angular, TypeScript, HTML/CSS, SCSS
- **Backend**: Java, Spring Boot, Kotlin (some modules), JWT for authentication
- **Database**: PostgreSQL, Hibernate, JDBC

## Setup and Installation

### Prerequisites

- **Java 17** or higher
- **Node.js** and **npm** (for Angular)
- **PostgreSQL** installed and running

### Backend Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/kabu03/payment-transactions-app.git
   cd payment-transactions-app
   ```

2. **Set Up PostgreSQL Database**

   - Create a PostgreSQL database (e.g., `payment_transactions_db`).
   - Note down the database URL, username, and password.

3. **Configure Database Connection**

   Open `application.properties` (or `application.yml`) in `src/main/resources/` and set your PostgreSQL connection details:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/payment_transactions_db
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
   
4. **Run the Backend**

   Navigate to the backend directory and run the Spring Boot application:

   ```bash
   ./mvnw spring-boot:run
   ```

The backend server should start on http://localhost:8080.

### Frontend Setup

  1. **Navigate to the Frontend Directory**
     ```bash
     cd application/frontend
     ```
  3. **Install Dependencies**
     ```bash
     npm install
     ```
  4. **Run the Frontend**
     Start the Angular development server:
     ```bash
     ng serve
     ```
     The frontend will be available at http://localhost:4200.

## Usage
1. Access the Application
- Open your browser and go to http://localhost:4200.
2. Log In
- Use the login page to authenticate.
- You’ll need to have user records in the PostgreSQL database. You can create dummy users by inserting them directly into the database if needed.
3. View Transactions
- Once logged in, navigate to Incoming Transactions to view transactions where you are the creditor.
- Go to Outgoing Transactions to view transactions where you are the debtor.
4. Add New Transaction
- Go to Add Transaction to create a new transaction.
- Fill in details such as amount, currency, debtor, and creditor aliases.
5. Logout
- Click the logout button to securely log out of your session.

## Notes
- **Database Initialization:** If the app fails due to missing data (e.g., users), you may need to insert records manually into PostgreSQL. Refer to the database schema you created and use dummy data for initial testing.
- **Security:** JWT authentication is enabled; tokens are generated upon successful login and must be included in headers for protected API routes.
- **Development vs Production:** Make sure to update the database connection details and JWT configuration when deploying to production.
