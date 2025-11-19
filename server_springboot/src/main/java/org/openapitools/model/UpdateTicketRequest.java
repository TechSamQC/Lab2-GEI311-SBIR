package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;
import javax.annotation.Generated;

/**
 * Requête de modification d&#39;un ticket existant
 */

@Schema(name = "UpdateTicketRequest", description = "Requête de modification d'un ticket existant")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class UpdateTicketRequest {

  private String title;

  /**
   * Nouvelle priorité (optionnel)
   */
  public enum PriorityEnum {
    BASSE("BASSE"),
    
    MOYENNE("MOYENNE"),
    
    HAUTE("HAUTE"),
    
    CRITIQUE("CRITIQUE");

    private String value;

    PriorityEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PriorityEnum fromValue(String value) {
      for (PriorityEnum b : PriorityEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private PriorityEnum priority;

  public UpdateTicketRequest title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Nouveau titre (optionnel)
   * @return title
   */
  @Size(min = 1, max = 200) 
  @Schema(name = "title", example = "Bug critique - Crash à la connexion [RÉSOLU]", description = "Nouveau titre (optionnel)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public UpdateTicketRequest priority(PriorityEnum priority) {
    this.priority = priority;
    return this;
  }

  /**
   * Nouvelle priorité (optionnel)
   * @return priority
   */
  
  @Schema(name = "priority", example = "MOYENNE", description = "Nouvelle priorité (optionnel)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("priority")
  public PriorityEnum getPriority() {
    return priority;
  }

  public void setPriority(PriorityEnum priority) {
    this.priority = priority;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateTicketRequest updateTicketRequest = (UpdateTicketRequest) o;
    return Objects.equals(this.title, updateTicketRequest.title) &&
        Objects.equals(this.priority, updateTicketRequest.priority);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, priority);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTicketRequest {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
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

