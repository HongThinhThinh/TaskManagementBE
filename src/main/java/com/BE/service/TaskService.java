package com.BE.service;

import com.BE.enums.StatusEnum;
import com.BE.model.entity.Task;
import com.BE.model.request.TaskRequest;
import com.BE.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    public Task createNewTask(TaskRequest taskRequest){
    Task task = new Task();
    task.setDateCreate(taskRequest.getDate());
    task.setDeadline(taskRequest.getDeadline());
    task.setTitle(taskRequest.getTitle());
    task.setDescription(taskRequest.getDescription());
    task.setStatusEnum(StatusEnum.ACTIVE);
    return  taskRepository.save(task);
    }

    public List<Task> getAllTask() {
        return taskRepository.findByStatusEnum(StatusEnum.ACTIVE);
    }

    public Task getTaskById(long id) {
    return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public Task deleteTask(long id) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    task.setStatusEnum(StatusEnum.DELETE);
    return taskRepository.save(task);
    }

    public Task updateTask(long id,TaskRequest taskRequest) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    task.setDescription(taskRequest.getDescription());
    task.setTitle(taskRequest.getTitle());
    task.setDeadline(taskRequest.getDeadline());
    return taskRepository.save(task);
    }
    public Task updateDoneStatus(long id) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        task.setStatusEnum(StatusEnum.DONE);
    return taskRepository.save(task);
    }
}
