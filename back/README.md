# MDD App Monde de Dev Spring Boot API Projet

Ce référentiel contient une API Spring Boot Backend pour le projet d'application MDD.

## Table of Contents

- [MDD App Monde de Dev Spring Boot API Projet](#mdd-app-monde-de-dev-spring-boot-api-projet)
  - [Contents](#table-of-contents)
  - [Prerequisite Requirements](#prerequisite-requirements)
  - [Configuration Settings](#configuration-settings)
  - [Installation Guide](#installation-guide)
  - [Project Architecture](#project-architecture)
  - [Security and Authentication](#security-and-authentication)

## Prerequisite Requirements

Avant de commencer à configurer l'API Spring Boot de l'application Yoga, assurez-vous que votre système satisfait aux conditions préalables suivantes :


- **Java Development Kit (JDK):** Téléchargez et installez la dernière version du [JDK](https://adoptopenjdk.net/) adaptée à votre plateforme.

- **Apache Maven:** Ce projet s'appuie sur [Maven](https://maven.apache.org/) pour la gestion des dépendances et la compilation. Installez Maven pour faciliter la configuration et la maintenance du projet.

- **MySQL:** Ce projet repose sur une base de données MySQL. Si vous n'avez pas encore installé MySQL, consultez le guide d'installation [ici](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/).

## Configuration Settings

### 1. Java
**Configuration de la variable d'environnement Java** :

Assurez-vous que la variable d'environnement Java est correctement configurée sur votre système. Cette variable est essentielle au bon fonctionnement des applications Java. Suivez les étapes correspondant à votre système d'exploitation :

#### Windows:

1. Ouvrez les Propriétés système.
2. Accédez à l'onglet « Avancé ».
3. Cliquez sur le bouton « Variables d'environnement ».
4. Sous « Variables système », faites défiler la page jusqu'à la variable « Chemin » et cliquez sur « Modifier ».
5. Ajoutez le chemin d'accès au répertoire binaire de votre JDK (par exemple, « C:\Program Files\Java\jdk[VERSION]\bin ») à la liste de valeurs. Assurez-vous de le séparer des autres entrées par un point-virgule.
6. Cliquez sur « OK » pour enregistrer vos modifications et exécutez la commande suivante :

```shell
java -version
```
Vous devriez voir des informations sur la version Java installée.

### 2. MySQL Database

Suivez ces étapes pour configurer MySQL Workbench pour votre application Java :

1. Ouvrez MySQL Workbench.
2. Connectez-vous à votre instance de serveur MySQL.
3. Créez une nouvelle base de données pour votre application :


```sql
CREATE DATABASE `dbName`;
```

Si vous cherchez de l'inspiration pour le nom de votre base de données, pensez à utiliser « mdd », c'est un choix judicieux !

4. Exécutez le fichier SQL « script.sql » qui se trouve dans le dossier ressources situé à la racine de l'application Front/Back (root\resources\sql\initSqlDbAndPopulate.sql).

```sql
source C:\folder\...\root\ressources\sql\initSqlDbAndPopulate.sql
```

## Installation Guide

**Cloning the project:**

1. Clone this repository from GitHub: `git clone  https://github.com/Diengsalla1990/Developpez-une-application-full-stack-complete.git`

**Configurer les variables d'environnement dans le fichier application.properties**

### 2. Configuring the `application.properties` file:

Le projet inclut un fichier « application.properties » situé dans le dossier « src/main/resources/ ». Voici les éléments de variables d'environnement à configurer :

```properties
# Data source configuration (MySQL)
spring.datasource.url=jdbc:mysql://localhost:{port}/{dbName}?allowPublicKeyRetrieval=true
spring.datasource.username={dbUsername}
spring.datasource.password={dbPassword}
```

- Le port par défaut MySQL « spring.datasource.url » est généralement « 3306 ».
- Le nom MySQL « {dbName} » de « spring.datasource.url » est le nom de la base de données que vous avez créée lors de la configuration de MySQL.
- Les identifiants MySQL « spring.datasource.username » et « spring.datasource.password » sont vos identifiants MySQL.

```properties
# Secret key for JWT (Json Web Token)
oc.app.jwtSecret={jwtKey}
```

Le `jwtKey` doit contenir votre clé de chiffrement JWT, qui sera utilisée à des fins d'authentification. Utilisez une clé robuste de 256 bits et préservez sa confidentialité.
3. Install the application by running `mvn clean install` in the project directory.

4. Exécutez l'application à l'aide de votre IDE ou en exécutant `mvn spring-boot:run` dans le répertoire du projet.

5. Vous pouvez également utiliser Postman pour tester les appels API. La collection Postman se trouve dans le `root\resources\postman` dossier. (vous devrez utiliser un jeton porteur qui sera renvoyé après un appel de connexion réussi à l'API, puis l'utiliser pour toutes les autres demandes aux points de terminaison de l'API autres que ceux d'authentification).


## Project Architecture

Le projet adhère à une architecture en couches conventionnelle (Contrôleur/Service/Référentiel d'API de persistance Java) pour garantir la modularité et la maintenabilité de la base de code, en s'alignant sur les meilleures pratiques de l'industrie.

```
├───main
│   ├───java
│   │   └───com
│   │       └───openclassrooms
│   │           └───starterjwt
│   │               ├───controllers
│   │               ├───dto
│   │               ├───exception
│   │               ├───mapper
│   │               ├───models
│   │               ├───payload
│   │               │   ├───request
│   │               │   └───response
│   │               ├───repository
│   │               ├───security
│   │               │   ├───jwt
│   │               │   └───services
│   │               └───services
│   └───resources
└───test
    └───java
        └───com
            └───openclassrooms
                └───starterjwt
                    ├───integration
                    └───unit
                        ├───controllers
                        ├───mapper
                        ├───models
                        ├───payload
                        │   ├───request
                        │   └───response
                        ├───security
                        │   ├───jwt
                        │   └───services
                        └───services
```

## Security and Authentication

L'authentification est gérée par Spring Security avec JWT. Toutes les routes nécessitent une authentification, sauf celles liées à la création ou à la connexion d'un compte. Les mots de passe sont cryptés pour garantir leur stockage sécurisé dans la base de données.

