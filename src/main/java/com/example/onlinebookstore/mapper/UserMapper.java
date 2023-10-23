package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.dto.CreateUserRequestDto;
import com.example.onlinebookstore.dto.UserDto;
import com.example.onlinebookstore.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface UserMapper {
    User toUser(CreateUserRequestDto createUserRequestDto);

    UserDto toUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(CreateUserRequestDto createUserRequestDto, @MappingTarget User user);
}
