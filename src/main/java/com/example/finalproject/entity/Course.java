package com.example.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(generator = "course_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "course_gen", sequenceName = "course_seq", allocationSize = 1)
    private Long id;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "duration_in_month")
    private String durationInMonth;
    @Column(name = "date_of_start")

    private LocalDate dateOfStart;
    private String image;
    private String description;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    private Company company;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH},mappedBy = "courses")
    private List<Instructor> instructors;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "course")
    private List<Lesson>lessons;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
    private List<Student> students;

    public Course(String courseName, String durationInMonth, LocalDate dateOfStart, String image, String description, Company company) {
        this.courseName = courseName;
        this.durationInMonth = durationInMonth;
        this.dateOfStart = dateOfStart;
        this.image = image;
        this.description = description;
        this.company = company;
    }

    public void addInstructor(Instructor instructor){
        if (instructors==null){
            instructors = new ArrayList<>();
        }
        instructors.add(instructor);

    }

    public void addStudent (Student student){
        if (students==null){
            students = new ArrayList<>();
        }
        students.add(student);
    }

    public void addLesson(Lesson lesson){
        if (lessons==null){
            lessons = new ArrayList<>();
        }
        lessons.add(lesson);
    }
}
