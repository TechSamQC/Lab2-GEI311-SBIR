package org.openapitools.api;

import org.openapitools.model.UserCreate;
import org.openapitools.model.UserDTO;
import org.openapitools.model.UserUpdate;
import org.openapitools.service.UserService;
import org.openapitools.mapper.DomainMapper;
import org.openapitools.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
@Controller
@RequestMapping("${openapi.systmeDeGestionDeTicketsSBIRAPIREST.base-path:/api/v1}")
public class UsersApiController implements UsersApi {

    private final NativeWebRequest request;
    private final UserService userService;

    @Autowired
    public UsersApiController(NativeWebRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreate userCreate) {
        try {
            String role = userCreate.getRole() != null ? userCreate.getRole().getValue() : "USER";
            User user = userService.createUser(userCreate.getName(), userCreate.getEmail(), role);
            
            UserDTO userDTO = DomainMapper.toUserDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDTO> userDTOs = DomainMapper.toUserDTOList(users);
            return new ResponseEntity<>(userDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer userId) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            UserDTO userDTO = DomainMapper.toUserDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") Integer userId, @Valid @RequestBody UserUpdate userUpdate) {
        try {
            if (!userService.userExists(userId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String name = userUpdate.getName();
            String email = userUpdate.getEmail();
            String role = userUpdate.getRole() != null ? userUpdate.getRole().getValue() : null;

            User updatedUser = userService.updateUser(userId, name, email, role);
            if (updatedUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            UserDTO userDTO = DomainMapper.toUserDTO(updatedUser);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Integer userId) {
        try {
            boolean deleted = userService.deleteUser(userId);
            if (!deleted) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
