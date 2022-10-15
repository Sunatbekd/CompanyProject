package com.example.finalproject.api;

import com.example.finalproject.dto.StudentRequest;
import com.example.finalproject.dto.StudentResponse;
import com.example.finalproject.dto.StudentResponseView;
import com.example.finalproject.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
@PreAuthorize("hasAuthority('ADMIN')")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentResponse saveStudent(@RequestBody StudentRequest request){
        return studentService.createStudent(request);
    }

    @GetMapping("{id}")
    public StudentResponse getStudentById(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @DeleteMapping("{id}")
    public StudentResponse deleteStudentById(@PathVariable Long id){
        return studentService.deleteStudentById(id);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public StudentResponse updateStudentById(@PathVariable Long id,@RequestBody StudentRequest request){
        return studentService.updateStudentById(id,request);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public List<StudentResponse>getAll(){
        return studentService.getAlStudents();
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public StudentResponseView studentPagination(@RequestParam(name = "text",required = false) String text,
                                                 @RequestParam int page,
                                                 @RequestParam int size){
        return studentService.getStudentPagination(text,page,size);
    }

}
