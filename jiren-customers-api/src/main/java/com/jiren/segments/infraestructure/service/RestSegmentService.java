package com.jiren.segments.infraestructure.service;

import com.jiren.segments.domain.model.types.SegmentEnumerator;
import com.jiren.segments.service.SegmentService;
import com.jiren.segments.service.dto.GetSegmentServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RestSegmentService implements SegmentService {

    @Override
    public List<GetSegmentServiceDTO> getSegments() {
        return Stream.of(SegmentEnumerator.values())
                .map(segmentEnumerator -> new GetSegmentServiceDTO(segmentEnumerator.getCode(),segmentEnumerator.getDescription()))
                .collect(Collectors.toList());
    }
}
