package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * Description multimédia d&#39;un ticket utilisant le pattern Composite. Supporte le texte, les images et les vidéos. 
 */

@Schema(name = "Description", description = "Description multimédia d'un ticket utilisant le pattern Composite. Supporte le texte, les images et les vidéos. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class Description {

  private String textContent;

  @Valid
  private List<String> imagePaths = new ArrayList<>();

  @Valid
  private List<String> videoPaths = new ArrayList<>();

  public Description textContent(String textContent) {
    this.textContent = textContent;
    return this;
  }

  /**
   * Contenu textuel de la description
   * @return textContent
   */
  
  @Schema(name = "textContent", example = "L'application crash lors de la connexion après 3 tentatives échouées.", description = "Contenu textuel de la description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("textContent")
  public String getTextContent() {
    return textContent;
  }

  public void setTextContent(String textContent) {
    this.textContent = textContent;
  }

  public Description imagePaths(List<String> imagePaths) {
    this.imagePaths = imagePaths;
    return this;
  }

  public Description addImagePathsItem(String imagePathsItem) {
    if (this.imagePaths == null) {
      this.imagePaths = new ArrayList<>();
    }
    this.imagePaths.add(imagePathsItem);
    return this;
  }

  /**
   * Chemins vers les images attachées
   * @return imagePaths
   */
  
  @Schema(name = "imagePaths", example = "[\"media/images/screenshot1.png\",\"media/images/error_log.png\"]", description = "Chemins vers les images attachées", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("imagePaths")
  public List<String> getImagePaths() {
    return imagePaths;
  }

  public void setImagePaths(List<String> imagePaths) {
    this.imagePaths = imagePaths;
  }

  public Description videoPaths(List<String> videoPaths) {
    this.videoPaths = videoPaths;
    return this;
  }

  public Description addVideoPathsItem(String videoPathsItem) {
    if (this.videoPaths == null) {
      this.videoPaths = new ArrayList<>();
    }
    this.videoPaths.add(videoPathsItem);
    return this;
  }

  /**
   * Chemins vers les vidéos attachées
   * @return videoPaths
   */
  
  @Schema(name = "videoPaths", example = "[\"media/videos/bug_reproduction.mp4\"]", description = "Chemins vers les vidéos attachées", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("videoPaths")
  public List<String> getVideoPaths() {
    return videoPaths;
  }

  public void setVideoPaths(List<String> videoPaths) {
    this.videoPaths = videoPaths;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Description description = (Description) o;
    return Objects.equals(this.textContent, description.textContent) &&
        Objects.equals(this.imagePaths, description.imagePaths) &&
        Objects.equals(this.videoPaths, description.videoPaths);
  }

  @Override
  public int hashCode() {
    return Objects.hash(textContent, imagePaths, videoPaths);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Description {\n");
    sb.append("    textContent: ").append(toIndentedString(textContent)).append("\n");
    sb.append("    imagePaths: ").append(toIndentedString(imagePaths)).append("\n");
    sb.append("    videoPaths: ").append(toIndentedString(videoPaths)).append("\n");
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

