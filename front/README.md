# MDD App (Monde de Dév) Angular Frontend Project (MVP (Minimum Viable Product))

Ce référentiel contient un frontend angulaire pour le projet MDD MVP.

Ce projet a été généré avec [Angular CLI](https://github.com/angular/angular-cli) version 19.2.11.

## Table of Contents

- [MDD App Angular Frontend Project](#mdd-app-monde-de-dév-angular-frontend-project-mvp-minimum-viable-product)
  - [Contents](#table-of-contents)
  - [Prerequisite Requirements](#prerequisite-requirements)
  - [Installation Guide](#installation-guide)
  - [Project Architecture](#project-architecture)

## Prerequisite Requirements

### 1. Node Package Manager (NPM)

**Installing NPM:**

Avant d'utiliser le frontend MDD Angular, assurez-vous que Node Package Manager (NPM) est installé sur votre système. NPM est essentiel à la gestion des dépendances et à l'exécution des scripts. Suivez les étapes ci-dessous :

1. Download et install [Node.js](https://nodejs.org/).
2. Confirm the successful installation by running the following commands in your terminal or command prompt:

```shell
node -v
npm -v
```

Vous devriez voir des versions pour Node.js et NPM.

### 2. Angular CLI

**Installing Angular CLI:**

Angular CLI est une interface de ligne de commande pour les applications Angular. Installez la dernière version globalement avec la commande suivante :

```shell
npm install -g @angular/cli
```

Confirmez l'installation réussie en exécutant :

```shell
ng --version
```

Vous devriez voir des informations sur la version Angular CLI installée

## Installation Guide

**Cloning the project:**

1. Clone this repository from GitHub: `git clone https://github.com/Diengsalla1990/Developpez-une-application-full-stack-complete.git`

2. Navigate to front folder

```shell
cd front
```

3. Install dependencies

```shell
npm install
```

**Ce projet fonctionne avec l'API fournie dans la partie Backend de l'application, n'oubliez pas de l'installer et de l'exécuter avant d'exécuter le Frontend.**

4. Front-end de lancement

```shell
npm start
```

## Project Architecture

```
├───app
│   ├───component
│   │   ├───button
│   │   ├───comment
│   │   ├───header
│   │   ├───nav
│   │   ├───post-card
│   │   └───theme-card
│   ├───core
│   │   ├───model
│   │   └───service
│   │       ├───api
│   │       │   ├───common
│   │       │   └───interface
│   │       │       ├───comment
│   │       │       │   ├───request
│   │       │       │   └───response
│   │       │       ├───post
│   │       │       │   ├───request
│   │       │       │   └───response
│   │       │       ├───subject
│   │       │       ├───user
│   │       │       │   ├───request
│   │       │       │   └───response
│   │       │       └───userAuth
│   │       │           ├───request
│   │       │           └───response
│   │       ├───route
│   │       └───session
│   ├───enviroment
│   ├───guard
│   ├───interceptor
│   ├───interface
│   ├───page
│   │   ├───article
│   │   ├───articles
│   │   ├───home
│   │   ├───login
│   │   ├───me
│   │   ├───new-article
│   │   ├───not-found
│   │   ├───register
│   │   └───theme
│   └───style
└───assets
```
