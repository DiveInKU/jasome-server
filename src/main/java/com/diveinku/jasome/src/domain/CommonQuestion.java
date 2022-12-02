package com.diveinku.jasome.src.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "common_question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonQuestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "common_question_id")
    private Long id;

    @Column(length = 200)
    private String content;
}
