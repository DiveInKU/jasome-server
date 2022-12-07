package com.diveinku.jasome.src.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "word_count")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordCount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_count_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @Column(length = 20)
    private String word;

    private int count;

    public WordCount(Interview interview, String word, int count) {
        this.interview = interview;
        this.word = word;
        this.count = count;
    }
}
