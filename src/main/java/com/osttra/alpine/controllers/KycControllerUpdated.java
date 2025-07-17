package com.osttra.alpine.controllers;

import com.osttra.alpine.dtos.KycResponseDto;
import com.osttra.alpine.dtos.KycTasks;
import com.osttra.alpine.services.KycService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/kyc/updated")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class KycControllerUpdated {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    RestTemplate restTemplate;

    private final KycService kycService;

    @PostMapping("/start")
    public ResponseEntity<String> startKyc() //@RequestBody Map<String,Object> payload
    {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("Kyc-verification"); //,payload
        KycResponseDto kycResponseDto = kycService.createProcessId(instance.getId());
        log.info("Starting new process with id: {}",instance.getId());
        return ResponseEntity.ok(kycResponseDto.getProcessInstanceId());
    }

    @GetMapping("/get-task-id/{processInstanceId}")
    public ResponseEntity<String> taskId(@PathVariable(name = "processInstanceId") String processInstanceId)
    {
        log.info("Attempting to fetch task with process id: {}",processInstanceId);
        String camundaUrl = "http://localhost:8080/engine-rest/task?processInstanceId="+processInstanceId;
        ResponseEntity<KycTasks[]> response = restTemplate.getForEntity(camundaUrl, KycTasks[].class);
        KycTasks[] kycTasks = response.getBody();
        String taskId = kycTasks[0].getId();
        log.info("Task id fetched successfully.Task id: {}",taskId);
        kycService.updateTaskIdByProcessId(processInstanceId,taskId);
        return ResponseEntity.ok(kycTasks[0].getId());
    }


    @GetMapping("/get-process-variables/{processInstanceId}")
    public ResponseEntity<KycResponseDto> getKycDetails(@PathVariable(name = "processInstanceId") String processInstanceId)
    {
        log.info("Attempting to fetch KYC details for the process-id: {}",processInstanceId);
       KycResponseDto kycResponseDto = kycService.getDetailsByProcessId(processInstanceId);
       log.info("Successfully fetched Kyc details......");
        return ResponseEntity.ok(kycResponseDto);
    }

    @PatchMapping("/complete-task/{taskId}")
    public ResponseEntity<String> completeTask(@PathVariable(name = "taskId")String taskId,
                                               @RequestBody Map<String,Object> payload)
    {
       // taskService.complete(taskId,payload);
        log.info("Attempting to complete task with id: {}",taskId);
        kycService.updateKycDetailsByTaskId(taskId,payload);
        taskService.complete(taskId);
        log.info("Successfully submitted task with id:{}",taskId);
        return ResponseEntity.ok("Task has been completed!");
    }
}
