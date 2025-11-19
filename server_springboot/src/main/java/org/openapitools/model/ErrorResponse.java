package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;
import javax.annotation.Generated;

/**
 * Réponse d&#39;erreur standardisée
 */

@Schema(name = "ErrorResponse", description = "Réponse d'erreur standardisée")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class ErrorResponse {

  private String error;

  private Integer code;

  private String message;

  private String details;

  public ErrorResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ErrorResponse(String error, String message) {
    this.error = error;
    this.message = message;
  }

  public ErrorResponse error(String error) {
    this.error = error;
    return this;
  }

  /**
   * Code d'erreur
   * @return error
   */
  @NotNull 
  @Schema(name = "error", example = "INVALID_TRANSITION", description = "Code d'erreur", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("error")
  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public ErrorResponse code(Integer code) {
    this.code = code;
    return this;
  }

  /**
   * Code d'erreur numérique
   * @return code
   */
  
  @Schema(name = "code", example = "400", description = "Code d'erreur numérique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("code")
  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public ErrorResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Message d'erreur détaillé
   * @return message
   */
  @NotNull 
  @Schema(name = "message", example = "Transition invalide : Ouvert -> Validation. Transitions autorisées : ASSIGNE, FERME", description = "Message d'erreur détaillé", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorResponse details(String details) {
    this.details = details;
    return this;
  }

  /**
   * Détails supplémentaires sur l'erreur
   * @return details
   */
  
  @Schema(name = "details", description = "Détails supplémentaires sur l'erreur", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("details")
  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.error, errorResponse.error) &&
        Objects.equals(this.code, errorResponse.code) &&
        Objects.equals(this.message, errorResponse.message) &&
        Objects.equals(this.details, errorResponse.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, code, message, details);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

