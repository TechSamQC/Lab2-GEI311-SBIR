# TicketsApi

All URIs are relative to *http://localhost:8080/api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**assignTicket**](TicketsApi.md#assignTicket) | **PUT** /tickets/{ticketId}/assign | Assigne un ticket à un utilisateur |
| [**closeTicket**](TicketsApi.md#closeTicket) | **PUT** /tickets/{ticketId}/close | Ferme/termine un ticket |
| [**createTicket**](TicketsApi.md#createTicket) | **POST** /tickets | Crée un nouveau ticket |
| [**deleteTicket**](TicketsApi.md#deleteTicket) | **DELETE** /tickets/{ticketId} | Supprime un ticket |
| [**getAllTickets**](TicketsApi.md#getAllTickets) | **GET** /tickets | Liste tous les tickets |
| [**getTicketById**](TicketsApi.md#getTicketById) | **GET** /tickets/{ticketId} | Récupère un ticket spécifique |
| [**unassignTicket**](TicketsApi.md#unassignTicket) | **PUT** /tickets/{ticketId}/unassign | Désassigne un ticket |
| [**updateTicket**](TicketsApi.md#updateTicket) | **PUT** /tickets/{ticketId} | Met à jour un ticket |
| [**updateTicketDescription**](TicketsApi.md#updateTicketDescription) | **PUT** /tickets/{ticketId}/description | Met à jour la description d&#39;un ticket |
| [**updateTicketPriority**](TicketsApi.md#updateTicketPriority) | **PUT** /tickets/{ticketId}/priority | Modifie la priorité d&#39;un ticket |


<a id="assignTicket"></a>
# **assignTicket**
> TicketDTO assignTicket(ticketId, assignmentDTO)

Assigne un ticket à un utilisateur

Assigne un ticket à un utilisateur spécifique. Change automatiquement le statut du ticket à ASSIGNE. Seuls les admins peuvent assigner des tickets. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TicketsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    TicketsApi apiInstance = new TicketsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    AssignmentDTO assignmentDTO = new AssignmentDTO(); // AssignmentDTO | 
    try {
      TicketDTO result = apiInstance.assignTicket(ticketId, assignmentDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TicketsApi#assignTicket");
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
| **assignmentDTO** | [**AssignmentDTO**](AssignmentDTO.md)|  | |

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
| **200** | Ticket assigné avec succès |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="closeTicket"></a>
# **closeTicket**
> TicketDTO closeTicket(ticketId, closeTicketRequest)

Ferme/termine un ticket

Marque un ticket comme terminé

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TicketsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    TicketsApi apiInstance = new TicketsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    CloseTicketRequest closeTicketRequest = new CloseTicketRequest(); // CloseTicketRequest | 
    try {
      TicketDTO result = apiInstance.closeTicket(ticketId, closeTicketRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TicketsApi#closeTicket");
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
| **closeTicketRequest** | [**CloseTicketRequest**](CloseTicketRequest.md)|  | |

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
| **200** | Ticket fermé avec succès |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **404** | Ressource non trouvée |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="createTicket"></a>
# **createTicket**
> TicketDTO createTicket(createTicketRequest)

Crée un nouveau ticket

Crée un nouveau ticket dans le système

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TicketsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    TicketsApi apiInstance = new TicketsApi(defaultClient);
    CreateTicketRequest createTicketRequest = new CreateTicketRequest(); // CreateTicketRequest | 
    try {
      TicketDTO result = apiInstance.createTicket(createTicketRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TicketsApi#createTicket");
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
| **createTicketRequest** | [**CreateTicketRequest**](CreateTicketRequest.md)|  | |

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
| **201** | Ticket créé avec succès |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="deleteTicket"></a>
# **deleteTicket**
> deleteTicket(ticketId)

Supprime un ticket

Supprime un ticket. Seuls les administrateurs peuvent supprimer des tickets. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TicketsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    TicketsApi apiInstance = new TicketsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    try {
      apiInstance.deleteTicket(ticketId);
    } catch (ApiException e) {
      System.err.println("Exception when calling TicketsApi#deleteTicket");
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
| **204** | Ticket supprimé avec succès |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getAllTickets"></a>
# **getAllTickets**
> List&lt;TicketDTO&gt; getAllTickets(status, assignedTo, priority)

Liste tous les tickets

Retourne la liste de tous les tickets du système avec filtrage optionnel

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TicketsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    TicketsApi apiInstance = new TicketsApi(defaultClient);
    String status = "OUVERT"; // String | Filtrer par statut
    Integer assignedTo = 56; // Integer | Filtrer par utilisateur assigné (ID)
    String priority = "BASSE"; // String | Filtrer par priorité
    try {
      List<TicketDTO> result = apiInstance.getAllTickets(status, assignedTo, priority);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TicketsApi#getAllTickets");
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
| **status** | **String**| Filtrer par statut | [optional] [enum: OUVERT, ASSIGNE, TERMINE, FERME] |
| **assignedTo** | **Integer**| Filtrer par utilisateur assigné (ID) | [optional] |
| **priority** | **String**| Filtrer par priorité | [optional] [enum: BASSE, MOYENNE, HAUTE, CRITIQUE] |

### Return type

[**List&lt;TicketDTO&gt;**](TicketDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des tickets récupérée avec succès |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getTicketById"></a>
# **getTicketById**
> TicketDTO getTicketById(ticketId)

Récupère un ticket spécifique

Retourne les informations complètes d&#39;un ticket spécifique

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TicketsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    TicketsApi apiInstance = new TicketsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    try {
      TicketDTO result = apiInstance.getTicketById(ticketId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TicketsApi#getTicketById");
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

[**TicketDTO**](TicketDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ticket trouvé |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="unassignTicket"></a>
# **unassignTicket**
> TicketDTO unassignTicket(ticketId, unassignTicketRequest)

Désassigne un ticket

Retire l&#39;assignation d&#39;un ticket

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TicketsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    TicketsApi apiInstance = new TicketsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    UnassignTicketRequest unassignTicketRequest = new UnassignTicketRequest(); // UnassignTicketRequest | 
    try {
      TicketDTO result = apiInstance.unassignTicket(ticketId, unassignTicketRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TicketsApi#unassignTicket");
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
| **unassignTicketRequest** | [**UnassignTicketRequest**](UnassignTicketRequest.md)|  | |

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
| **200** | Ticket désassigné avec succès |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="updateTicket"></a>
# **updateTicket**
> TicketDTO updateTicket(ticketId, updateTicketRequest)

Met à jour un ticket

Modifie un ticket existant. Les admins/développeurs peuvent modifier tous les tickets. Les utilisateurs ne peuvent modifier que leurs propres tickets. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TicketsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    TicketsApi apiInstance = new TicketsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    UpdateTicketRequest updateTicketRequest = new UpdateTicketRequest(); // UpdateTicketRequest | 
    try {
      TicketDTO result = apiInstance.updateTicket(ticketId, updateTicketRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TicketsApi#updateTicket");
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
| **updateTicketRequest** | [**UpdateTicketRequest**](UpdateTicketRequest.md)|  | |

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
| **200** | Ticket modifié avec succès |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="updateTicketDescription"></a>
# **updateTicketDescription**
> TicketDTO updateTicketDescription(ticketId, description)

Met à jour la description d&#39;un ticket

Modifie la description d&#39;un ticket existant

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TicketsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    TicketsApi apiInstance = new TicketsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    Description description = new Description(); // Description | 
    try {
      TicketDTO result = apiInstance.updateTicketDescription(ticketId, description);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TicketsApi#updateTicketDescription");
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
| **description** | [**Description**](Description.md)|  | |

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
| **200** | Description mise à jour avec succès |  -  |
| **404** | Ressource non trouvée |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="updateTicketPriority"></a>
# **updateTicketPriority**
> TicketDTO updateTicketPriority(ticketId, updateTicketPriorityRequest)

Modifie la priorité d&#39;un ticket

Met à jour la priorité d&#39;un ticket spécifique

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TicketsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    TicketsApi apiInstance = new TicketsApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    UpdateTicketPriorityRequest updateTicketPriorityRequest = new UpdateTicketPriorityRequest(); // UpdateTicketPriorityRequest | 
    try {
      TicketDTO result = apiInstance.updateTicketPriority(ticketId, updateTicketPriorityRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TicketsApi#updateTicketPriority");
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
| **updateTicketPriorityRequest** | [**UpdateTicketPriorityRequest**](UpdateTicketPriorityRequest.md)|  | |

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
| **200** | Priorité mise à jour avec succès |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

