package com.example.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructors")
@Setter @Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(generator = "ins_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ins_gen", sequenceName = "instructor_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String specialization;
    private boolean isActive = true;
    private LocalDateTime created;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.DETACH})
    private List<Course> courses;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    private Company company;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user;

    public Instructor(String firstName, String lastName, String phoneNumber, String email, String specialization, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.specialization = specialization;
        this.courses = courses;
    }

    public void addCourse(Course course){
        if (courses==null){
            courses = new ArrayList<>();
        }
        courses.add(course);
    }
}
