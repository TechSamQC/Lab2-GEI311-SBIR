package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.model.Description;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Requête de création d&#39;un nouveau ticket
 */

@Schema(name = "CreateTicketRequest", description = "Requête de création d'un nouveau ticket")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class CreateTicketRequest {

  private String title;

  /**
   * Niveau de priorité
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

  private Description description;

  public CreateTicketRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateTicketRequest(String title, PriorityEnum priority) {
    this.title = title;
    this.priority = priority;
  }

  public CreateTicketRequest title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Titre du ticket
   * @return title
   */
  @NotNull @Size(min = 1, max = 200) 
  @Schema(name = "title", example = "Bug critique - Crash à la connexion", description = "Titre du ticket", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public CreateTicketRequest priority(PriorityEnum priority) {
    this.priority = priority;
    return this;
  }

  /**
   * Niveau de priorité
   * @return priority
   */
  @NotNull 
  @Schema(name = "priority", example = "HAUTE", description = "Niveau de priorité", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("priority")
  public PriorityEnum getPriority() {
    return priority;
  }

  public void setPriority(PriorityEnum priority) {
    this.priority = priority;
  }

  public CreateTicketRequest description(Description description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  @Valid 
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public Description getDescription() {
    return description;
  }

  public void setDescription(Description description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTicketRequest createTicketRequest = (CreateTicketRequest) o;
    return Objects.equals(this.title, createTicketRequest.title) &&
        Objects.equals(this.priority, createTicketRequest.priority) &&
        Objects.equals(this.description, createTicketRequest.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, priority, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTicketRequest {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

