package com.jiren.workareas.handler;

import com.jiren.customers.adapter.rest.dto.workareas.GetSubWorkAreaResponseDTO;
import com.jiren.customers.adapter.rest.dto.workareas.GetWorkAreaResponseDTO;
import com.jiren.workareas.handler.mapper.WorkAreaMapper;
import com.jiren.ubigeo.service.WorkAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkAreaHandler {

    private final WorkAreaService workAreaService;
    private final WorkAreaMapper workAreaMapper;

    public List<GetWorkAreaResponseDTO> listWorkArea() {
        return workAreaMapper.toGetWorkAreaResponseDtos(workAreaService.getWorkAreas());
    }

    public List<GetSubWorkAreaResponseDTO> listSubWorkArea(String workAreaId) {
        return workAreaMapper.toGetSubWorkAreaResponseDtos(workAreaService.getSubWorkAreaByWorkArea(workAreaId));
    }
}
