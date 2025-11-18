# ExportApi

All URIs are relative to *http://localhost:8080/api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**exportTicketToPDF**](ExportApi.md#exportTicketToPDF) | **GET** /tickets/{ticketId}/export/pdf | Exporte un ticket en PDF |


<a id="exportTicketToPDF"></a>
# **exportTicketToPDF**
> File exportTicketToPDF(ticketId, userId)

Exporte un ticket en PDF

Génère et exporte le contenu d&#39;un ticket en format PDF. Utilise le pattern Strategy (PDFExporter) côté serveur. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExportApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    ExportApi apiInstance = new ExportApi(defaultClient);
    Integer ticketId = 1001; // Integer | Identifiant unique du ticket
    Integer userId = 56; // Integer | ID de l'utilisateur demandant l'export
    try {
      File result = apiInstance.exportTicketToPDF(ticketId, userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExportApi#exportTicketToPDF");
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
| **userId** | **Integer**| ID de l&#39;utilisateur demandant l&#39;export | [optional] |

### Return type

[**File**](File.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/pdf, text/plain, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Export PDF généré avec succès |  -  |
| **404** | Ressource non trouvée |  -  |
| **403** | Accès refusé (permissions insuffisantes) |  -  |
| **500** | Erreur interne du serveur |  -  |

