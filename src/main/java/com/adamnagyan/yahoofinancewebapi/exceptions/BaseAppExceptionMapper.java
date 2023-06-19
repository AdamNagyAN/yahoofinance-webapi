package com.adamnagyan.yahoofinancewebapi.exceptions;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.ExceptionDto;
import org.mapstruct.Mapper;

@Mapper
public interface BaseAppExceptionMapper {
  ExceptionDto toExceptionDto(BaseAppException baseAppException);
}
