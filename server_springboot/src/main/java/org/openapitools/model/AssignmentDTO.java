package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;
import javax.annotation.Generated;

/**
 * Requête d&#39;assignation d&#39;un ticket à un utilisateur
 */

@Schema(name = "AssignmentDTO", description = "Requête d'assignation d'un ticket à un utilisateur")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class AssignmentDTO {

  private Integer userId;

  private Integer assignedBy;

  public AssignmentDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AssignmentDTO(Integer userId) {
    this.userId = userId;
  }

  public AssignmentDTO userId(Integer userId) {
    this.userId = userId;
    return this;
  }

  /**
   * ID de l'utilisateur à qui assigner le ticket
   * @return userId
   */
  @NotNull 
  @Schema(name = "userId", example = "2", description = "ID de l'utilisateur à qui assigner le ticket", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("userId")
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public AssignmentDTO assignedBy(Integer assignedBy) {
    this.assignedBy = assignedBy;
    return this;
  }

  /**
   * ID de l'utilisateur effectuant l'assignation
   * @return assignedBy
   */
  
  @Schema(name = "assignedBy", example = "1", description = "ID de l'utilisateur effectuant l'assignation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assignedBy")
  public Integer getAssignedBy() {
    return assignedBy;
  }

  public void setAssignedBy(Integer assignedBy) {
    this.assignedBy = assignedBy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssignmentDTO assignmentDTO = (AssignmentDTO) o;
    return Objects.equals(this.userId, assignmentDTO.userId) &&
        Objects.equals(this.assignedBy, assignmentDTO.assignedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, assignedBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignmentDTO {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    assignedBy: ").append(toIndentedString(assignedBy)).append("\n");
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

