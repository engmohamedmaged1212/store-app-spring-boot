package com.codewithmosh.store.mapper;

import com.codewithmosh.store.dtos.RegisterUserRequest;
import com.codewithmosh.store.dtos.UpdateUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
     User toEntity(UserDto userDto);
     User registerRequestToEntity(RegisterUserRequest registerUserRequest);
     void update(UpdateUserRequest source, @MappingTarget User target);

}
