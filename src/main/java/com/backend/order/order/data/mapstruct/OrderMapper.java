package com.backend.data.mapstruct;

import com.backend.data.dto.request.UserReqDTO;
import com.backend.data.dto.response.UserDTO;
import com.backend.data.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target="orders", ignore=true)
    UserDTO toDto(UserEntity entity);



    @Mapping(target="id", ignore=true)
    @Mapping(target="userId", ignore=true)
    @Mapping(target="encryptedPwd", ignore=true)
    UserEntity toEntity(UserReqDTO dto);


    List<UserDTO> toDtoList(List<UserEntity> entityList);

	List<UserEntity> toEntityList(List<UserReqDTO> dtoList);


}
