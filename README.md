# Système de Gestion de Tickets (SBIR)

## Description

Système complet de gestion de tickets développé en Java. Ce système permet de créer, assigner, suivre et gérer des tickets de support avec différents niveaux de priorité et des workflows de statuts.

Cette version inclut une **interface** intuitive pour l'utilisateur développé avec Java Swing.

## Architecture du Système

Le système est organisé autour de **trois piliers principaux** :
1. **Gestion des tickets** : Création, validation et cycle de vie des tickets
2. **Gestion des utilisateurs de base** : Création, suppression et permissions de modification de tickets
3. **Export en PDF** : Exportation en PDF des tickets et de tous ses informations

### Lancer le serveur Spring Boot

```cmd
cd server_springboot
mvn spring-boot:run
```

Ou avec Maven Wrapper :

```cmd
cd server_springboot
mvnw spring-boot:run
```

**Accès à la documentation :**
- Swagger UI : http://localhost:8080/swagger-ui.html
- API Docs : http://localhost:8080/v3/api-docs

### Lancer le client Java

**Installation du client :**

```cmd
cd client_java
mvn clean install
```

**Utilisation du client dans votre projet :**

Ajoutez la dépendance Maven dans votre `pom.xml` :

```xml
<dependency>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-java-client</artifactId>
  <version>4.0.0</version>
</dependency>
```

**Exemple d'utilisation :**

```java
import org.openapitools.client.ApiClient;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.TicketsApi;

ApiClient client = Configuration.getDefaultApiClient();
client.setBasePath("http://localhost:8080/api/v1");

TicketsApi ticketsApi = new TicketsApi(client);
List<TicketDTO> tickets = ticketsApi.getAllTickets();
```

## Ressources principales du projet existant

### Modèles de données

- src/Ticket.java - Entité principale représentant un ticket

- src/User.java - Entité représentant un utilisateur du système

- src/Description.java - Gestion des descriptions multimédia (texte, images, vidéos)

### Managers (logique métier)

- src/TicketManager.java - Gestion complète des tickets et orchestration

- src/statusManager.java - Gestion des statuts (OUVERT, ASSIGNÉ, TERMINÉ)

- src/PriorityManager.java - Gestion des priorités (BASSE, MOYENNE, HAUTE, CRITIQUE)

- src/commentManager.java - Gestion des commentaires sur les tickets

- src/AssignationManager.java - Gestion des assignations utilisateur-ticket

- src/descriptionManager.java - Gestion des descriptions et médias

- src/pdfExporter.java - Export PDF des tickets

### Validateurs et Créateurs

- src/ticketValidator.java - Validation des données de tickets

- src/userValidator.java - Validation des données utilisateurs

- src/DescriptionValidator.java - Validation des descriptions

- src/TicketCreator.java - Factory pour la création de tickets

- src/UserCreator.java - Factory pour la création d'utilisateurs

## Endpoints REST

### Gestion des tickets

GET    /api/tickets                      - Liste tous les tickets

GET    /api/tickets/{id}                 - Récupère un ticket spécifique

POST   /api/tickets                      - Crée un nouveau ticket

PUT    /api/tickets/{id}                 - Met à jour un ticket

DELETE /api/tickets/{id}                 - Supprime un ticket


GET    /api/tickets/findByStatus?status={status}      - Recherche par statut

GET    /api/tickets/findByPriority?priority={priority}  - Recherche par priorité

GET    /api/tickets/findByUser?user={userId} - Recherche par utilisateur

GET    /api/tickets/findByStatusUser?status={status}&user={userId} - Recherche par utilisateur et statut

GET    /api/tickets/findByTitle?title={title} - Recherche par titre

GET    /api/tickets/unassigned           - Tickets non assignés

GET    /api/tickets/critical             - Tickets critiques


### Actions sur les tickets

PUT    /api/tickets/{id}/assign          - Assigne un ticket à un utilisateur

PUT    /api/tickets/{id}/unassign        - Désassigne un ticket

PUT    /api/tickets/{id}/close           - Ferme/termine un ticket


### Gestion des commentaires

GET    /api/tickets/{id}/comments        - Liste les commentaires d'un ticket

POST   /api/tickets/{id}/comments        - Ajoute un commentaire à un ticket


### Gestion des images

GET    /api/tickets/{id}/image          - Voir les images d'un ticket

POST   /api/tickets/{id}/image          - Ajouter une image à un ticket


### Gestion des vidéos

GET    /api/tickets/{id}/videos        - Voir les vidéos d'un ticket

POST   /api/tickets/{id}/videos        - Ajouter une vidéo à un ticket


### Gestion des utilisateurs

GET    /api/users                        - Liste tous les utilisateurs

GET    /api/users/{id}                   - Récupère un utilisateur spécifique

POST   /api/users                        - Crée un nouvel utilisateur

PUT    /api/users/{id}                   - Met à jour un utilisateur

DELETE /api/users/{id}                   - Supprime un utilisateur


### Export

GET    /api/export/tickets/{id}/pdf      - Exporte un ticket en format PDF


## Versions

Prendre note qu'il y a quatre branches principales dans ce projet :

    1. La V1 est la partie 1 du laboratoire 2.

    2. La V2 est la partie 2 du laboratoire 2.

    3. Le V3 est la partie 3 du laboratoire 2. (laboratoire 3).

    4. Le V4 est le laboratoire 4 - Implémentation de l'API REST.

## Auteurs

Projet développé pour le cours GEI311 - Architecture Logicielle  

Il'aina Ratefinanahary et Samuel Brassard

UQAC - Automne 2025

## License

Ce projet est à usage éducatif uniquement.
