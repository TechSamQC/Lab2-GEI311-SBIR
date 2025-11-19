package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;
import javax.annotation.Generated;

/**
 * Requête d&#39;ajout de vidéo à un ticket
 */

@Schema(name = "AddVideoRequest", description = "Requête d'ajout de vidéo à un ticket")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class AddVideoRequest {

  private String videoPath;

  public AddVideoRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AddVideoRequest(String videoPath) {
    this.videoPath = videoPath;
  }

  public AddVideoRequest videoPath(String videoPath) {
    this.videoPath = videoPath;
    return this;
  }

  /**
   * Chemin ou URL de la vidéo à ajouter
   * @return videoPath
   */
  @NotNull 
  @Schema(name = "videoPath", example = "media/videos/bug_reproduction.mp4", description = "Chemin ou URL de la vidéo à ajouter", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("videoPath")
  public String getVideoPath() {
    return videoPath;
  }

  public void setVideoPath(String videoPath) {
    this.videoPath = videoPath;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddVideoRequest addVideoRequest = (AddVideoRequest) o;
    return Objects.equals(this.videoPath, addVideoRequest.videoPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(videoPath);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddVideoRequest {\n");
    sb.append("    videoPath: ").append(toIndentedString(videoPath)).append("\n");
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

