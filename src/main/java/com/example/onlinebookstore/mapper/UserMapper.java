package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.dto.UserResponseDto;
import com.example.onlinebookstore.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface UserMapper {
    UserResponseDto toUserResponse(User user);

    UserDto toUserDto(User user);

}
