package com.diveinku.jasome.src.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class ResumeDto {
    private String title;
    private List<QnaDto> qnas;
}
