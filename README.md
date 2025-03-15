# Système de Gestion de Cabinet Médical

Cette application JavaFX est conçue pour la gestion d'un cabinet médical, incluant la gestion des dossiers patients et des consultations.

## Aperçu du Projet

Cette application a été développée pour aider les cabinets médicaux à gérer leurs patients et consultations. Elle offre les fonctionnalités suivantes :

- Ajout, modification et suppression des dossiers patients
- Enregistrement des consultations liées aux patients
- Recherche de patients
- Visualisation de l'historique des consultations

## Technologies Utilisées

- Java 17
- JavaFX pour l'interface utilisateur
- FXML et Scene Builder pour la conception de l'interface
- Base de données MySQL pour le stockage des données
- Pattern DAO (Data Access Object) pour les opérations sur la base de données

## Architecture du Projet

L'application suit une architecture en couches :

1. **Couche UI** : Vues FXML JavaFX et contrôleurs
2. **Couche Service** : Logique métier et classes de service
3. **Couche d'Accès aux Données** : Classes DAO pour les opérations sur la base de données
4. **Couche Entité** : Classes modèles représentant les entités métier

### Structure des Packages

- `ma.enset.gestionconsultationbdcc` : Package principal de l'application
- `ma.enset.gestionconsultationbdcc.controllers` : Contrôleurs JavaFX
- `ma.enset.gestionconsultationbdcc.dao` : Classes d'accès aux données
- `ma.enset.gestionconsultationbdcc.entities` : Entités métier
- `ma.enset.gestionconsultationbdcc.service` : Services métier
- `resources/ma/enset/gestionconsultationbdcc/views` : Fichiers FXML pour l'interface utilisateur

![image](https://github.com/user-attachments/assets/c2259750-983f-485c-973e-2bd6090062a2)


## Configuration de la Base de Données

L'application se connecte à une base de données MySQL. Voici comment la base de données est structurée :

### Tables de la Base de Données

1. **patients** : Stocke les informations des patients
   - id (clé primaire)
   - nom
   - prenom
   - tel

2. **consultations** : Stocke les consultations
   - id (clé primaire)
   - date_consultation
   - description
   - patient_id (clé étrangère référençant la table patients)

  ![image](https://github.com/user-attachments/assets/b07af6d6-7d52-4850-9eab-d9b0ea391017)


### Configuration de la Connexion

La connexion à la base de données est gérée dans nos classes DAO. 

## Fonctionnalités Principales

### Gestion des Patients
* Ajouter un nouveau patient
* Modifier les informations d'un patient existant
* Supprimer un patient
* Rechercher des patients par nom ou numéro de téléphone

### Gestion des Consultations
* Enregistrer une nouvelle consultation
* Lier une consultation à un patient
* Consulter l'historique des consultations
* Supprimer une consultation

## Interface de Gestion des Patients

https://github.com/user-attachments/assets/3b5b0ef4-cde9-4e3b-b56f-51687ce18161

## Interface de Gestion des Consultations
![image](https://github.com/user-attachments/assets/fbb12039-d076-4819-80cf-2ac4c0dcb3e8)

## Auteur
### HANANE NAHIM

