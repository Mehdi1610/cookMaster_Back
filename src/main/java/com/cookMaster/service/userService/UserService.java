package com.cookMaster.service.userService;

import com.cookMaster.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    void deleteUserById(Long id);
    UserDTO updateUserById(Long id, UserDTO userDTO);
}












































