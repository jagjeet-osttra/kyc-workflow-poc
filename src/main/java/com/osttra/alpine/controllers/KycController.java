package com.osttra.alpine.controllers;

import com.osttra.alpine.dtos.KycTasks;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/kyc")
@CrossOrigin(origins = "*")
public class KycController {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/start")
    public ResponseEntity<String> startKyc() //@RequestBody Map<String,Object> payload
    {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("Process_0tghgmn"); //,payload
        return ResponseEntity.ok(instance.getId());
    }

    @GetMapping("/get-task-id/{processInstanceId}")
    public ResponseEntity<String> taskId(@PathVariable(name = "processInstanceId") String processInstanceId)
    {
        String camundaUrl = "http://localhost:8080/engine-rest/task?processInstanceId="+processInstanceId;
        ResponseEntity<KycTasks[]> response = restTemplate.getForEntity(camundaUrl, KycTasks[].class);
        KycTasks[] kycTasks = response.getBody();
        return ResponseEntity.ok(kycTasks[0].getId());
    }

    @PostMapping("/complete-task/{taskId}")
    public ResponseEntity<String> completeTask(@PathVariable(name = "taskId")String taskId,
                                               @RequestBody Map<String,Object> payload)
    {
        taskService.complete(taskId,payload);
        return ResponseEntity.ok("Task has been completed!");
    }
}
