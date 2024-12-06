package com.adamnagyan.yahoofinancewebapi.auth.mapper;

import com.adamnagyan.yahoofinancewebapi.auth.dto.AuthenticationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthenticationRequestMapper {

	AuthenticationRequestMapper INSTANCE = Mappers.getMapper(AuthenticationRequestMapper.class);

	AuthenticationResponseDto authenticationResponseToDTO(String token);

}
