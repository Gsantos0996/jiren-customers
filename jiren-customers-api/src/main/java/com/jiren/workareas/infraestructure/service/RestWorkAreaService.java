package com.jiren.workareas.infraestructure.service;

import com.jiren.shared.models.types.WorkAreaEnumerator;
import com.jiren.ubigeo.service.WorkAreaService;
import com.jiren.ubigeo.service.dto.GetSubWorkAreaServiceDTO;
import com.jiren.ubigeo.service.dto.GetWorkAreaServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RestWorkAreaService implements WorkAreaService {

    @Override
    public List<GetWorkAreaServiceDTO> getWorkAreas() {
        return Stream.of(WorkAreaEnumerator.values())
                .map(workAreaEnumerator -> new GetWorkAreaServiceDTO(workAreaEnumerator.getCode(),workAreaEnumerator.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetSubWorkAreaServiceDTO> getSubWorkAreaByWorkArea(String workAreaId) {
        return WorkAreaEnumerator.of(workAreaId).getSubWorkAreas()
                .stream().map(subWorkAreaEnumerator -> new GetSubWorkAreaServiceDTO(subWorkAreaEnumerator.getCode(), subWorkAreaEnumerator.getDescription()))
                .collect(Collectors.toList());
    }
}
