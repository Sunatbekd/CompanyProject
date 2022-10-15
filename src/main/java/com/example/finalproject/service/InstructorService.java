package com.example.finalproject.service;

import com.example.finalproject.dto.*;
import com.example.finalproject.entity.*;
import com.example.finalproject.exeptions.NotFoundException;
import com.example.finalproject.repository.CompanyRepository;
import com.example.finalproject.repository.CourseRepository;
import com.example.finalproject.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final CompanyRepository companyRepository;
    private final CourseRepository courseRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public InstructorResponse saveInstructor(InstructorRequest request) {
        Instructor instructor = mapToEntity(request);
        return mapToResponse(instructorRepository.save(instructor));
    }

    public InstructorResponse deleteInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Instructor with id = %s not found", id)));
        for (Course c : instructor.getCourses()) {
            c.setInstructors(null);
        }
        instructor.setCompany(null);
        instructorRepository.delete(instructor);
        return mapToResponse(instructor);
    }

    public InstructorResponse getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Instructor with id = %s not found", id)));
        return mapToResponse(instructor);
    }

    public InstructorResponse updateInstructorById(Long id, InstructorRequest request) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Instructor with id = %s not found", id)));
        Instructor instructor1 = update(instructor, request);
        return mapToResponse(instructorRepository.save(instructor1));
    }

//    public List<InstructorResponse> getAll() {
//        return instructorRepository.getAllInstructors();
//    }


    public Instructor update(Instructor instructor, InstructorRequest request) {
        instructor.setFirstName(request.getFirstName());
        instructor.setLastName(request.getLastName());
        instructor.setPhoneNumber(request.getPhoneNumber());
        instructor.setSpecialization(request.getSpecialization());
        instructor.getUser().setEmail(request.getEmail());
        instructor.getUser().setRole(Role.INSTRUCTOR);
        instructor.getUser().setPassword(passwordEncoder.encode(request.getPassword()));
        return instructorRepository.save(instructor);
    }


    public Instructor mapToEntity(InstructorRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(()-> new NotFoundException(String.format("Company with id = %s not found",request.getCompanyId())));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(()-> new NotFoundException(String.format("Course with id = %s not found",request.getCourseId())));
        User user = new User();
        user.setCreated(LocalDate.now());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.INSTRUCTOR);
        return Instructor.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .specialization(request.getSpecialization())
                .created(LocalDateTime.now())
                .company(company)
                .courses(List.of(course))
                .user(user)
                .build();

    }


    public InstructorResponse mapToResponse(Instructor instructor) {
        List<CourseResponse> courseResponses = new ArrayList<>();
        for (Course course: instructor.getCourses()){
            CourseResponse courseResponse = new CourseResponse(course.getId(),course.getCourseName(),course.getDurationInMonth(),
                    course.getDateOfStart(),course.getImage(),course.getDescription());
            courseResponses.add(courseResponse);
        }
        return InstructorResponse.builder()
                .id(instructor.getId())
                .firstName(instructor.getFirstName())
                .lastName(instructor.getLastName())
                .email(instructor.getEmail())
                .phoneNumber(instructor.getPhoneNumber())
                .specialization(instructor.getSpecialization())
                .courseResponse(courseResponses)
                .build();
    }

    public InstructorResponseView getInstructorPagination(String text, int page, int size) {
        InstructorResponseView instructorResponseView = new InstructorResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        instructorResponseView.setInstructorResponse(getAll(search(text, pageable)));
        instructorResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        instructorResponseView.setTotalPage(instructorRepository.findAll(pageable).getTotalPages());
        return instructorResponseView;
    }

    public List<InstructorResponse> getAll(List<Instructor> instructors) {
        List<InstructorResponse> instructorResponses = new ArrayList<>();
        for (Instructor instructor : instructors) {
            instructorResponses.add(mapToResponse(instructor));
        }
        return instructorResponses;
    }

    public List<Instructor> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return instructorRepository.searchByInstructorName(text, pageable);
    }

}
