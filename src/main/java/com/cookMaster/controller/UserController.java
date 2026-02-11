package com.cookMaster.controller;

import com.cookMaster.dto.UserDTO;
import com.cookMaster.service.userService.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        log.info("POST user : {}", userDTO.getName());
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getAllUsers();
        log.info("GET users : {}", users);
        return ResponseEntity.ok(users);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        log.info("GET user by id : {} , {}", id, userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("user/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUserById(id,userDTO));
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("OK");
    }

}