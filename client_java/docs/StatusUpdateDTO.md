

# StatusUpdateDTO

Requête de changement de statut d'un ticket

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**status** | [**StatusEnum**](#StatusEnum) | Nouveau statut souhaité (validation des transitions côté serveur) |  |
|**requestedBy** | **Integer** | ID de l&#39;utilisateur effectuant la modification |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| OUVERT | &quot;OUVERT&quot; |
| ASSIGNE | &quot;ASSIGNE&quot; |
| TERMINE | &quot;TERMINE&quot; |
| FERME | &quot;FERME&quot; |



