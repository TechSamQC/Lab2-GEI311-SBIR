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
 * Représentation d&#39;un utilisateur du système
 */

@Schema(name = "UserDTO", description = "Représentation d'un utilisateur du système")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class UserDTO {

  private Integer userID;

  private String name;

  private String email;

  /**
   * Rôle de l'utilisateur dans le système
   */
  public enum RoleEnum {
    ADMIN("ADMIN"),
    
    DEVELOPER("DEVELOPER"),
    
    USER("USER");

    private String value;

    RoleEnum(String value) {
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
    public static RoleEnum fromValue(String value) {
      for (RoleEnum b : RoleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private RoleEnum role;

  private Boolean isAdmin;

  public UserDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UserDTO(Integer userID, String name, String email, RoleEnum role, Boolean isAdmin) {
    this.userID = userID;
    this.name = name;
    this.email = email;
    this.role = role;
    this.isAdmin = isAdmin;
  }

  public UserDTO userID(Integer userID) {
    this.userID = userID;
    return this;
  }

  /**
   * Identifiant unique de l'utilisateur
   * @return userID
   */
  @NotNull 
  @Schema(name = "userID", example = "1", description = "Identifiant unique de l'utilisateur", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("userID")
  public Integer getUserID() {
    return userID;
  }

  public void setUserID(Integer userID) {
    this.userID = userID;
  }

  public UserDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Nom complet de l'utilisateur
   * @return name
   */
  @NotNull @Size(min = 1, max = 100) 
  @Schema(name = "name", example = "Utilisateur1", description = "Nom complet de l'utilisateur", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserDTO email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Adresse email de l'utilisateur
   * @return email
   */
  @NotNull @javax.validation.constraints.Email 
  @Schema(name = "email", example = "utilisateur1@uqac.ca", description = "Adresse email de l'utilisateur", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserDTO role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * Rôle de l'utilisateur dans le système
   * @return role
   */
  @NotNull 
  @Schema(name = "role", example = "DEVELOPER", description = "Rôle de l'utilisateur dans le système", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("role")
  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  public UserDTO isAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
    return this;
  }

  /**
   * Indique si l'utilisateur a les privilèges admin
   * @return isAdmin
   */
  @NotNull 
  @Schema(name = "isAdmin", example = "false", description = "Indique si l'utilisateur a les privilèges admin", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("isAdmin")
  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDTO userDTO = (UserDTO) o;
    return Objects.equals(this.userID, userDTO.userID) &&
        Objects.equals(this.name, userDTO.name) &&
        Objects.equals(this.email, userDTO.email) &&
        Objects.equals(this.role, userDTO.role) &&
        Objects.equals(this.isAdmin, userDTO.isAdmin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userID, name, email, role, isAdmin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDTO {\n");
    sb.append("    userID: ").append(toIndentedString(userID)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    isAdmin: ").append(toIndentedString(isAdmin)).append("\n");
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

