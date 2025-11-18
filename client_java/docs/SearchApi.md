# SearchApi

All URIs are relative to *http://localhost:8080/api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getCriticalTickets**](SearchApi.md#getCriticalTickets) | **GET** /tickets/critical | Récupère les tickets critiques |
| [**getTicketsByPriority**](SearchApi.md#getTicketsByPriority) | **GET** /tickets/priority/{priority} | Récupère les tickets par priorité |
| [**getTicketsByStatus**](SearchApi.md#getTicketsByStatus) | **GET** /tickets/status/{status} | Récupère les tickets par statut |
| [**getTicketsByUser**](SearchApi.md#getTicketsByUser) | **GET** /tickets/user/{userId} | Récupère les tickets d&#39;un utilisateur |
| [**getUnassignedTickets**](SearchApi.md#getUnassignedTickets) | **GET** /tickets/unassigned | Récupère les tickets non assignés |
| [**searchTicketsByTitle**](SearchApi.md#searchTicketsByTitle) | **GET** /tickets/search | Recherche des tickets par titre |


<a id="getCriticalTickets"></a>
# **getCriticalTickets**
> List&lt;TicketDTO&gt; getCriticalTickets()

Récupère les tickets critiques

Retourne tous les tickets avec priorité CRITIQUE

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SearchApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    SearchApi apiInstance = new SearchApi(defaultClient);
    try {
      List<TicketDTO> result = apiInstance.getCriticalTickets();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SearchApi#getCriticalTickets");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

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
| **200** | Liste des tickets critiques |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getTicketsByPriority"></a>
# **getTicketsByPriority**
> List&lt;TicketDTO&gt; getTicketsByPriority(priority)

Récupère les tickets par priorité

Retourne tous les tickets ayant une priorité spécifique

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SearchApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    SearchApi apiInstance = new SearchApi(defaultClient);
    String priority = "BASSE"; // String | Priorité des tickets à récupérer
    try {
      List<TicketDTO> result = apiInstance.getTicketsByPriority(priority);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SearchApi#getTicketsByPriority");
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
| **priority** | **String**| Priorité des tickets à récupérer | [enum: BASSE, MOYENNE, HAUTE, CRITIQUE] |

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
| **200** | Liste des tickets filtrés |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getTicketsByStatus"></a>
# **getTicketsByStatus**
> List&lt;TicketDTO&gt; getTicketsByStatus(status)

Récupère les tickets par statut

Retourne tous les tickets ayant un statut spécifique

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SearchApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    SearchApi apiInstance = new SearchApi(defaultClient);
    String status = "OUVERT"; // String | Statut des tickets à récupérer
    try {
      List<TicketDTO> result = apiInstance.getTicketsByStatus(status);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SearchApi#getTicketsByStatus");
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
| **status** | **String**| Statut des tickets à récupérer | [enum: OUVERT, ASSIGNE, TERMINE, FERME] |

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
| **200** | Liste des tickets filtrés |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getTicketsByUser"></a>
# **getTicketsByUser**
> List&lt;TicketDTO&gt; getTicketsByUser(userId)

Récupère les tickets d&#39;un utilisateur

Retourne tous les tickets assignés à un utilisateur spécifique

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SearchApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    SearchApi apiInstance = new SearchApi(defaultClient);
    Integer userId = 1; // Integer | Identifiant unique de l'utilisateur
    try {
      List<TicketDTO> result = apiInstance.getTicketsByUser(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SearchApi#getTicketsByUser");
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
| **userId** | **Integer**| Identifiant unique de l&#39;utilisateur | |

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
| **200** | Liste des tickets de l&#39;utilisateur |  -  |
| **404** | Ressource non trouvée |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getUnassignedTickets"></a>
# **getUnassignedTickets**
> List&lt;TicketDTO&gt; getUnassignedTickets()

Récupère les tickets non assignés

Retourne tous les tickets ouverts qui ne sont pas encore assignés

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SearchApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    SearchApi apiInstance = new SearchApi(defaultClient);
    try {
      List<TicketDTO> result = apiInstance.getUnassignedTickets();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SearchApi#getUnassignedTickets");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

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
| **200** | Liste des tickets non assignés |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="searchTicketsByTitle"></a>
# **searchTicketsByTitle**
> List&lt;TicketDTO&gt; searchTicketsByTitle(title)

Recherche des tickets par titre

Recherche des tickets dont le titre contient la chaîne spécifiée

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SearchApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    SearchApi apiInstance = new SearchApi(defaultClient);
    String title = "title_example"; // String | Titre ou partie du titre à rechercher
    try {
      List<TicketDTO> result = apiInstance.searchTicketsByTitle(title);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SearchApi#searchTicketsByTitle");
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
| **title** | **String**| Titre ou partie du titre à rechercher | |

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
| **200** | Résultats de la recherche |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **500** | Erreur interne du serveur |  -  |

