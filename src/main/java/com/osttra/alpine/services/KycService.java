package com.osttra.alpine.services;

import com.osttra.alpine.dtos.KycResponseDto;

import java.util.Map;


public interface KycService {

    KycResponseDto getDetailsByProcessId(String processInstanceId);
    void updateTaskIdByProcessId(String processInstanceId,String taskId);
    KycResponseDto updateKycDetailsByTaskId(String taskId, Map<String,Object> updates);
    KycResponseDto createProcessId(String processId);
}
