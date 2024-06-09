package com.BE.controller;


import com.BE.model.entity.Task;
import com.BE.model.request.TaskRequest;
import com.BE.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "api")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("")
    public ResponseEntity createNewTask(@RequestBody TaskRequest taskRequest) {
        Task task = taskService.createNewTask(taskRequest);
        return ResponseEntity.ok(task);
    }

    @GetMapping()
    public ResponseEntity getAllTask() {
        return ResponseEntity.ok(taskService.getAllTask());
    }

    @GetMapping("{id}")
    public ResponseEntity getTaskById(@PathVariable long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteTask(@PathVariable long id){
        return ResponseEntity.ok(taskService.deleteTask(id));
    }
    @PutMapping("{id}")
    public ResponseEntity updateTask(@PathVariable long id ,@RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(taskService.updateTask(id,taskRequest));
    }
    @PutMapping("/done/{id}")
    public ResponseEntity updateDoneStatus(@PathVariable long id){
        return ResponseEntity.ok(taskService.updateDoneStatus(id));
    }
}

