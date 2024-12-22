 -- Create a database:

     CREATE DATABASE synchronyProject;

   -- Add a table:

     CREATE TABLE user (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         name VARCHAR(255) NOT NULL,
         email VARCHAR(255) UNIQUE NOT NULL
     );