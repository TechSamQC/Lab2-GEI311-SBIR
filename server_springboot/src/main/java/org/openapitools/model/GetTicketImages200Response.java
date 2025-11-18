package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * GetTicketImages200Response
 */

@JsonTypeName("getTicketImages_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class GetTicketImages200Response {

  private Integer ticketId;

  @Valid
  private List<String> imagePaths = new ArrayList<>();

  public GetTicketImages200Response ticketId(Integer ticketId) {
    this.ticketId = ticketId;
    return this;
  }

  /**
   * Get ticketId
   * @return ticketId
   */
  
  @Schema(name = "ticketId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ticketId")
  public Integer getTicketId() {
    return ticketId;
  }

  public void setTicketId(Integer ticketId) {
    this.ticketId = ticketId;
  }

  public GetTicketImages200Response imagePaths(List<String> imagePaths) {
    this.imagePaths = imagePaths;
    return this;
  }

  public GetTicketImages200Response addImagePathsItem(String imagePathsItem) {
    if (this.imagePaths == null) {
      this.imagePaths = new ArrayList<>();
    }
    this.imagePaths.add(imagePathsItem);
    return this;
  }

  /**
   * Liste des chemins d'images
   * @return imagePaths
   */
  
  @Schema(name = "imagePaths", description = "Liste des chemins d'images", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("imagePaths")
  public List<String> getImagePaths() {
    return imagePaths;
  }

  public void setImagePaths(List<String> imagePaths) {
    this.imagePaths = imagePaths;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetTicketImages200Response getTicketImages200Response = (GetTicketImages200Response) o;
    return Objects.equals(this.ticketId, getTicketImages200Response.ticketId) &&
        Objects.equals(this.imagePaths, getTicketImages200Response.imagePaths);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ticketId, imagePaths);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetTicketImages200Response {\n");
    sb.append("    ticketId: ").append(toIndentedString(ticketId)).append("\n");
    sb.append("    imagePaths: ").append(toIndentedString(imagePaths)).append("\n");
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

