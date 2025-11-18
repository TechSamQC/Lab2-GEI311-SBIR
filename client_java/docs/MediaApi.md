# MediaApi

All URIs are relative to *http://localhost:8080/api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addImageToTicket**](MediaApi.md#addImageToTicket) | **POST** /tickets/{ticketId}/ajoutImage | Ajoute une image à la description du ticket |
| [**addVideoToTicket**](MediaApi.md#addVideoToTicket) | **POST** /tickets/{ticketId}/ajoutVideo | Ajoute une vidéo à la description du ticket |
| [**getTicketImages**](MediaApi.md#getTicketImages) | **GET** /tickets/{ticketId}/voirImage | Récupère les images d&#39;un ticket |


<a id="addImageToTicket"></a>
# **addImageToTicket**
> TicketDTO addImageToTicket(ticketId, addImageRequest)

Ajoute une image à la description du ticket

Ajoute un chemin d&#39;image à la description d&#39;un ticket spécifique

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MediaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    MediaApi apiInstance = new MediaApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    AddImageRequest addImageRequest = new AddImageRequest(); // AddImageRequest | 
    try {
      TicketDTO result = apiInstance.addImageToTicket(ticketId, addImageRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MediaApi#addImageToTicket");
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
| **addImageRequest** | [**AddImageRequest**](AddImageRequest.md)|  | |

### Return type

[**TicketDTO**](TicketDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Image ajoutée avec succès |  -  |
| **404** | Ressource non trouvée |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="addVideoToTicket"></a>
# **addVideoToTicket**
> TicketDTO addVideoToTicket(ticketId, addVideoRequest)

Ajoute une vidéo à la description du ticket

Ajoute un chemin de vidéo à la description d&#39;un ticket spécifique

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MediaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    MediaApi apiInstance = new MediaApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    AddVideoRequest addVideoRequest = new AddVideoRequest(); // AddVideoRequest | 
    try {
      TicketDTO result = apiInstance.addVideoToTicket(ticketId, addVideoRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MediaApi#addVideoToTicket");
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
| **addVideoRequest** | [**AddVideoRequest**](AddVideoRequest.md)|  | |

### Return type

[**TicketDTO**](TicketDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Vidéo ajoutée avec succès |  -  |
| **404** | Ressource non trouvée |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getTicketImages"></a>
# **getTicketImages**
> GetTicketImages200Response getTicketImages(ticketId)

Récupère les images d&#39;un ticket

Retourne la liste de tous les chemins d&#39;images associés à la description du ticket

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MediaApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    MediaApi apiInstance = new MediaApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    try {
      GetTicketImages200Response result = apiInstance.getTicketImages(ticketId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MediaApi#getTicketImages");
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

[**GetTicketImages200Response**](GetTicketImages200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des images récupérée avec succès |  -  |
| **404** | Ressource non trouvée |  -  |
| **500** | Erreur interne du serveur |  -  |

