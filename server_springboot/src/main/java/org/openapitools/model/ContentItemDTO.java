package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;
import javax.annotation.Generated;

/**
 * Représentation d&#39;un élément de contenu (texte, image, vidéo). Implémente le pattern Composite côté API. 
 */

@Schema(name = "ContentItemDTO", description = "Représentation d'un élément de contenu (texte, image, vidéo). Implémente le pattern Composite côté API. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class ContentItemDTO {

  /**
   * Type de contenu
   */
  public enum TypeEnum {
    TEXT("TEXT"),
    
    IMAGE("IMAGE"),
    
    VIDEO("VIDEO");

    private String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  private String data;

  private JsonNullable<String> metadata = JsonNullable.<String>undefined();

  public ContentItemDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ContentItemDTO(TypeEnum type, String data) {
    this.type = type;
    this.data = data;
  }

  public ContentItemDTO type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Type de contenu
   * @return type
   */
  @NotNull 
  @Schema(name = "type", example = "TEXT", description = "Type de contenu", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public ContentItemDTO data(String data) {
    this.data = data;
    return this;
  }

  /**
   * Données du contenu : - Pour TEXT : le texte complet - Pour IMAGE : chemin du fichier image - Pour VIDEO : chemin du fichier vidéo 
   * @return data
   */
  @NotNull 
  @Schema(name = "data", example = "L'application crash lors de la connexion après 3 tentatives échouées.", description = "Données du contenu : - Pour TEXT : le texte complet - Pour IMAGE : chemin du fichier image - Pour VIDEO : chemin du fichier vidéo ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("data")
  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public ContentItemDTO metadata(String metadata) {
    this.metadata = JsonNullable.of(metadata);
    return this;
  }

  /**
   * Métadonnées optionnelles : - Pour TEXT : null ou vide - Pour IMAGE : caption/légende - Pour VIDEO : durée en secondes (format string) 
   * @return metadata
   */
  
  @Schema(name = "metadata", example = "Message d'erreur affiché lors du crash", description = "Métadonnées optionnelles : - Pour TEXT : null ou vide - Pour IMAGE : caption/légende - Pour VIDEO : durée en secondes (format string) ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("metadata")
  public JsonNullable<String> getMetadata() {
    return metadata;
  }

  public void setMetadata(JsonNullable<String> metadata) {
    this.metadata = metadata;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContentItemDTO contentItemDTO = (ContentItemDTO) o;
    return Objects.equals(this.type, contentItemDTO.type) &&
        Objects.equals(this.data, contentItemDTO.data) &&
        equalsNullable(this.metadata, contentItemDTO.metadata);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, data, hashCodeNullable(metadata));
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
    sb.append("class ContentItemDTO {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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

