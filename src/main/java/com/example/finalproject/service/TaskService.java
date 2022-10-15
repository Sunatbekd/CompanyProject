package com.example.finalproject.service;

import com.example.finalproject.dto.TaskRequest;
import com.example.finalproject.dto.TaskResponse;
import com.example.finalproject.entity.Lesson;
import com.example.finalproject.entity.Task;
import com.example.finalproject.exeptions.NotFoundException;
import com.example.finalproject.repository.LessonRepository;
import com.example.finalproject.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final LessonRepository lessonRepository;


    public TaskResponse createTask(TaskRequest request){
        Task task = mapToEntity(request);
        return mapToResponse(taskRepository.save(task));
    }

    public TaskResponse getByTaskId(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Task with id = %s not found",id)));
        return mapToResponse(task);
    }

    public TaskResponse deleteTaskById(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Task with id = %s not found",id)));
        task.setLesson(null);
        taskRepository.delete(task);
        return mapToResponse(task);
    }

    public TaskResponse updateTaskById(Long id,TaskRequest request){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Task with id = %s not found",id)));
        Task task1 = update(task,request);
        return mapToResponse(task1);
    }

    public List<TaskResponse>getAllTask(){
        return taskRepository.getAllTasks();
    }

    public Task update (Task task,TaskRequest request){
        task.setTaskName(request.getTaskName());
        task.setTaskText(request.getTaskText());
        task.setDeadline(request.getDeadline());
        return task;
    }

    public Task mapToEntity(TaskRequest request){
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(()-> new NotFoundException("Lesson with id = %s not found"));
        Task task = new Task();
        task.setTaskName(request.getTaskName());
        task.setTaskText(request.getTaskText());
        task.setDeadline(request.getDeadline());
        task.setLesson(lesson);
        return task;
    }

    public TaskResponse mapToResponse(Task task){
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTaskName(task.getTaskName());
        taskResponse.setTaskText(task.getTaskText());
        taskResponse.setDeadline(task.getDeadline());
        return taskResponse;
    }
}
