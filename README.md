# Système de Gestion de Tickets (SBIR)

## Description

Système complet de gestion de tickets (Service Bureau Issue Reporting) développé en Java. Ce système permet de créer, assigner, suivre et gérer des tickets de support avec différents niveaux de priorité et des workflows de statuts.

## Architecture du Système

Le système est organisé autour de **trois piliers principaux** :
1. **Gestion des tickets** : Création, validation et cycle de vie des tickets
2. **Gestion des utilisateurs** : Création, validation et permissions
3. **Coordination centrale** : TicketManager qui orchestre tous les managers

### Principe de Délégation

TicketManager est le point d'entrée unique qui coordonne tous les managers spécialisés. Chaque manager a une responsabilité unique et bien définie. Les validators sont utilisés par les creators et managers pour garantir l'intégrité des données. Display reste isolé et reçoit simplement les données formatées à afficher.

### Entités Principales

#### **Ticket**
- **Attributs** :
  - `ticketID` : Identifiant unique
  - `title` : Titre du ticket
  - `description` : Description (String pour compatibilité)
  - `status` : Statut actuel (OUVERT, ASSIGNÉ, VALIDATION, TERMINÉ)
  - `priority` : Priorité (BASSE, MOYENNE, HAUTE, CRITIQUE)
  - `creationDate` : Date de création
  - `updateDate` : Dernière modification
  - `assignedUserId` : ID de l'utilisateur assigné

#### **Description**
- **Attributs** :
  - `textContent` : Contenu textuel
  - `imagePaths` : Liste des chemins d'images
  - `videoPaths` : Liste des chemins de vidéos
  - `creationDate`, `lastModified` : Dates de gestion
- **Méthodes** : Gestion complète des médias et du contenu

#### **User**
- **Attributs** :
  - `userID`, `name`, `email`, `role`
- **Rôles** : USER, DEVELOPER, ADMIN
- **Méthodes** : `isAdmin()`, `canAssignTickets()`, `canCloseTickets()`

### Validateurs

#### **ticketValidator**
- Validation complète des tickets (titre, statut, priorité)
- Longueurs min/max configurables
- Liste d'erreurs détaillées

#### **DescriptionValidator**
- Validation des contenus textuels
- Vérification des formats de médias (images/vidéos)
- Limites de taille configurables

#### **userValidator**
- Validation des utilisateurs (nom, email, rôle)
- Regex pour format email
- Vérification des rôles autorisés

### Factories

#### **TicketCreator**
- Création de tickets avec validation
- Génération d'IDs uniques
- Support de différentes surcharges (simple, avec description, avec priorité)

#### **UserCreator**
- Création d'utilisateurs avec validation
- Rôle par défaut assignable
- Génération d'IDs uniques

### Managers

#### **statusManager**
- Gestion des transitions de statuts
- Règles de workflow strictes
- Vérification des permissions utilisateur

#### **PriorityManager**
- Gestion des priorités
- Comparaison et tri par priorité
- Identification des tickets critiques

#### **commentManager**
- Gestion complète des commentaires
- Association commentaires-auteurs-dates
- Historique formaté

#### **AssignationManager**
- Assignation/désassignation de tickets
- Suivi de la charge de travail
- Statistiques d'assignation

#### **descriptionManager**
- Gestion des descriptions
- Ajout/retrait de médias
- Validation intégrée
- Export PDF (simulation)

### Affichage

#### **Display**
- Affichage formaté des tickets
- Multiples vues (par statut, priorité, utilisateur)
- Statistiques système
- Export PDF (simulation)

### Classe Centrale

#### **TicketManager**
- Point d'entrée principal du système
- Intégration de tous les managers
- API unifiée pour toutes les opérations
- Méthodes de recherche et filtrage

## Utilisation

### Compilation

```bash
javac -d bin src/*.java
```

### Exécution

```bash
java -cp bin Main
```

## Workflow des Statuts

```
OUVERT → ASSIGNÉ → VALIDATION → TERMINÉ
   ↓        ↓          ↓   ↓
   └────────┴──────────┴───┘
         (retour possible)
   
Fermeture directe possible :
OUVERT → TERMINÉ (sans assignation)
OUVERT → VALIDATION (possible mais inutile)
ASSIGNÉ → VALIDATION (obligatoire avant TERMINÉ)
```

### Règles de Transition

- **OUVERT** : Peut aller vers ASSIGNÉ, VALIDATION (inutile) ou TERMINÉ (fermeture directe)
- **ASSIGNÉ** : Peut aller vers OUVERT ou VALIDATION (DOIT passer par VALIDATION avant TERMINÉ)
- **VALIDATION** : Peut aller vers OUVERT, ASSIGNÉ ou TERMINÉ (après validation de l'équipe)
- **TERMINÉ** : État final, aucune transition possible

### Cas d'Usage

1. **Cycle normal** : OUVERT → ASSIGNÉ → VALIDATION → TERMINÉ
2. **Fermeture directe depuis OUVERT** : Problème non prioritaire ou spécifique à l'utilisateur
3. **Retour en arrière** : VALIDATION → ASSIGNÉ/OUVERT si correction nécessaire

## Permissions

### Utilisateur (USER/DEVELOPER)
- Créer des tickets
- Voir tous les tickets
- Mettre à jour ses tickets assignés (ASSIGNÉ → VALIDATION)
- Ajouter des commentaires

### Administrateur (ADMIN)
- Toutes les permissions utilisateur
- Assigner/desassigner des tickets
- Changer tous les statuts (notamment VALIDATION → TERMINE)
- Changer les priorites
- Supprimer des commentaires

## Priorites

1. **BASSE** : Probleme mineur sans urgence
2. **MOYENNE** : Probleme a traiter dans un delai raisonnable
3. **HAUTE** : Probleme important necessitant attention rapide
4. **CRITIQUE** : Probleme bloquant necessitant resolution immediate

## Exemples de Code

### Création d'un Utilisateur

```java
UserCreator userCreator = new UserCreator();
User admin = userCreator.createUser("Alice", "alice@example.com", "ADMIN");
User developer = userCreator.createUser("Bob", "bob@example.com", "DEVELOPER");
```

### Création d'un Ticket

```java
TicketCreator ticketCreator = new TicketCreator();
Ticket ticket = ticketCreator.createTicket(
    "Probleme de connexion", 
    "Impossible de se connecter au systeme", 
    admin, 
    "HAUTE"
);
```

### Utilisation du TicketManager

```java
TicketManager manager = new TicketManager();

// Ajouter un ticket
manager.addTicket(ticket);

// Assigner un ticket
manager.assignTicket(ticket.getTicketID(), developer, admin);

// Ajouter un commentaire
manager.addCommentToTicket(ticket.getTicketID(), "Probleme identifie", developer);

// Changer le statut
manager.updateTicketStatus(ticket.getTicketID(), "VALIDATION", developer);

// Afficher le ticket
manager.displayTicket(ticket.getTicketID());

// Afficher les statistiques
manager.displaySystemStatistics();
```

### Gestion des Descriptions

```java
descriptionManager descManager = new descriptionManager();
Description desc = descManager.createDescription("Description detaillee du probleme");

// Ajouter des medias
descManager.addImageToDescription(desc, "media/images/screenshot.png");
descManager.addVideoToDescription(desc, "media/videos/demo.mp4");

// Afficher le resume
System.out.println(descManager.getDescriptionSummary(desc));
```

## Fonctionnalités Principales

- Creation et gestion de tickets  
- Workflow de statuts avec transitions controlees  
- Systeme de priorites a 4 niveaux  
- Gestion des commentaires avec historique  
- Assignation de tickets avec suivi de charge  
- Validation complete des donnees  
- Affichage formate et statistiques  
- Export PDF (simulation)  
- Recherche et filtrage de tickets  
- Systeme de permissions base sur les roles  

## Structure des Fichiers

```
Lab2-GEI311-SBIR/
├── bin/                        # Fichiers compilés (.class)
├── media/                      # Médias et exports
│   ├── images/                # Captures d'écran des tickets
│   ├── videos/                # Vidéos de démonstration
│   └── exports/               # Exports PDF des tickets
├── src/                        # Code source
│   ├── Ticket.java            # Entité ticket
│   ├── User.java              # Entité utilisateur (avec permissions)
│   ├── Description.java       # Entité description
│   ├── ticketValidator.java   # Validateur de tickets
│   ├── userValidator.java     # Validateur d'utilisateurs
│   ├── DescriptionValidator.java # Validateur de descriptions
│   ├── TicketCreator.java     # Factory pour tickets
│   ├── UserCreator.java       # Factory pour utilisateurs
│   ├── statusManager.java     # Gestionnaire de statuts
│   ├── PriorityManager.java   # Gestionnaire de priorités
│   ├── commentManager.java    # Gestionnaire de commentaires
│   ├── AssignationManager.java # Gestionnaire d'assignations
│   ├── descriptionManager.java # Gestionnaire de descriptions
│   ├── Display.java           # Gestionnaire d'affichage
│   ├── TicketManager.java     # Gestionnaire central
│   └── Main.java              # Point d'entrée
└── README.md                   # Cette documentation
```

## Validation

### Tickets
- Titre : 5-200 caracteres
- Statut : OUVERT, ASSIGNE, VALIDATION, TERMINE
- Priorite : BASSE, MOYENNE, HAUTE, CRITIQUE

### Utilisateurs
- Nom : 2-100 caracteres
- Email : format valide (regex)
- Role : USER, DEVELOPER, ADMIN

### Descriptions
- Texte : max 5000 caracteres
- Images : max 10, formats .jpg, .jpeg, .png, .gif
- Videos : max 5, formats .mp4, .avi, .mov

## Details des Classes

### Entites

#### **Ticket**
Entite centrale representant un probleme signale.

**Attributs:**
- `ticketID: int` - Identifiant unique
- `title: String` - Titre du ticket
- `description: String` - Description du probleme
- `status: String` - Statut actuel (OUVERT, ASSIGNE, VALIDATION, TERMINE)
- `priority: String` - Priorite (BASSE, MOYENNE, HAUTE, CRITIQUE)
- `creationDate: Date` - Date de creation
- `updateDate: Date` - Derniere modification
- `assignedUserId: int` - ID utilisateur assigne

**Methodes:**
- `Ticket(int, String, String, String, String)` - Constructeur
- Getters/Setters pour tous les attributs
- `assignTo(int)` - Assigner un utilisateur
- `updateStatus(String)` - Changer le statut
- `addComment(String)` - Ajouter commentaire
- `toString()` - Representation textuelle

#### **Description**
Encapsule le contenu detaille avec texte, images et videos.

**Attributs:**
- `textContent: String` - Contenu textuel
- `imagePaths: List<String>` - Liste chemins images
- `videoPaths: List<String>` - Liste chemins videos
- `creationDate: Date` - Date creation
- `lastModified: Date` - Derniere modification

**Methodes:**
- `Description()` - Constructeur vide
- `Description(String)` - Constructeur avec texte
- `addImagePath(String)` - Ajouter image
- `removeImagePath(String)` - Retirer image
- `addVideoPath(String)` - Ajouter video
- `removeVideoPath(String)` - Retirer video
- `updateLastModified()` - MAJ date
- `hasContent()` - Verifie si texte present
- `hasImages()` - Verifie si images presentes
- `hasVideos()` - Verifie si videos presentes
- `getContentSummary()` - Resume du contenu
- Getters/Setters standards

#### **User**
Represente les utilisateurs avec permissions.

**Attributs:**
- `userID: int` - Identifiant unique
- `name: String` - Nom complet
- `email: String` - Adresse email
- `role: String` - Role (USER, DEVELOPER, ADMIN)

**Methodes:**
- `User(int, String, String, String)` - Constructeur
- Getters/Setters pour tous les attributs
- `isAdmin(): boolean` - Verifie si admin
- `canAssignTickets(): boolean` - Peut assigner
- `canCloseTickets(): boolean` - Peut fermer
- `toString()` - Representation textuelle

### Validateurs

#### **ticketValidator**
Valide tous les aspects d'un ticket.

**Attributs:**
- `minTitleLength: int` - Longueur min titre (5)
- `maxTitleLength: int` - Longueur max titre (200)
- `validStatuses: List<String>` - Statuts acceptes
- `validPriorities: List<String>` - Priorites acceptees

**Methodes:**
- `ticketValidator()` - Constructeur
- `validateTicket(Ticket): boolean` - Validation complete
- `validateTitle(String): boolean` - Valide titre
- `validateStatus(String): boolean` - Valide statut
- `validatePriority(String): boolean` - Valide priorite
- `validateCreator(User): boolean` - Valide createur
- `validateDates(Date, Date): boolean` - Valide dates
- `getValidationErrors(Ticket): List<String>` - Liste erreurs
- `isValidForCreation(Ticket): boolean` - Valide creation
- `isValidForUpdate(Ticket): boolean` - Valide MAJ
- Getters pour limites

#### **DescriptionValidator**
Valide le contenu des descriptions.

**Attributs:**
- `maxTextLength: int` - Longueur max texte (5000)
- `maxImagesCount: int` - Nombre max images (10)
- `maxVideosCount: int` - Nombre max videos (5)
- `allowedImageFormats: List<String>` - Formats images acceptes
- `allowedVideoFormats: List<String>` - Formats videos acceptes

**Methodes:**
- `DescriptionValidator()` - Constructeur
- `validateDescription(Description): boolean` - Validation complete
- `validateTextContent(String): boolean` - Valide texte
- `validateImagePath(String): boolean` - Valide chemin image
- `validateVideoPath(String): boolean` - Valide chemin video
- `validateImageFormat(String): boolean` - Valide format image
- `validateVideoFormat(String): boolean` - Valide format video
- `validateImageCount(int): boolean` - Valide nombre images
- `validateVideoCount(int): boolean` - Valide nombre videos
- `getValidationErrors(Description): List<String>` - Liste erreurs
- Getters pour formats et limites

#### **userValidator**
Valide les donnees utilisateurs.

**Attributs:**
- `emailPattern: String` - Regex validation email
- `validRoles: List<String>` - Roles acceptes
- `minNameLength: int` - Longueur min nom (2)
- `maxNameLength: int` - Longueur max nom (100)

**Methodes:**
- `userValidator()` - Constructeur
- `validateUser(User): boolean` - Validation complete
- `validateName(String): boolean` - Valide nom
- `validateEmail(String): boolean` - Valide email
- `validateRole(String): boolean` - Valide role
- `validateUserID(int): boolean` - Valide ID
- `getValidationErrors(User): List<String>` - Liste erreurs
- `isValidEmailFormat(String): boolean` - Verifie format email
- Getters pour roles et limites

### Factories

#### **TicketCreator**
Centralise la creation des tickets.

**Attributs:**
- `nextTicketID: int` - Compteur IDs
- `ticketValidator: ticketValidator` - Validateur
- `descriptionValidator: DescriptionValidator` - Validateur descriptions

**Methodes:**
- `TicketCreator()` - Constructeur
- `createTicket(String, User): Ticket` - Creation simple
- `createTicket(String, String, User): Ticket` - Avec description
- `createTicket(String, String, User, String): Ticket` - Avec priorite
- `generateTicketID(): int` - Genere ID unique
- `initializeTicket(Ticket)` - Initialise dates/statut
- `validateBeforeCreation(String, String, User): boolean` - Valide avant creation

#### **UserCreator**
Factory pour creation utilisateurs.

**Attributs:**
- `nextUserID: int` - Compteur IDs
- `userValidator: userValidator` - Validateur

**Methodes:**
- `UserCreator()` - Constructeur
- `createUser(String, String): User` - Role par defaut
- `createUser(String, String, String): User` - Avec role
- `generateUserID(): int` - Genere ID unique
- `assignDefaultRole(): String` - Role par defaut (USER)
- `validateBeforeCreation(String, String, String): boolean` - Valide avant creation

### Managers

#### **TicketManager**
Classe centrale coordinatrice.

**Attributs:**
- `allTickets: List<Ticket>` - Tous les tickets
- `statusManager: statusManager` - Gestionnaire statuts
- `priorityManager: PriorityManager` - Gestionnaire priorites
- `commentManager: commentManager` - Gestionnaire commentaires
- `assignationManager: AssignationManager` - Gestionnaire assignations
- `descriptionManager: descriptionManager` - Gestionnaire descriptions
- `display: Display` - Gestionnaire affichage

**Methodes:**
- `TicketManager()` - Constructeur
- `addTicket(Ticket)` - Ajoute ticket
- `removeTicket(int): boolean` - Retire ticket
- `getTicket(int): Ticket` - Recupere ticket
- `getAllTickets(): List<Ticket>` - Tous tickets
- `getTicketsByStatus(String): List<Ticket>` - Par statut
- `getTicketsByUser(User): List<Ticket>` - Par utilisateur
- `getTicketsByPriority(String): List<Ticket>` - Par priorite
- `updateTicketStatus(int, String, User): boolean` - MAJ statut
- `updateTicketPriority(int, String, User): boolean` - MAJ priorite
- `assignTicket(int, User, User): boolean` - Assigne ticket
- `unassignTicket(int, User): boolean` - Desassigne
- `addCommentToTicket(int, String, User): boolean` - Ajoute commentaire
- `updateTicketDescription(int, Description): boolean` - MAJ description
- `closeTicket(int, User): boolean` - Ferme ticket
- `displayTicket(int)` - Affiche ticket
- `displayAllTickets()` - Affiche tous
- `exportTicketToPDF(int, String): boolean` - Export PDF
- `displaySystemStatistics()` - Statistiques
- `displayTicketsByStatus(String)` - Affiche par statut
- `displayTicketsByPriority(String)` - Affiche par priorite
- `displayTicketsByUser(User)` - Affiche par utilisateur
- `displayAssignationStatistics()` - Stats assignations
- `searchTicketsByTitle(String): List<Ticket>` - Recherche
- `getCriticalTickets(): List<Ticket>` - Tickets critiques
- `getUnassignedOpenTickets(): List<Ticket>` - Non assignes

#### **statusManager**
Gere le cycle de vie avec transitions.

**Attributs:**
- `validStatuses: List<String>` - Statuts valides
- `statusTransitions: Map<String, List<String>>` - Transitions autorisees

**Methodes:**
- `statusManager()` - Constructeur
- `updateStatus(Ticket, String, User): boolean` - MAJ statut
- `validateStatus(String): boolean` - Valide statut
- `validateTransition(String, String): boolean` - Valide transition
- `canUserChangeStatus(User, Ticket, String): boolean` - Verifie permission
- `getValidTransitions(String): List<String>` - Transitions possibles
- `getValidStatuses(): List<String>` - Liste statuts
- `initializeTransitions()` - Initialise regles
- `getStatusDescription(String): String` - Description statut

#### **PriorityManager**
Gere les priorites.

**Attributs:**
- `validPriorities: List<String>` - Priorites valides
- `priorityLevels: Map<String, Integer>` - Niveaux numeriques

**Methodes:**
- `PriorityManager()` - Constructeur
- `updatePriority(Ticket, String, User): boolean` - MAJ priorite
- `validatePriority(String): boolean` - Valide priorite
- `canUserChangePriority(User, Ticket): boolean` - Verifie permission
- `getValidPriorities(): List<String>` - Liste priorites
- `comparePriorities(String, String): int` - Compare priorites
- `getPriorityLevel(String): int` - Niveau numerique
- `getHighestPriorityTickets(List<Ticket>): List<Ticket>` - Plus prioritaires

#### **commentManager**
Gere commentaires, auteurs et dates.

**Attributs:**
- `ticketComments: Map<Integer, List<String>>` - Commentaires
- `commentAuthors: Map<Integer, List<User>>` - Auteurs
- `commentDates: Map<Integer, List<Date>>` - Dates

**Methodes:**
- `commentManager()` - Constructeur
- `addComment(int, String, User): boolean` - Ajoute commentaire
- `getComments(int): List<String>` - Recupere commentaires
- `getCommentsWithAuthors(int): List<String>` - Avec auteurs
- `getCommentCount(int): int` - Nombre commentaires
- `getLastComment(int): String` - Dernier commentaire
- `deleteComment(int, int, User): boolean` - Supprime commentaire
- `hasComments(int): boolean` - Verifie presence
- `formatCommentHistory(int): String` - Historique formate
- `getCommentAuthors(int): List<User>` - Liste auteurs
- `getCommentDates(int): List<Date>` - Liste dates
- `clearComments(int)` - Efface commentaires

#### **AssignationManager**
Gere assignations et charge travail.

**Attributs:**
- `userAssignments: Map<Integer, List<Ticket>>` - Tickets par user
- `userWorkload: Map<Integer, Integer>` - Charge par user

**Methodes:**
- `AssignationManager()` - Constructeur
- `assignTicket(Ticket, User, User): boolean` - Assigne
- `unassignTicket(Ticket, User): boolean` - Desassigne
- `canAssign(User, Ticket, User): boolean` - Verifie permissions
- `isUserAdmin(User): boolean` - Verifie admin
- `getUserAssignedTickets(int): List<Ticket>` - Tickets assignes
- `getUserWorkload(int): int` - Charge utilisateur
- `isUserAvailable(int, int): boolean` - Verifie disponibilite
- `reassignTicket(Ticket, User, User): boolean` - Reassigne
- `getAssignmentStatistics(): Map<Integer, Integer>` - Statistiques

#### **descriptionManager**
Coordonne les descriptions.

**Attributs:**
- `descriptionValidator: DescriptionValidator` - Validateur

**Methodes:**
- `descriptionManager()` - Constructeur
- `createDescription(): Description` - Creation vide
- `createDescription(String): Description` - Avec texte
- `updateDescription(Description, String): boolean` - MAJ texte
- `addImageToDescription(Description, String): boolean` - Ajoute image
- `addVideoToDescription(Description, String): boolean` - Ajoute video
- `removeImageFromDescription(Description, String): boolean` - Retire image
- `removeVideoFromDescription(Description, String): boolean` - Retire video
- `validateDescription(Description): boolean` - Valide
- `exportDescriptionToPDF(Description, String): boolean` - Export PDF
- `getDescriptionSummary(Description): String` - Resume

### Affichage

#### **Display**
Responsable de l'affichage formate.

**Methodes:**
- `Display()` - Constructeur
- `displayTicket(Ticket)` - Affiche ticket simple
- `displayTicket(Ticket, List<String>)` - Avec commentaires
- `displayTicketDetails(Ticket, List<String>, String)` - Details complets
- `displayAllTickets(List<Ticket>)` - Tous tickets
- `displayTicketsByStatus(List<Ticket>, String)` - Par statut
- `displayTicketsByPriority(List<Ticket>, String)` - Par priorite
- `displayTicketsByUser(List<Ticket>, User)` - Par utilisateur
- `displayUserInfo(User)` - Info utilisateur
- `displayDescription(Description)` - Affiche description
- `displayStatistics(int, int, int)` - Statistiques
- `exportTicketToPDF(Ticket, Description, List<String>, String): boolean` - Export PDF
- `formatTicketInfo(Ticket): String` - Formate ticket
- `formatDate(Date): String` - Formate date
- `formatComments(List<String>): String` - Formate commentaires
- Methodes privees auxiliaires d'affichage

## Flux de Travail

Le flux suit un pattern de delegation :
1. Creation d'utilisateurs via UserCreator qui valide avec UserValidator
2. Creation de tickets via TicketCreator qui valide avec TicketValidator et DescriptionValidator
3. Ajout au systeme via TicketManager
4. Toutes les operations passent par TicketManager qui delegue aux managers appropries
5. Chaque manager verifie permissions et validite avant modification
6. Display est utilise par TicketManager pour l'affichage final

Cette separation claire des responsabilites rend le systeme facilement modifiable.

## Auteurs

Projet développé pour le cours GEI311 - Architecture Logicielle  
Il'aina Ratefinanahary et Samuel Brassard
UQAC - Automne 2025

## License

Ce projet est à usage éducatif uniquement.
