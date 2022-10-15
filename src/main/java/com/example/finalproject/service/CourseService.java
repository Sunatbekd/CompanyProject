package com.example.finalproject.service;

import com.example.finalproject.dto.*;
import com.example.finalproject.entity.Company;
import com.example.finalproject.entity.Course;
import com.example.finalproject.entity.Instructor;
import com.example.finalproject.exeptions.NotFoundException;
import com.example.finalproject.repository.CompanyRepository;
import com.example.finalproject.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    private final CompanyRepository companyRepository;


    public CourseResponse createCourse(CourseRequest request) {
        Course course = mapToEntity(request);
        return mapToResponse(courseRepository.save(course));
    }

    public CourseResponse getCourseById(Long companyId,Long courseId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()-> new NotFoundException(String.format("Company with id = %s not found",companyId)));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(String.format("Course with id = %s not found", courseId)));
        course.setCompany(company);
        return mapToResponse(course);
    }

    public List<CourseResponse> getCourseByCompanyId(Long id) {
        List<CourseResponse > responses = new ArrayList<>();
        for (Course course:courseRepository.findCourseByCompanyId(id)) {
            responses.add(mapToResponse(course));
        }
        return responses;

    }

    public CourseResponse deleteCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Course with id = %s not found", id)));
        for (Instructor i : course.getInstructors()) {
            i.setCourses(null);
        }
        course.setCompany(null);
        courseRepository.delete(course);
        return mapToResponse(course);
    }

    public CourseResponse updateCourseById(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Course with id = %s not found", id)));
        Course course1 = updateCourse(course,request);
        return mapToResponse(courseRepository.save(course1));
    }

    public CourseResponseView getAllCoursePagination(String text,int page,int size){
        CourseResponseView courseResponseView = new CourseResponseView();
        Pageable pageable = PageRequest.of(page-1,size);
        courseResponseView.setCourseResponses(getCourse(searchCourse(text,pageable)));
        courseResponseView.setCurrentPage(pageable.getPageNumber()+1);
        courseResponseView.setTotalPage(courseRepository.findAll(pageable).getTotalPages());
        return courseResponseView;
    }

    public List<CourseResponse>getCourse(List<Course> courses){
        List<CourseResponse>courseResponses = new ArrayList<>();
        for (Course course : courses){
            courseResponses.add(mapToResponse(course));
        }
        return courseResponses;
    }

    private List<Course>searchCourse(String name,Pageable pageable){
        String text = name==null ? "" : name;
        return courseRepository.searByCourseName(text,pageable);
    }

    public Course updateCourse(Course course, CourseRequest request) {
        String courseName = course.getCourseName();
        String newCourseName = request.getCourseName();
        if (newCourseName != null && !newCourseName.equals(courseName)) {
            course.setCourseName(newCourseName);
        }
        String description = course.getDescription();
        String newDescription = request.getDescription();
        if (newDescription != null && !newDescription.equals(description)) {
            course.setDescription(newDescription);
        }
        String duration = course.getDurationInMonth();
        String newDuration = request.getDurationInMonth();
        if (newDuration != null && !newDuration.equals(duration)) {
            course.setDurationInMonth(newDuration);
        }
        LocalDate dateOfStart = course.getDateOfStart();
        LocalDate newDateOfStart = request.getDateOfStart();
        if (newDateOfStart != null && !newDateOfStart.equals(dateOfStart)) {
            course.setDateOfStart(request.getDateOfStart());
        }
        return courseRepository.save(course);
    }

    public Course mapToEntity(CourseRequest request) {
        Course course = new Course();
        Company company = companyRepository.findById(request.getCompanyId()).orElseThrow(
                () -> new NotFoundException(String.format("company with id - %s not found!"
                        , request.getCompanyId())));
        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setDurationInMonth(request.getDurationInMonth());
        course.setDateOfStart(request.getDateOfStart());
        course.setCompany(company);
        company.addCourses(course);
        return courseRepository.save(course);
    }

    public CourseResponse mapToResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .durationInMonth(course.getDurationInMonth())
                .dateOfStart(course.getDateOfStart())
                .image(course.getImage())
                .description(course.getDescription())
                .build();
    }

}
