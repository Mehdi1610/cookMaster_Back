package com.cookMaster.service.userService;

import com.cookMaster.dto.UserDTO;
import com.cookMaster.mapper.UserMapper;
import com.cookMaster.model.User;
import com.cookMaster.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl ( UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userMapper=userMapper;
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userMapper.toEntity(userDTO);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO updateUserById(Long id, UserDTO userDTO) {

        User user = userRepository.findById(id)
                .orElseThrow();
        userMapper.updateUserFromDto(userDTO,user);
        return userMapper.toDto(userRepository.save(user));

    }
}
