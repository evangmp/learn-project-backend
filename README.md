works with docker BUT :
if you can do a query at the database, no problem, initialize the project and if the table role appears do : 
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

else or if the table role doesn't appears : 
create the DB with pgadmin4 and set in the project the good name, username, password, etc.
Launch the projet with mvn spring-boot:run, with pgadmin4 launch  the query : 
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
then (normally) you can launch with docker compose at the next launch 
