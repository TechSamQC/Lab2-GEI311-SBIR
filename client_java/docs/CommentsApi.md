# CommentsApi

All URIs are relative to *http://localhost:8080/api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addComment**](CommentsApi.md#addComment) | **POST** /tickets/{ticketId}/comments | Ajoute un commentaire à un ticket |
| [**clearComments**](CommentsApi.md#clearComments) | **DELETE** /tickets/{ticketId}/comments | Supprime tous les commentaires d&#39;un ticket |
| [**getTicketComments**](CommentsApi.md#getTicketComments) | **GET** /tickets/{ticketId}/comments | Liste les commentaires d&#39;un ticket |


<a id="addComment"></a>
# **addComment**
> String addComment(ticketId, commentRequest)

Ajoute un commentaire à un ticket

Ajoute un nouveau commentaire à un ticket. Admins et développeurs peuvent commenter tous les tickets. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CommentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    CommentsApi apiInstance = new CommentsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    CommentRequest commentRequest = new CommentRequest(); // CommentRequest | 
    try {
      String result = apiInstance.addComment(ticketId, commentRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CommentsApi#addComment");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **ticketId** | **Integer**| Identifiant unique du ticket | |
| **commentRequest** | [**CommentRequest**](CommentRequest.md)|  | |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Commentaire ajouté avec succès |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="clearComments"></a>
# **clearComments**
> clearComments(ticketId)

Supprime tous les commentaires d&#39;un ticket

Efface tous les commentaires associés à un ticket

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CommentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    CommentsApi apiInstance = new CommentsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    try {
      apiInstance.clearComments(ticketId);
    } catch (ApiException e) {
      System.err.println("Exception when calling CommentsApi#clearComments");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **ticketId** | **Integer**| Identifiant unique du ticket | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Commentaires supprimés avec succès |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getTicketComments"></a>
# **getTicketComments**
> List&lt;String&gt; getTicketComments(ticketId)

Liste les commentaires d&#39;un ticket

Retourne tous les commentaires associés à un ticket

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CommentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    CommentsApi apiInstance = new CommentsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    try {
      List<String> result = apiInstance.getTicketComments(ticketId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CommentsApi#getTicketComments");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **ticketId** | **Integer**| Identifiant unique du ticket | |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des commentaires récupérée |  -  |
| **404** | Ressource non trouvée |  -  |
| **500** | Erreur interne du serveur |  -  |

