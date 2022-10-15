package com.example.finalproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "videos")
@Getter @Setter
@NoArgsConstructor
public class Video {
    @Id
    @SequenceGenerator(name = "video_gen",sequenceName = "video_seq",allocationSize = 1)
    @GeneratedValue(generator = "video_gen",strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "video_name")
    private String videoName;
    @Column(name = "video_link")
    private String videoLink;
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    private Lesson lesson;
}
