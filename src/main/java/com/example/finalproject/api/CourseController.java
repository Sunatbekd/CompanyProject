package com.example.finalproject.api;

import com.example.finalproject.dto.CourseRequest;
import com.example.finalproject.dto.CourseResponse;
import com.example.finalproject.dto.CourseResponseView;
import com.example.finalproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
@PreAuthorize("hasAuthority('ADMIN')")
public class CourseController {

    private final CourseService courseService;
    @PostMapping
    public CourseResponse saveCourse(@RequestBody CourseRequest request){
        return courseService.createCourse(request);
    }

    @GetMapping("/{companyId}/{courseId}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','ADMIN')")
    public CourseResponse getCourseById(@PathVariable Long companyId,@PathVariable Long courseId){
        return courseService.getCourseById(companyId,courseId);
    }

    @DeleteMapping("{id}")
    public CourseResponse deleteCourseById(@PathVariable Long id){
        return courseService.deleteCourseById(id);
    }

    @PutMapping("{id}")
    public CourseResponse updateCourseById(@PathVariable Long id,
                                           @RequestBody CourseRequest request){
        return courseService.updateCourseById(id,request);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','ADMIN')")
    public CourseResponseView getAllPagination(@RequestParam(name = "text",required = false) String text,
                                               @RequestParam int page,
                                               @RequestParam int size){
        return courseService.getAllCoursePagination(text,page, size);
    }

    @GetMapping("all/{id}")
    public List<CourseResponse> getCoursesByCompanyId(@PathVariable Long id){
        return courseService.getCourseByCompanyId(id);
    }
}
