package com.example.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter @Setter
@NoArgsConstructor
public class Task {
    @Id
    @SequenceGenerator(name = "task_gen",sequenceName = "task_seq",allocationSize = 1)
    @GeneratedValue(generator = "task_gen",strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "task_text")
    private String taskText;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate deadline;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    private Lesson lesson;
}
