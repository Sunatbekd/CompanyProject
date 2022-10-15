package com.example.finalproject.api;

import com.example.finalproject.dto.TaskRequest;
import com.example.finalproject.dto.TaskResponse;
import com.example.finalproject.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
@PreAuthorize("hasAuthority('INSTUCTOR')")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request){
        return taskService.createTask(request);
    }

    @GetMapping("{id}")
    public TaskResponse getTaskById(@PathVariable Long id){
        return taskService.getByTaskId(id);
    }

    @DeleteMapping("{id}")
    public TaskResponse deleteTaskById(@PathVariable Long id){
        return taskService.deleteTaskById(id);
    }

    @PutMapping("{id}")
    public TaskResponse updateTaskById(@PathVariable Long id,@RequestBody TaskRequest request){
        return taskService.updateTaskById(id,request);
    }
    @GetMapping
    public List<TaskResponse>getAll(){
        return taskService.getAllTask();
    }

}
