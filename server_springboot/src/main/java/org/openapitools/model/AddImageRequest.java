package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;
import javax.annotation.Generated;

/**
 * Requête d&#39;ajout d&#39;image à un ticket
 */

@Schema(name = "AddImageRequest", description = "Requête d'ajout d'image à un ticket")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class AddImageRequest {

  private String imagePath;

  public AddImageRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AddImageRequest(String imagePath) {
    this.imagePath = imagePath;
  }

  public AddImageRequest imagePath(String imagePath) {
    this.imagePath = imagePath;
    return this;
  }

  /**
   * Chemin ou URL de l'image à ajouter
   * @return imagePath
   */
  @NotNull 
  @Schema(name = "imagePath", example = "media/images/screenshot1.png", description = "Chemin ou URL de l'image à ajouter", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("imagePath")
  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddImageRequest addImageRequest = (AddImageRequest) o;
    return Objects.equals(this.imagePath, addImageRequest.imagePath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imagePath);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddImageRequest {\n");
    sb.append("    imagePath: ").append(toIndentedString(imagePath)).append("\n");
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

