DROP USER jee8webapp@localhost;
CREATE USER jee8webapp@localhost IDENTIFIED BY 'password1';
SELECT user FROM mysql.user;
GRANT ALL PRIVILEGES ON charityDB.* TO jee8webapp@localhost;
FLUSH PRIVILEGES;
SHOW GRANTS FOR jee8webapp@localhost;