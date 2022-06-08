package com.jiren.sourcechannels.infraestructure.service;

import com.jiren.sourcechannels.domain.model.types.SourceChannelEnumerator;
import com.jiren.sourcechannels.service.SourceChannelService;
import com.jiren.sourcechannels.service.dto.GetSourceChannelServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RestSourceChannelService implements SourceChannelService {

    @Override
    public List<GetSourceChannelServiceDTO> getSourceChannels() {
        return Stream.of(SourceChannelEnumerator.values())
                .map(sourceChannelEnumerator -> new GetSourceChannelServiceDTO(sourceChannelEnumerator.getCode(),sourceChannelEnumerator.getDescription()))
                .collect(Collectors.toList());
    }
}
