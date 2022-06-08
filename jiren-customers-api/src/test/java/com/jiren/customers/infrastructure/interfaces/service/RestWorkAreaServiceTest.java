package com.jiren.customers.infrastructure.interfaces.service;

import com.jiren.customers.UnitTestBase;
import com.jiren.shared.models.types.WorkAreaEnumerator;
import com.jiren.workareas.infraestructure.service.RestWorkAreaService;
import com.jiren.ubigeo.service.WorkAreaService;
import com.jiren.ubigeo.service.dto.GetSubWorkAreaServiceDTO;
import com.jiren.ubigeo.service.dto.GetWorkAreaServiceDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RestWorkAreaServiceTest extends UnitTestBase {

    private static final short COUNT_WORK_AREAS = 2;
    private static final short COUNT_GASTRONOMY_SUBWORK_AREAS = 7;
    private WorkAreaService workAreaService;

    @BeforeEach
    void setUp() {
        this.workAreaService = new RestWorkAreaService();
    }

    @Test
    void givenValidParamWhenListWorkAreaThenSuccess() {

        List<GetWorkAreaServiceDTO> getWorkAreaServiceDTOS = workAreaService.getWorkAreas();

        Assertions.assertEquals(COUNT_WORK_AREAS,getWorkAreaServiceDTOS.size());
    }

    @Test
    void givenValidParamWhenListSubWorkAreaThenSuccess() {

        List<GetSubWorkAreaServiceDTO> getSubWorkAreaServiceDTOS = workAreaService.getSubWorkAreaByWorkArea(WorkAreaEnumerator.GASTRONOMY.getCode());

        Assertions.assertEquals(COUNT_GASTRONOMY_SUBWORK_AREAS,getSubWorkAreaServiceDTOS.size());
    }


}
