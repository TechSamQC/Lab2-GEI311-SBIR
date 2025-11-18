package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Requête d&#39;ajout de commentaire
 */

@Schema(name = "CommentRequest", description = "Requête d'ajout de commentaire")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class CommentRequest {

  private String comment;

  private Integer authorId;

  public CommentRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CommentRequest(String comment) {
    this.comment = comment;
  }

  public CommentRequest comment(String comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Texte du commentaire
   * @return comment
   */
  @NotNull @Size(min = 1, max = 1000) 
  @Schema(name = "comment", example = "Investigation en cours - logs analysés", description = "Texte du commentaire", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("comment")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public CommentRequest authorId(Integer authorId) {
    this.authorId = authorId;
    return this;
  }

  /**
   * ID de l'auteur du commentaire
   * @return authorId
   */
  
  @Schema(name = "authorId", example = "1", description = "ID de l'auteur du commentaire", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("authorId")
  public Integer getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentRequest commentRequest = (CommentRequest) o;
    return Objects.equals(this.comment, commentRequest.comment) &&
        Objects.equals(this.authorId, commentRequest.authorId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, authorId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentRequest {\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    authorId: ").append(toIndentedString(authorId)).append("\n");
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

