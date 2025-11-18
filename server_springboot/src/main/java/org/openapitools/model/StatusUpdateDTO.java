package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Requête de changement de statut d&#39;un ticket
 */

@Schema(name = "StatusUpdateDTO", description = "Requête de changement de statut d'un ticket")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class StatusUpdateDTO {

  /**
   * Nouveau statut souhaité (validation des transitions côté serveur)
   */
  public enum StatusEnum {
    OUVERT("OUVERT"),
    
    ASSIGNE("ASSIGNE"),
    
    TERMINE("TERMINE"),
    
    FERME("FERME");

    private String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StatusEnum status;

  private Integer requestedBy;

  public StatusUpdateDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public StatusUpdateDTO(StatusEnum status) {
    this.status = status;
  }

  public StatusUpdateDTO status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Nouveau statut souhaité (validation des transitions côté serveur)
   * @return status
   */
  @NotNull 
  @Schema(name = "status", example = "ASSIGNE", description = "Nouveau statut souhaité (validation des transitions côté serveur)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public StatusUpdateDTO requestedBy(Integer requestedBy) {
    this.requestedBy = requestedBy;
    return this;
  }

  /**
   * ID de l'utilisateur effectuant la modification
   * @return requestedBy
   */
  
  @Schema(name = "requestedBy", example = "1", description = "ID de l'utilisateur effectuant la modification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("requestedBy")
  public Integer getRequestedBy() {
    return requestedBy;
  }

  public void setRequestedBy(Integer requestedBy) {
    this.requestedBy = requestedBy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatusUpdateDTO statusUpdateDTO = (StatusUpdateDTO) o;
    return Objects.equals(this.status, statusUpdateDTO.status) &&
        Objects.equals(this.requestedBy, statusUpdateDTO.requestedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, requestedBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatusUpdateDTO {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    requestedBy: ").append(toIndentedString(requestedBy)).append("\n");
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

