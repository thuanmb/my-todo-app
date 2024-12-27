# My TODO App

This is a TODO management application that includes:

-   Backend: Java + PostgreSQL
-   Frontend: ReactJS

## Requirements

-   Java 17
-   Maven
-   Node.js and npm
-   PostgreSQL

```
# Install Java, Maven, Node.js, npm, and PostgreSQL client if needed
sudo yum update -y
sudo yum install -y java-17-openjdk maven nodejs npm

```

## Installation

1. Set up PostgreSQL and create a database.
2. Configure the database credentials in `backend/src/main/resources/application.properties`.
3. Run the installation script:

```
# Run the configure script
autoreconf --install
./configure

# Compile the project and package it
make

# Install the application
sudo make install

# Run the tests
make run_tests

```

4. Run the app:

```
/opt/my-todo-app
```

## Usage

-   Run the backend with `mvn spring-boot:run`.
-   Run the frontend with `npm start`.

## License

This project is licensed under the GNU General Public License v3.0.

TODO:

-   load the properties password from the ENV/configure file instead

## DESIGN

### Requirements

#### Scenarior 1: new user

-   As a new user, I can register a new account, using username and password
-   After registered the account successfully, I can see the my TODO list page
    Note: Optionally, requires to verify the account via email

#### Scenarior 2: existing user

-   As an existing user, i can login into the system
-   After logged in successfully, I can see the my TODO list page

#### Scenarior 3: TODOs list page

-   In the TODO list page, I can create new TODO
-   In the TODO list page, I can edit an existing TODO
-   In the TODO list page, I can remove an existing TODO
-   In the TODO list page, I can set the due date for a TODO. When the time come, i will receive a notification about my due TODO
-   In the TODO list page, I can filter the items using the quick access label
-   In the TODO list page, I can search for the items by title and description
-   In the TODO list page, when I scroll down, the items will be loaded more automatically
-   In the TODO list page, my session will be renew every 15min

#### Scenarior 4: log out

-   In the TODO list page, I can log out
-   After logged out, I can see the Login page

### System design

#### Sign Up

-   User goes to the home page
-   User clicks on the Sign Up button
-   User enters the username/password and submit the form
-   FE requests API to BE
-   BE validates the username/password, create a new user in the DB (or update the existing deleted user)
-   FE checks the response from BE, if it's success, redirect the user to the TODO List page, else show the error message

#### Sign In

-   User goes to the home page
-   User clicks on the Sign In button
-   User enters the username/password and submit the form
-   FE requests API to BE
-   BE verifies the username/password: if user exists, activating, not deleted, password is correct
-   FE checks the response from BE, if it's success, redirect the user to the TODO List page, else show the error message

#### Session Management

-   When signing in the system, on successfull authentication, BE create a new JWT (payload: userId, roles, and iat)
-   FE after received the JWT, store in HTTP-only cookies (to prevent XSS) or local storage
-   FE sends the JWT in the Authorization header for every API request
-   BE validates the JWT in middleware for all protected routes
-   BE decodes the token to identify the user and enforce access control (e.g., restrict access to their own todos)
-   For every 15min (before the token expiration time), FE automatically refresh the token using the token refreshing endpoint
-   Schedule a job to remove expired tokens from the tokens periodically

#### Viewing TODO List

-   FE requests API to get TODO list of an user, using the stored JTW
-   BE authentication and authorization the request and query the TODO list of requesting user
-   FE after received the response from BE, show the TODO list or error message

#### Create/Update/Delete TODO

-   User C/U/D TODO
-   FE requests API to C/U/D TODO, using the stored JTW
-   BE authentication and authorization the request and validate the TODO content
-   BE C/U/D the TODO in the DB
-   FE after received the response from BE, show error message if any

#### Sign Out

-   User sign out the system
-   FE requests API to revoke the token

### Database design

#### Table: users

-   id
-   username
-   password (hashed)
-   active
-   deleted
-   created_at
-   updated_at

#### Table: todos

-   id
-   user_id
-   title
-   description
-   status
-   due_date
-   deleted
-   created_at
-   updated_at

#### Table: todo_categories

-   id
-   name
-   description

#### Table: todo_category_rlt

-   todo_id
-   category_id

#### Table: todo_notifs

-   id
-   todo_id
-   status
-   payload
-   deleted
-   created_at
-   updated_at

#### Table: roles

-   id
-   name
-   description
-   active
-   deleted
-   created_at
-   updated_at

#### Table: permissions

-   id
-   name
-   description
-   active
-   deleted
-   created_at
-   updated_at

#### Table: user_role

-   user_id
-   role_id
-   PRIMARY KEY (user_id, role_id)

#### Table: role_permission

-   role_id
-   permission_id
-   PRIMARY KEY (role_id, permission_id)

#### Table: jwt_tokens

-   id
-   user_id
-   token
-   expires_at
-   created_at
-   updated_at
-   revoked BOOLEAN DEFAULT FALSE

### API Design

-   POST /v0/users/create - create new user
-   POST /v0/users/signin - sign in
-   POST /v0/users/signout - sign out

-   POST /v0/todos/user/{userId} - create new TODO
-   GET /v0/todos/user/{userId} - get TODO list
-   UPDATE /v0/todos/user/{userId} - update a TODO
-   DELETE /v0/todos/user/{userId} - delete a TODO

-   GET /v0/todo_categories - get TODO categories list
-   POST /v0/todo_categories - create new TODO category
-   UPDATE /v0/todo_categories - update a TODO category
-   DELETE /v0/todo_categories - delete a TODO category

-   POST /v0/tokens/renew - renew a token

### Backend Services

-   Every 1min, check for the "due" TODO(s) and sending the reminder
-   Every 1min, check for the expired tokens and delete them
