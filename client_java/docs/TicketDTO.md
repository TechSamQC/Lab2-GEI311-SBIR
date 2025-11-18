

# TicketDTO

Représentation complète d'un ticket

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**ticketID** | **Integer** | Identifiant unique du ticket |  |
|**title** | **String** | Titre du ticket |  |
|**status** | [**StatusEnum**](#StatusEnum) | Statut actuel du ticket |  |
|**priority** | [**PriorityEnum**](#PriorityEnum) | Niveau de priorité du ticket |  |
|**createdByName** | **String** | Nom de l&#39;utilisateur qui a créé le ticket |  [optional] |
|**assignedToName** | **String** | Nom de l&#39;utilisateur assigné (null si non assigné) |  [optional] |
|**assignedUserId** | **Integer** | ID de l&#39;utilisateur assigné (0 si non assigné) |  [optional] |
|**description** | [**Description**](Description.md) |  |  [optional] |
|**creationDate** | **OffsetDateTime** | Date et heure de création du ticket |  |
|**updateDate** | **OffsetDateTime** | Date et heure de dernière modification |  [optional] |
|**comments** | **List&lt;String&gt;** | Liste des commentaires du ticket |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| OUVERT | &quot;OUVERT&quot; |
| ASSIGNE | &quot;ASSIGNE&quot; |
| TERMINE | &quot;TERMINE&quot; |
| FERME | &quot;FERME&quot; |



## Enum: PriorityEnum

| Name | Value |
|---- | -----|
| BASSE | &quot;BASSE&quot; |
| MOYENNE | &quot;MOYENNE&quot; |
| HAUTE | &quot;HAUTE&quot; |
| CRITIQUE | &quot;CRITIQUE&quot; |



