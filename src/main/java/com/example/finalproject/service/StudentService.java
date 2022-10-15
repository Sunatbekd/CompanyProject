package com.example.finalproject.service;

import com.example.finalproject.dto.*;
import com.example.finalproject.entity.*;
import com.example.finalproject.exeptions.NotFoundException;
import com.example.finalproject.repository.CompanyRepository;
import com.example.finalproject.repository.CourseRepository;
import com.example.finalproject.repository.StudentRepository;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final CourseRepository courseRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public StudentResponse createStudent(StudentRequest request) {
        Student student = mapToEntity(request);
        return mapToResponse(studentRepository.save(student));
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id = %s not found", id)));
        return mapToResponse(student);
    }

    public StudentResponse deleteStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id = %s not found", id)));
        student.setCourse(null);
        studentRepository.delete(student);
        return mapToResponse(student);
    }

    public StudentResponse updateStudentById(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id = %s not found", id)));
        Student student1 = update(student, request);
        return mapToResponse(studentRepository.save(student1));
    }

    public List<StudentResponse> getAlStudents() {
        return studentRepository.getAllStudents();
    }


    public Student update(Student student, StudentRequest request) {
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setStudyFormat(request.getStudyFormat());
        student.getUser().setPassword(passwordEncoder.encode(request.getPassword()));
        student.getUser().setRole(Role.STUDENT);
        return student;
    }


    public Student mapToEntity(StudentRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(
                        () -> new NotFoundException(String.format("Company with id = %s not found", request.getCompanyId())));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NotFoundException(String.format("Course with id = %s not found", request.getCourseId())));
        User user = new User();
        user.setCreated(LocalDate.now());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT);
        return Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .studyFormat(request.getStudyFormat())
                .company(company)
                .course(course)
                .user(user)
                .build();
    }

    public StudentResponse mapToResponse(Student student) {

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setFirstName(student.getFirstName());
        studentResponse.setLastName(student.getLastName());
        studentResponse.setEmail(student.getEmail());
        studentResponse.setPhoneNumber(student.getPhoneNumber());
        studentResponse.setStudyFormat(student.getStudyFormat());
        return studentResponse;
    }


    public StudentResponseView getStudentPagination(String text, int page, int size) {
        StudentResponseView studentResponseView = new StudentResponseView();
        Pageable pageable = PageRequest.of(page, size);
        studentResponseView.setStudentResponse(getPagination(search(text, pageable)));
        studentResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        studentResponseView.setTotalPage(studentRepository.findAll(pageable).getTotalPages());
        return studentResponseView;
    }

    public List<StudentResponse> getPagination(List<Student> students) {
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            studentResponses.add(mapToResponse(student));
        }
        return studentResponses;
    }

    public List<Student> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return studentRepository.searchStudentByFirstName(text, pageable);
    }

}
