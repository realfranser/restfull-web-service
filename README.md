# BOOKNET - RESTFULL API

Este proyecto constituye la primera practica grupal de la asignatura Sistemas Orientados a Servicios (SOS)
de la facultad ETSIINF de la Universidad Politecnica de Madrid.
Este proyecto consiste en el desarrollo de un API de tipo REST para una red social llamada booknet,
esta red social es un punto de encuentro entre diferentes lectores, los cuales pueden compartir
los libros que han leido junto con una pequena resena, a su vez los usuarior pueden agregarse entre si
como amigos y poder ver la actividad reciente de los mismos.

## Informacion
| Infomacion: |  |   
| ----------- | --------
| Titulación  | Grado de Ingeniería Informática. Plan 09.
| Curso         | 2020/21
| Asignatura     | Sistemas Orientados a Servicios (SOS)
| Curso		 | 3º Curso
| Semestre    | 6º Semestre (Tarde)
| Proyecto    | Practica 1 - RESTFull API

## Autores
- Jesus Vallejo Collados [Matricula]
- Francisco Javier Serrano Arrese [180487]

## Indice

- [Introduccion](#introduccion)
- [Base de Datos](#base_de_datos)
    - [Subelemento](#subelemento)

## Base de datos

### Aspectos generales

Informacion de la base de datos en REST-VM:

- URI: `uri.booknet.com:3306`
- Nombre: `booknet`
- Usuario: `restuser`
- Contrasena: `restuser`

Tablas que conforman la base de datos:

- **USERS** (Tabla con los datos de los usuarios)
- **FRIENDSHIPS** (Tabla con las amistades entre usuarios)
- **BOOKS** (Tabla con la informacion de los libros)
- **READ_BOOKS** (Tabla con la informacion de las lecturas de los libros)

### Esquema E/R
Esquema de Entidad Relacion empleado en el diseno de la base de datos booknet:

![Entidad / Relacion](href img)

### Tabla USERS
Tabla con la informacion de los usuarios de booknet.

Cada usuario cuenta con los siguientes atributos:

| user_id | user_name | email | edad |
|:-:|:-:|:-:|:-:|
| INT | VARCHAR | VARCHAR | INT |

> **Primary Key:**
> user_id

### Tabla FRIENDSHIPS
Tabla con la informacion de las amistades entre los usuarios

Cada amistad cuenta con los siguientes atributos:

| friendship_id | user_id | friend_id |
|:-:|:-:|:-:|
| INT | INT | INT |
> **Primary Key:**
> friendship_id
> **Foreign Key:**
> user_id
> **Foreign Key:**
> friend_id

### Tabla BOOKS
Tabla con la informacion de los libros de la red booknet.

Cada libro cuenta con los siguientes atributos:

| isbn | book_name | authors_name | category |
|:-:|:-:|:-:|:-:|
| INT | VARCHAR | VARCHAR | VARCHAR |
> **Primary Key:**
> isbn

### Tabla READ_BOOKS
Tabla con la informacion de una lectura de un libro realizada por un usuario

Cada lectura cuenta con los siguientes atributos:

| read_id | user_id | isbn | user_rating | read_date |
|:-:|:-:|:-:|:-:|:-:|
| INT | INT | INT | INT | INT |

> **Primary Key:**
> read_id
> **Foreign Key:**
> user_id
> **Foreign Key:**
> isbn

### Script para crear la base de datos "booknet"
> **Nota:**
> Este script es capaz de funcionar a pesar de que la base de datos haya sido creada anteriormente.

Script booknet_db:

``` MYSQL

CREATE DATABASE  IF NOT EXISTS `booknet` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `booknet`;
-- MySQL dump 10.13  Distrib 5.5.22, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: booknet
-- ------------------------------------------------------
-- Server version	5.5.22-0ubuntu1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `books` (
  `isbn` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(50) NOT NULL,
  `authors_name` varchar(50) NOT NULL,
  `category` varchar(50) NOT NULL,
  PRIMARY KEY (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'libro1','autor1','categoria1'),(2,'libro2','autor2','categoria1'),(3,'libro3','autor3','categoria1'),(4,'libro4','autor4','categoria2'),(5,'libro5','autor4','categoria3');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friendship`
--

DROP TABLE IF EXISTS `friendship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friendship` (
  `friendship_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  PRIMARY KEY (`friendship_id`),
  UNIQUE KEY `user_id_2` (`user_id`,`friend_id`),
  KEY `user_id` (`user_id`),
  KEY `friend_id` (`friend_id`),
  CONSTRAINT `friendship_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `friendship_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friendship`
--

LOCK TABLES `friendship` WRITE;
/*!40000 ALTER TABLE `friendship` DISABLE KEYS */;
INSERT INTO `friendship` VALUES (1,1,2),(2,1,3),(3,2,1),(4,2,3),(5,3,1);
/*!40000 ALTER TABLE `friendship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `read_books`
--

DROP TABLE IF EXISTS `read_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `read_books` (
  `read_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `isbn` int(11) NOT NULL,
  `user_rating` int(11) NOT NULL,
  `read_date` int(8) NOT NULL,
  PRIMARY KEY (`read_id`),
  UNIQUE KEY `read_id_2` (`user_id`,`isbn`),
  KEY `user_id` (`user_id`),
  KEY `isbn` (`isbn`),
  CONSTRAINT `read_books_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `read_books_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `books` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `read_books`
--

LOCK TABLES `read_books` WRITE;
/*!40000 ALTER TABLE `read_books` DISABLE KEYS */;
INSERT INTO `read_books` VALUES (1,1,1,5,20001102),(2,1,2,7,20001106),(3,1,3,3,19990202),(4,2,1,6,20010806),(5,2,2,2,20051203),(6,3,4,8,20091005),(7,3,5,4,19880407);
/*!40000 ALTER TABLE `read_books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `edad` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'user1','mail1@mail.com',50),(2,'user2','mail2@mail.com',20),(3,'user3','mail3@mail.com',22),(4,'user4','mail4@mail.com',34);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-21 16:01:09


```
hola
## Diseno de las URIs

> **Nota:**
> El simbolo '~' representa en nuestro caso: http://localhost8080/booknet/api

### Users

#### **[POST]** ~/users
| URI        		| http://localhost:8080/booknet/api/v1/users/ | 
| ------------- 	|-------------			|
| Descripción       | Permite crear un usuario, enviando una estructura JSON del tipo User. |
| Método      	| POST 					    |
| Cadena de consulta| Ninguna                      |
| Cuerpo 			| POX (users/user+json)	|  
| Devuelve      	| <ul><li>``201`` : Created y cabecera Location</li><li>``406`` : Not Acceptable</li><li>``415`` : Unsupported Media Type</li></ul>|  

#### **[GET]** ~/users/{user_id}
| URI        		| http://localhost:8080/booknet/api/v1/users/{user_id} |
| ------------- 	|-------------	| 
| Descripción       | Devuelve en formato JSON la informacion del usuario con user_id con primary key. |
| Método      	    | GET 			|
| Cadena de consulta| <ul><li>filter\_by\_id= búsqueda por user_id</li></ul>
| Cuerpo 			| Ninguno		|  
| Devuelve      	| <ul><li>``200`` : OK y POX (usuarios/usuario+json)</li><li>``404`` : Not Found</li></ul>|

### **[PUT]** ~/users/{user_id}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id} 									| 
| ------------- 	|-------------			|
| Descripción       | Modifica los atributos de un usuario                    |
| Método      	| PUT 						|
| Cadena de consulta| Ninguna                      |
| Cuerpo 			| POX (users/user+json)	|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``201`` : Created y cabecera Location *(1)</li><li>``406`` : Not Acceptable</li><li>``415`` : Unsupported Media Type</li></ul>|

#### **[DELETE]** ~/users/{user_id}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id} | 
| ------------- 	|-------------			|
| Descripción       | Elimina a un usuario de la red |
| Método      	| DELETE 					|
| Cadena de consulta| Ninguna                      |
| Cuerpo 			| Ninguno|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li><li>``503`` : Service Unavailable</li></ul>|  

#### **[GET]** ~/users
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/ |
| ------------- 	|-------------	| 
| Descripción       | Devuelve una lista de usuarios de la red que contenga en su nombre el parametro user_name. En caso de no proporcionar este parametro, se devuelve toda la red de usuarios. |
| Método      	    | GET 			|
| Cadena de consulta| <ul><li>filter\_by\_text= búsqueda por nombre</li></ul>
| Cuerpo 			| Ninguno		|  
| Devuelve      	| <ul><li>``200`` : OK y POX (usuarios/usuario+xml)</li><li>``404`` : Not Found</li></ul>|

#### **[POST]** ~/users/{user_id}
| URI        		| http://localhost:8080/booknet/api/v1/users/{user_id} | 
| ------------- 	|-------------			|
| Descripción       | Permite anadir un libro leido por un usuario a la red, enviando una estructura JSON del tipo read_book. |
| Método      	| POST 					    |
| Cadena de consulta| Ninguna                      |
| Cuerpo 			| POX (users/user+json)	|  
| Devuelve      	| <ul><li>``201`` : Created y cabecera Location</li><li>``406`` : Not Acceptable</li><li>``415`` : Unsupported Media Type</li></ul>|  

#### **[DELETE]** ~/users/{user_id}/read_books/{isbn}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id}/read_books/{isbn} | 
| ------------- 	|-------------			|
| Descripción       | Elimina la lectura de un libro por parte de un usuario.|
| Método      	| DELETE 					|
| Cadena de consulta| Ninguna                      |
| Cuerpo 			| Ninguno|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li><li>``503`` : Service Unavailable</li></ul>|  

### **[PUT]** ~/users/{user_id}/read_books/{isbn}
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id}/read_books/{isbn}							| 
| ------------- 	|-------------			|
| Descripción       | Modifica los atributos de la lectura de un libro |
| Método      	| PUT 						|
| Cadena de consulta| Ninguna                      |
| Cuerpo 			| POX (users/user+json)	|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``201`` : Created y cabecera Location *(1)</li><li>``406`` : Not Acceptable</li><li>``415`` : Unsupported Media Type</li></ul>|

#### **[GET]** ~/users/{user_id}/readings
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id}/readings |
| ------------- 	|-------------	| 
| Descripción       | Permite consultar los ultimos libros leidos por un usuario en funcion de una fecha y con los criterios de paginacion adecuados. |
| Método      	    | GET 			|
| Cadena de consulta| <ul><li>filter\_by\_text= búsqueda por fecha</li></ul>
| Cuerpo 			| Ninguno		|  
| Devuelve      	| <ul><li>``200`` : OK y POX (usuarios/usuario+json)</li><li>``404`` : Not Found</li></ul>|

#### **[POST]** ~/users/{user_id}/friends/
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{username}/friends/  |
| ------------- 	|-------------			|
| Descripción       |  Crea una amistad de un usuario con otro     |
| Método      	    | POST 					|
| Cadena de consulta| Ninguna                    |
| Cuerpo 			| Ninguno	|  
| Devuelve      	| <ul><li>``201`` : Created y cabecera Location</li><li>``302`` : Found</li><li>``404`` : Not Found</li></ul>|

#### **[DELETE]** ~/users/{user_id}/friends/
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id}/friends    | 
| ------------- 	|-------------			|
| Descripción       | Elimina amistad de un usuario con otro.   |
| Método      	| DELETE 					|
| Cadena de consulta| Ninguna                      |
| Cuerpo 			| Ninguno|  
| Devuelve      	| <ul><li>``200`` : OK</li><li>``404`` : Not Found</li><li>``503`` : Service Unavailable</li></ul>|  

#### **[GET]** ~/users/{user_id}/friends
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id}/friends |
| ------------- 	|-------------	| 
| Descripción       | Permite consultar los amigos de un usuario en funcion de un parametro nombre, en caso de que este no se introduzca, se devolvera la lista completa de amigos de un usuario. |
| Método      	    | GET 			|
| Cadena de consulta| <ul><li>filter\_by\_text= búsqueda por nombre</li></ul>
| Cuerpo 			| Ninguno		|  
| Devuelve      	| <ul><li>``200`` : OK y POX (usuarios/usuario+json)</li><li>``404`` : Not Found</li></ul>|

#### **[GET]** ~/users/{user_id}/friendsreadings
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id}/friendsreadings |
| ------------- 	|-------------	| 
| Descripción       | Permite consultar las lecturas de libros por parte de los amigos de un usuario en funcion de la fecha de lectura. |
| Método      	    | GET 			|
| Cadena de consulta| <ul><li>filter\_by\_text= búsqueda por fecha</li></ul>
| Cuerpo 			| Ninguno		|  
| Devuelve      	| <ul><li>``200`` : OK y POX (usuarios/usuario+json)</li><li>``404`` : Not Found</li></ul>|

#### **[GET]** ~/users/{user_id}/friendsrecomendations
| URI        		| http://localhost:8080/UPMSocial/api/v1/users/{user_id}/friendsrecomendations |
| ------------- 	|-------------	| 
| Descripción       | Permite consultar las recomendaciones de libros por parte de los amigos de un usuario en funcion de la fecha de lectura, el rating y el nombre del autor. |
| Método      	    | GET 			|
| Cadena de consulta| <ul><li>filter\_by\_text= búsqueda por nombre</li></ul>
| Cuerpo 			| Ninguno		|  
| Devuelve      	| <ul><li>``200`` : OK y POX (usuarios/usuario+xml)</li><li>``404`` : Not Found</li></ul>|