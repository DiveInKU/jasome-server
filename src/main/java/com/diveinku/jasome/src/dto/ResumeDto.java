package com.diveinku.jasome.src.dto;

import com.diveinku.jasome.src.domain.ResumeCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class ResumeDto {
    private String title;
    private ResumeCategory resumeCategory;
    private List<QnaDto> qnas;
}
