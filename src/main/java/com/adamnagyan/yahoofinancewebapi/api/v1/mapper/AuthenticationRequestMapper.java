package com.adamnagyan.yahoofinancewebapi.api.v1.mapper;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.AuthenticationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthenticationRequestMapper {

  AuthenticationRequestMapper INSTANCE = Mappers.getMapper(AuthenticationRequestMapper.class);

  AuthenticationResponseDto authenticationResponseToDTO(String token);
}
