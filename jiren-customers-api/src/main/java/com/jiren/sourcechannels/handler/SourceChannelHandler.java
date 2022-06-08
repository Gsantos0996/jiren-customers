package com.jiren.sourcechannels.handler;

import com.jiren.customers.adapter.rest.dto.sourcechannels.GetSourceChannelResponseDTO;
import com.jiren.sourcechannels.handler.mapper.SourceChannelMapper;
import com.jiren.sourcechannels.service.SourceChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SourceChannelHandler {

    private final SourceChannelService sourceChannelService;
    private final SourceChannelMapper sourceChannelMapper;

    public List<GetSourceChannelResponseDTO> listSourceChannels() {
        return sourceChannelMapper.toGetSourceChannelResponseDtos(sourceChannelService.getSourceChannels());
    }

}
