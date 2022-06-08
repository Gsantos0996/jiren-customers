package com.jiren.ubigeo.service;

import com.jiren.ubigeo.service.dto.GetSubWorkAreaServiceDTO;
import com.jiren.ubigeo.service.dto.GetWorkAreaServiceDTO;
import java.util.List;

public interface WorkAreaService {

    List<GetWorkAreaServiceDTO> getWorkAreas();

    List<GetSubWorkAreaServiceDTO> getSubWorkAreaByWorkArea(String workAreaId);

}
