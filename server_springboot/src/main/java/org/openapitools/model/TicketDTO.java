package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import org.openapitools.model.Description;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.NoSuchElementException;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Représentation complète d&#39;un ticket
 */

@Schema(name = "TicketDTO", description = "Représentation complète d'un ticket")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class TicketDTO {

  private Integer ticketID;

  private String title;

  /**
   * Statut actuel du ticket
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

  /**
   * Niveau de priorité du ticket
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

  private String createdByName;

  private JsonNullable<String> assignedToName = JsonNullable.<String>undefined();

  private Integer assignedUserId;

  private Description description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime creationDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updateDate;

  @Valid
  private List<String> comments = new ArrayList<>();

  public TicketDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TicketDTO(Integer ticketID, String title, StatusEnum status, PriorityEnum priority, OffsetDateTime creationDate) {
    this.ticketID = ticketID;
    this.title = title;
    this.status = status;
    this.priority = priority;
    this.creationDate = creationDate;
  }

  public TicketDTO ticketID(Integer ticketID) {
    this.ticketID = ticketID;
    return this;
  }

  /**
   * Identifiant unique du ticket
   * @return ticketID
   */
  @NotNull 
  @Schema(name = "ticketID", example = "1001", description = "Identifiant unique du ticket", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ticketID")
  public Integer getTicketID() {
    return ticketID;
  }

  public void setTicketID(Integer ticketID) {
    this.ticketID = ticketID;
  }

  public TicketDTO title(String title) {
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

  public TicketDTO status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Statut actuel du ticket
   * @return status
   */
  @NotNull 
  @Schema(name = "status", example = "OUVERT", description = "Statut actuel du ticket", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public TicketDTO priority(PriorityEnum priority) {
    this.priority = priority;
    return this;
  }

  /**
   * Niveau de priorité du ticket
   * @return priority
   */
  @NotNull 
  @Schema(name = "priority", example = "HAUTE", description = "Niveau de priorité du ticket", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("priority")
  public PriorityEnum getPriority() {
    return priority;
  }

  public void setPriority(PriorityEnum priority) {
    this.priority = priority;
  }

  public TicketDTO createdByName(String createdByName) {
    this.createdByName = createdByName;
    return this;
  }

  /**
   * Nom de l'utilisateur qui a créé le ticket
   * @return createdByName
   */
  
  @Schema(name = "createdByName", example = "Utilisateur1", description = "Nom de l'utilisateur qui a créé le ticket", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdByName")
  public String getCreatedByName() {
    return createdByName;
  }

  public void setCreatedByName(String createdByName) {
    this.createdByName = createdByName;
  }

  public TicketDTO assignedToName(String assignedToName) {
    this.assignedToName = JsonNullable.of(assignedToName);
    return this;
  }

  /**
   * Nom de l'utilisateur assigné (null si non assigné)
   * @return assignedToName
   */
  
  @Schema(name = "assignedToName", example = "Utilisateur2", description = "Nom de l'utilisateur assigné (null si non assigné)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assignedToName")
  public JsonNullable<String> getAssignedToName() {
    return assignedToName;
  }

  public void setAssignedToName(JsonNullable<String> assignedToName) {
    this.assignedToName = assignedToName;
  }

  public TicketDTO assignedUserId(Integer assignedUserId) {
    this.assignedUserId = assignedUserId;
    return this;
  }

  /**
   * ID de l'utilisateur assigné (0 si non assigné)
   * @return assignedUserId
   */
  
  @Schema(name = "assignedUserId", example = "2", description = "ID de l'utilisateur assigné (0 si non assigné)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assignedUserId")
  public Integer getAssignedUserId() {
    return assignedUserId;
  }

  public void setAssignedUserId(Integer assignedUserId) {
    this.assignedUserId = assignedUserId;
  }

  public TicketDTO description(Description description) {
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

  public TicketDTO creationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Date et heure de création du ticket
   * @return creationDate
   */
  @NotNull @Valid 
  @Schema(name = "creationDate", example = "2025-11-16T10:30Z", description = "Date et heure de création du ticket", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("creationDate")
  public OffsetDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public TicketDTO updateDate(OffsetDateTime updateDate) {
    this.updateDate = updateDate;
    return this;
  }

  /**
   * Date et heure de dernière modification
   * @return updateDate
   */
  @Valid 
  @Schema(name = "updateDate", example = "2025-11-16T14:45Z", description = "Date et heure de dernière modification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updateDate")
  public OffsetDateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(OffsetDateTime updateDate) {
    this.updateDate = updateDate;
  }

  public TicketDTO comments(List<String> comments) {
    this.comments = comments;
    return this;
  }

  public TicketDTO addCommentsItem(String commentsItem) {
    if (this.comments == null) {
      this.comments = new ArrayList<>();
    }
    this.comments.add(commentsItem);
    return this;
  }

  /**
   * Liste des commentaires du ticket
   * @return comments
   */
  
  @Schema(name = "comments", description = "Liste des commentaires du ticket", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("comments")
  public List<String> getComments() {
    return comments;
  }

  public void setComments(List<String> comments) {
    this.comments = comments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketDTO ticketDTO = (TicketDTO) o;
    return Objects.equals(this.ticketID, ticketDTO.ticketID) &&
        Objects.equals(this.title, ticketDTO.title) &&
        Objects.equals(this.status, ticketDTO.status) &&
        Objects.equals(this.priority, ticketDTO.priority) &&
        Objects.equals(this.createdByName, ticketDTO.createdByName) &&
        equalsNullable(this.assignedToName, ticketDTO.assignedToName) &&
        Objects.equals(this.assignedUserId, ticketDTO.assignedUserId) &&
        Objects.equals(this.description, ticketDTO.description) &&
        Objects.equals(this.creationDate, ticketDTO.creationDate) &&
        Objects.equals(this.updateDate, ticketDTO.updateDate) &&
        Objects.equals(this.comments, ticketDTO.comments);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(ticketID, title, status, priority, createdByName, hashCodeNullable(assignedToName), assignedUserId, description, creationDate, updateDate, comments);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketDTO {\n");
    sb.append("    ticketID: ").append(toIndentedString(ticketID)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    createdByName: ").append(toIndentedString(createdByName)).append("\n");
    sb.append("    assignedToName: ").append(toIndentedString(assignedToName)).append("\n");
    sb.append("    assignedUserId: ").append(toIndentedString(assignedUserId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    updateDate: ").append(toIndentedString(updateDate)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
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

