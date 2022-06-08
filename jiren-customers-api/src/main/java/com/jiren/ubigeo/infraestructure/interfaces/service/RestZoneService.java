package com.jiren.ubigeo.infraestructure.interfaces.service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.jiren.ubigeo.domain.model.types.ZoneEnumerator;
import com.jiren.ubigeo.service.ZoneService;
import com.jiren.ubigeo.service.dto.GetZoneServiceDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestZoneService implements ZoneService {

    @Override
    public List<GetZoneServiceDTO> get() {
        return Stream.of(ZoneEnumerator.values())
                .map(x -> new GetZoneServiceDTO(x.getCode(),x.getName()))
                .collect(Collectors.toList());
    }
}
