

# ContentItemDTO

Représentation d'un élément de contenu (texte, image, vidéo). Implémente le pattern Composite côté API. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) | Type de contenu |  |
|**data** | **String** | Données du contenu : - Pour TEXT : le texte complet - Pour IMAGE : chemin du fichier image - Pour VIDEO : chemin du fichier vidéo  |  |
|**metadata** | **String** | Métadonnées optionnelles : - Pour TEXT : null ou vide - Pour IMAGE : caption/légende - Pour VIDEO : durée en secondes (format string)  |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| TEXT | &quot;TEXT&quot; |
| IMAGE | &quot;IMAGE&quot; |
| VIDEO | &quot;VIDEO&quot; |



