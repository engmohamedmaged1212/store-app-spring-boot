package com.codewithmosh.store.Controllers;
import java.util.*;

import com.codewithmosh.store.dtos.ChangePasswordDto;
import com.codewithmosh.store.dtos.RegisterUserRequest;
import com.codewithmosh.store.dtos.UpdateUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mapper.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import com.codewithmosh.store.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestParam(required = false , defaultValue = "" , name = "sort") String sortby
    ){
        if(!Set.of("name" , "email").contains(sortby))
            sortby ="name";
        return userService.getAllUsers(sortby);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "id") Long id) {
        return  ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping
    public ResponseEntity<?> registerUser(
           @Valid @RequestBody RegisterUserRequest registerUserRequest
            , UriComponentsBuilder uriComponentsBuilder
    ){
        var userDto = userService.registerUser(registerUserRequest);

        var uri =uriComponentsBuilder.path("users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") long id
            ,@RequestBody UpdateUserRequest updateUserRequest
    ){
        var userDto = userService.updateUser(id , updateUserRequest);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
          @PathVariable  long id
          ,@RequestBody ChangePasswordDto changePasswordDto
    ){
        userService.changePassword(id , changePasswordDto);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
