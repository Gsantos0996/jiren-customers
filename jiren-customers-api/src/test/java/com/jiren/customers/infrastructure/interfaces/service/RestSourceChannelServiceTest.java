package com.jiren.customers.infrastructure.interfaces.service;

import com.jiren.customers.UnitTestBase;
import com.jiren.sourcechannels.infraestructure.service.RestSourceChannelService;
import com.jiren.sourcechannels.service.SourceChannelService;
import com.jiren.sourcechannels.service.dto.GetSourceChannelServiceDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RestSourceChannelServiceTest extends UnitTestBase {

    private static final short COUNT_SOURCE_CHANNELS = 4;
    private SourceChannelService sourceChannelService;

    @BeforeEach
    void setUp() {
        this.sourceChannelService = new RestSourceChannelService();
    }

    @Test
    void givenValidParamWhenListWorkAreaThenSuccess() {

        List<GetSourceChannelServiceDTO> getSourceChannelsServiceDTOS = sourceChannelService.getSourceChannels();

        Assertions.assertEquals(COUNT_SOURCE_CHANNELS,getSourceChannelsServiceDTOS.size());
    }
    
}
