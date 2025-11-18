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
 * Requête de création d&#39;un nouvel utilisateur
 */

@Schema(name = "UserCreate", description = "Requête de création d'un nouvel utilisateur")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
public class UserCreate {

  private String name;

  private String email;

  /**
   * Rôle
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

  public UserCreate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UserCreate(String name, String email, RoleEnum role) {
    this.name = name;
    this.email = email;
    this.role = role;
  }

  public UserCreate name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Nom complet
   * @return name
   */
  @NotNull 
  @Schema(name = "name", example = "William Derreck", description = "Nom complet", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserCreate email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Adresse email
   * @return email
   */
  @NotNull @javax.validation.constraints.Email 
  @Schema(name = "email", example = "william.derreck@uqac.ca", description = "Adresse email", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserCreate role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * Rôle
   * @return role
   */
  @NotNull 
  @Schema(name = "role", example = "DEVELOPER", description = "Rôle", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("role")
  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserCreate userCreate = (UserCreate) o;
    return Objects.equals(this.name, userCreate.name) &&
        Objects.equals(this.email, userCreate.email) &&
        Objects.equals(this.role, userCreate.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, email, role);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserCreate {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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

