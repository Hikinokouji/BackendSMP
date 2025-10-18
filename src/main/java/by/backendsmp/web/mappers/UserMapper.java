package by.backendsmp.web.mappers;

import by.backendsmp.entity.User;
import by.backendsmp.web.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserDTO userDTO);
    UserDTO toDTO(User user);
}
