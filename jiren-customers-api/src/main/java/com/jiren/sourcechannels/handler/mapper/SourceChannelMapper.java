package com.jiren.sourcechannels.handler.mapper;

import com.jiren.customers.adapter.rest.dto.sourcechannels.GetSourceChannelResponseDTO;
import com.jiren.sourcechannels.service.dto.GetSourceChannelServiceDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SourceChannelMapper {

    List<GetSourceChannelResponseDTO> toGetSourceChannelResponseDtos(List<GetSourceChannelServiceDTO> sourceChannelServiceDTOList);

}
