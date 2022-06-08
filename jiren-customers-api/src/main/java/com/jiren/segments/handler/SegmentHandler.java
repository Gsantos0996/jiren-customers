package com.jiren.segments.handler;

import com.jiren.customers.adapter.rest.dto.segments.GetSegmentResponseDTO;
import com.jiren.segments.handler.mapper.SegmentMapper;
import com.jiren.segments.service.SegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SegmentHandler {

    private final SegmentService segmentService;
    private final SegmentMapper segmentMapper;

    public List<GetSegmentResponseDTO> listSegment() {
        return segmentMapper.toGetSegmentResponseDtos(segmentService.getSegments());
    }

}
