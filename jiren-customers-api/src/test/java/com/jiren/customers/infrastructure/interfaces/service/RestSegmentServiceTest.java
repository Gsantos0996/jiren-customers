package com.jiren.customers.infrastructure.interfaces.service;

import com.jiren.customers.UnitTestBase;
import com.jiren.segments.infraestructure.service.RestSegmentService;
import com.jiren.segments.service.SegmentService;
import com.jiren.segments.service.dto.GetSegmentServiceDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RestSegmentServiceTest extends UnitTestBase {

    private static final short COUNT_SEGMENTS = 4;
    private SegmentService segmentService;

    @BeforeEach
    void setUp() {
        this.segmentService = new RestSegmentService();
    }

    @Test
    void givenValidParamWhenListWorkAreaThenSuccess() {

        List<GetSegmentServiceDTO> getSegmentServiceDTOS = segmentService.getSegments();

        Assertions.assertEquals(COUNT_SEGMENTS,getSegmentServiceDTOS.size());
    }
    
}
