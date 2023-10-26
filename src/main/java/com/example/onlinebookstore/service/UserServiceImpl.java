package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.example.onlinebookstore.dto.user.UserResponseDto;
import com.example.onlinebookstore.exceptions.RegistrationException;
import com.example.onlinebookstore.mapper.UserMapper;
import com.example.onlinebookstore.model.Role;
import com.example.onlinebookstore.model.RoleName;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.RoleRepository;
import com.example.onlinebookstore.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findUserByEmail(requestDto.email()).isPresent()) {
            throw new RegistrationException("User with this email is already registered: "
                    + requestDto.email());
        }
        User user = userMapper.toUserDto(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setRoles(getDefaultRole());
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    private Set<Role> getDefaultRole() {
        return new HashSet<>(List.of(
                roleRepository.findByRoleName(RoleName.USER)));
    }
}