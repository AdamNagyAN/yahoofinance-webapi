package com.adamnagyan.yahoofinancewebapi.api.v1.mapper.auth;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthenticationMapper {

  AuthenticationMapper INSTANCE = Mappers.getMapper(AuthenticationMapper.class);

  AuthenticationResponseDto authenticationResponseToDTO(String token);
}
