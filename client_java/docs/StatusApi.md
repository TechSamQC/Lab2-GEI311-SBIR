# StatusApi

All URIs are relative to *http://localhost:8080/api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAvailableTransitions**](StatusApi.md#getAvailableTransitions) | **GET** /tickets/{ticketId}/status | Obtenir les transitions disponibles |
| [**updateTicketStatus**](StatusApi.md#updateTicketStatus) | **PUT** /tickets/{ticketId}/status | Modifie le statut d&#39;un ticket |


<a id="getAvailableTransitions"></a>
# **getAvailableTransitions**
> List&lt;String&gt; getAvailableTransitions(ticketId)

Obtenir les transitions disponibles

Retourne la liste des statuts vers lesquels une transition est possible

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatusApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    StatusApi apiInstance = new StatusApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    try {
      List<String> result = apiInstance.getAvailableTransitions(ticketId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatusApi#getAvailableTransitions");
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
| **200** | Liste des transitions disponibles |  -  |
| **404** | Ressource non trouvée |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="updateTicketStatus"></a>
# **updateTicketStatus**
> TicketDTO updateTicketStatus(ticketId, statusUpdateDTO)

Modifie le statut d&#39;un ticket

Met à jour le statut d&#39;un ticket spécifique avec validation des transitions.  Transitions autorisées : - OUVERT → ASSIGNE, FERME - ASSIGNE → TERMINE, FERME - TERMINE/FERME → (états finaux, aucune transition)  Seuls les admins et développeurs peuvent changer les statuts. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatusApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    StatusApi apiInstance = new StatusApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    StatusUpdateDTO statusUpdateDTO = new StatusUpdateDTO(); // StatusUpdateDTO | 
    try {
      TicketDTO result = apiInstance.updateTicketStatus(ticketId, statusUpdateDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatusApi#updateTicketStatus");
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
| **statusUpdateDTO** | [**StatusUpdateDTO**](StatusUpdateDTO.md)|  | |

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
| **200** | Statut mis à jour avec succès |  -  |
| **400** | Transition de statut invalide |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

