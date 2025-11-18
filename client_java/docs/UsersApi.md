# UsersApi

All URIs are relative to *http://localhost:8080/api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createUser**](UsersApi.md#createUser) | **POST** /users | Crée un nouvel utilisateur |
| [**deleteUser**](UsersApi.md#deleteUser) | **DELETE** /users/{userId} | Supprime un utilisateur |
| [**getAllUsers**](UsersApi.md#getAllUsers) | **GET** /users | Liste tous les utilisateurs |
| [**getUserById**](UsersApi.md#getUserById) | **GET** /users/{userId} | Récupère un utilisateur spécifique |
| [**updateUser**](UsersApi.md#updateUser) | **PUT** /users/{userId} | Met à jour un utilisateur |


<a id="createUser"></a>
# **createUser**
> UserDTO createUser(userCreate)

Crée un nouvel utilisateur

Crée un nouvel utilisateur dans le système

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    UsersApi apiInstance = new UsersApi(defaultClient);
    UserCreate userCreate = new UserCreate(); // UserCreate | Données de l'utilisateur à créer
    try {
      UserDTO result = apiInstance.createUser(userCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#createUser");
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
| **userCreate** | [**UserCreate**](UserCreate.md)| Données de l&#39;utilisateur à créer | |

### Return type

[**UserDTO**](UserDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Utilisateur créé avec succès |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="deleteUser"></a>
# **deleteUser**
> deleteUser(userId)

Supprime un utilisateur

Supprime un utilisateur du système

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    UsersApi apiInstance = new UsersApi(defaultClient);
    Integer userId = 1; // Integer | Identifiant unique de l'utilisateur
    try {
      apiInstance.deleteUser(userId);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#deleteUser");
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

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Utilisateur supprimé avec succès |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getAllUsers"></a>
# **getAllUsers**
> List&lt;UserDTO&gt; getAllUsers()

Liste tous les utilisateurs

Retourne la liste complète des utilisateurs du système

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    UsersApi apiInstance = new UsersApi(defaultClient);
    try {
      List<UserDTO> result = apiInstance.getAllUsers();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#getAllUsers");
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

[**List&lt;UserDTO&gt;**](UserDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des utilisateurs récupérée avec succès |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="getUserById"></a>
# **getUserById**
> UserDTO getUserById(userId)

Récupère un utilisateur spécifique

Retourne les détails d&#39;un utilisateur par son ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    UsersApi apiInstance = new UsersApi(defaultClient);
    Integer userId = 1; // Integer | Identifiant unique de l'utilisateur
    try {
      UserDTO result = apiInstance.getUserById(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#getUserById");
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

[**UserDTO**](UserDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Utilisateur trouvé |  -  |
| **404** | Ressource non trouvée |  -  |
| **500** | Erreur interne du serveur |  -  |

<a id="updateUser"></a>
# **updateUser**
> UserDTO updateUser(userId, userUpdate)

Met à jour un utilisateur

Met à jour les informations d&#39;un utilisateur existant

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    UsersApi apiInstance = new UsersApi(defaultClient);
    Integer userId = 1; // Integer | Identifiant unique de l'utilisateur
    UserUpdate userUpdate = new UserUpdate(); // UserUpdate | 
    try {
      UserDTO result = apiInstance.updateUser(userId, userUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#updateUser");
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
| **userUpdate** | [**UserUpdate**](UserUpdate.md)|  | |

### Return type

[**UserDTO**](UserDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Utilisateur mis à jour avec succès |  -  |
| **400** | Requête invalide (validation échouée) |  -  |
| **404** | Ressource non trouvée |  -  |
| **500** | Erreur interne du serveur |  -  |

