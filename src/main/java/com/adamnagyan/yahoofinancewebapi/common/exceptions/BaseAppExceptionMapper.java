package com.adamnagyan.yahoofinancewebapi.common.exceptions;

import com.adamnagyan.yahoofinancewebapi.common.dto.ExceptionDto;
import org.mapstruct.Mapper;

@Mapper
public interface BaseAppExceptionMapper {

	ExceptionDto toExceptionDto(BaseAppException baseAppException);

}
