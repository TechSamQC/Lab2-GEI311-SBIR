

# UserDTO

Représentation d'un utilisateur du système

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**userID** | **Integer** | Identifiant unique de l&#39;utilisateur |  |
|**name** | **String** | Nom complet de l&#39;utilisateur |  |
|**email** | **String** | Adresse email de l&#39;utilisateur |  |
|**role** | [**RoleEnum**](#RoleEnum) | Rôle de l&#39;utilisateur dans le système |  |
|**isAdmin** | **Boolean** | Indique si l&#39;utilisateur a les privilèges admin |  |



## Enum: RoleEnum

| Name | Value |
|---- | -----|
| ADMIN | &quot;ADMIN&quot; |
| DEVELOPER | &quot;DEVELOPER&quot; |
| USER | &quot;USER&quot; |



