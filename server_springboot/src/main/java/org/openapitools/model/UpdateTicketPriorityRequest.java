package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * UpdateTicketPriorityRequest
 */

@JsonTypeName("updateTicketPriority_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class UpdateTicketPriorityRequest {

  /**
   * Gets or Sets priority
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

  private Integer requestedBy;

  public UpdateTicketPriorityRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UpdateTicketPriorityRequest(PriorityEnum priority) {
    this.priority = priority;
  }

  public UpdateTicketPriorityRequest priority(PriorityEnum priority) {
    this.priority = priority;
    return this;
  }

  /**
   * Get priority
   * @return priority
   */
  @NotNull 
  @Schema(name = "priority", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("priority")
  public PriorityEnum getPriority() {
    return priority;
  }

  public void setPriority(PriorityEnum priority) {
    this.priority = priority;
  }

  public UpdateTicketPriorityRequest requestedBy(Integer requestedBy) {
    this.requestedBy = requestedBy;
    return this;
  }

  /**
   * Get requestedBy
   * @return requestedBy
   */
  
  @Schema(name = "requestedBy", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    UpdateTicketPriorityRequest updateTicketPriorityRequest = (UpdateTicketPriorityRequest) o;
    return Objects.equals(this.priority, updateTicketPriorityRequest.priority) &&
        Objects.equals(this.requestedBy, updateTicketPriorityRequest.requestedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(priority, requestedBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTicketPriorityRequest {\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
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

