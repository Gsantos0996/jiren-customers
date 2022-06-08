package com.jiren.workareas.handler.mapper;

import com.jiren.customers.adapter.rest.dto.workareas.GetSubWorkAreaResponseDTO;
import com.jiren.customers.adapter.rest.dto.workareas.GetWorkAreaResponseDTO;
import com.jiren.ubigeo.service.dto.GetSubWorkAreaServiceDTO;
import com.jiren.ubigeo.service.dto.GetWorkAreaServiceDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkAreaMapper {

    List<GetWorkAreaResponseDTO> toGetWorkAreaResponseDtos(List<GetWorkAreaServiceDTO> workAreaServiceDTOList);

    List<GetSubWorkAreaResponseDTO> toGetSubWorkAreaResponseDtos(List<GetSubWorkAreaServiceDTO> subWorkAreaServiceDTOList);

}
