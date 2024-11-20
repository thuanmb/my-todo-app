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
