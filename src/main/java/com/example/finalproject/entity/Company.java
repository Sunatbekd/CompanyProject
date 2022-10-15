package com.example.finalproject.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(generator = "company_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "company_gen", sequenceName = "company_seq", allocationSize = 1)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "located_country")
    private String locatedCountry;

    @CreatedDate
    private LocalDateTime created;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Course> courses;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
    private List<Student> students;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Instructor> instructors;

    public void addCourses(Course course) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        courses.add(course);
    }
}
