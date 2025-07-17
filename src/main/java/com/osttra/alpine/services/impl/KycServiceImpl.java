package com.osttra.alpine.services.impl;

import com.osttra.alpine.dtos.KycResponseDto;
import com.osttra.alpine.entities.KycDetails;
import com.osttra.alpine.exceptions.ProcessNotFoundException;
import com.osttra.alpine.exceptions.TaskNotFoundException;
import com.osttra.alpine.repositories.KycDetailsRepository;
import com.osttra.alpine.services.KycService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {

    private final KycDetailsRepository kycDetailsRepository;
    private final ModelMapper modelMapper;

    @Override
    public KycResponseDto getDetailsByProcessId(String processInstanceId) {
        KycDetails kycDetails = findKycByProcessId(processInstanceId);
        return modelMapper.map(kycDetails, KycResponseDto.class);
    }

    @Override
    public void updateTaskIdByProcessId(String processInstanceId,String taskId) {
        KycDetails kycDetails = findKycByProcessId(processInstanceId);
        kycDetails.setTaskId(taskId);
        kycDetailsRepository.save(kycDetails);
    }

    @Override
    public KycResponseDto updateKycDetailsByTaskId(String taskId, Map<String, Object> updates) {
        KycDetails kycDetails = findKycDetailsByTaskId(taskId);
        updates.forEach((field,value)->{
            Field fieldTobeUpdated = ReflectionUtils.findField(KycDetails.class,field);
            fieldTobeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldTobeUpdated,kycDetails,value);
        });
        return modelMapper.map(kycDetailsRepository.save(kycDetails), KycResponseDto.class);
    }

    @Override
    public KycResponseDto createProcessId(String processId) {
        KycDetails kycDetails = new KycDetails();
        kycDetails.setProcessInstanceId(processId);
        kycDetailsRepository.save(kycDetails);
        return modelMapper.map(kycDetails, KycResponseDto.class);
    }

    public KycDetails findKycByProcessId(String processInstanceId)
    {
        return  kycDetailsRepository.findByProcessInstanceId(processInstanceId)
                .orElseThrow(()->new ProcessNotFoundException("Process id doesn't exists!"));
    }

    public KycDetails findKycDetailsByTaskId(String taskId)
    {
        return  kycDetailsRepository.findByTaskId(taskId)
                .orElseThrow(()->new TaskNotFoundException("Task id doesn't exists!"));
    }
}
