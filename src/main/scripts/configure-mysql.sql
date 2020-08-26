# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

CREATE DATABASE md_dev;
CREATE DATABASE md_prod;

CREATE USER 'md_dev_user'@'localhost' IDENTIFIED BY 'md';
CREATE USER 'md_prod_user'@'localhost' IDENTIFIED BY 'md';
CREATE USER 'md_dev_user'@'%' IDENTIFIED BY 'md';
CREATE USER 'md_prod_user'@'%' IDENTIFIED BY 'md';

GRANT SELECT ON md_dev.* to 'md_dev_user'@'localhost';
GRANT INSERT ON md_dev.* to 'md_dev_user'@'localhost';
GRANT DELETE ON md_dev.* to 'md_dev_user'@'localhost';
GRANT UPDATE ON md_dev.* to 'md_dev_user'@'localhost';
GRANT SELECT ON md_prod.* to 'md_prod_user'@'localhost';
GRANT INSERT ON md_prod.* to 'md_prod_user'@'localhost';
GRANT DELETE ON md_prod.* to 'md_prod_user'@'localhost';
GRANT UPDATE ON md_prod.* to 'md_prod_user'@'localhost';
GRANT SELECT ON md_dev.* to 'md_dev_user'@'%';
GRANT INSERT ON md_dev.* to 'md_dev_user'@'%';
GRANT DELETE ON md_dev.* to 'md_dev_user'@'%';
GRANT UPDATE ON md_dev.* to 'md_dev_user'@'%';
GRANT SELECT ON md_prod.* to 'md_prod_user'@'%';
GRANT INSERT ON md_prod.* to 'md_prod_user'@'%';
GRANT DELETE ON md_prod.* to 'md_prod_user'@'%';
GRANT UPDATE ON md_prod.* to 'md_prod_user'@'%';
