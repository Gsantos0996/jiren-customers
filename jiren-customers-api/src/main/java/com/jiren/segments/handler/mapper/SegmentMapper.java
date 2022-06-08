package com.jiren.segments.handler.mapper;

import com.jiren.customers.adapter.rest.dto.segments.GetSegmentResponseDTO;
import com.jiren.segments.service.dto.GetSegmentServiceDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SegmentMapper {

    List<GetSegmentResponseDTO> toGetSegmentResponseDtos(List<GetSegmentServiceDTO> segmentServiceDTOS);

}
