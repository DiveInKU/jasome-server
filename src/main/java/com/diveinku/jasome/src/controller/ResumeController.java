package com.diveinku.jasome.src.controller;

import com.diveinku.jasome.src.commons.CommonResponse;
import com.diveinku.jasome.src.dto.ResumeDto;
import com.diveinku.jasome.src.dto.ResumePreviewDto;
import com.diveinku.jasome.src.service.ResumeService;
import com.diveinku.jasome.src.util.JwtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private JwtService jwtService;
    private ResumeService resumeService;

    @Autowired
    public ResumeController(JwtService jwtService, ResumeService resumeService) {
        this.jwtService = jwtService;
        this.resumeService = resumeService;
    }

    @GetMapping("")
    @ApiOperation(value = "자기소개서 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message =
                    "2004: 존재하지 않는 유저 <br>"),
    })
    public ResponseEntity<CommonResponse<List<ResumePreviewDto>>> getResumePreviews() {
        long memberId = jwtService.getMemberIdFromJwt();
        return ResponseEntity.ok(CommonResponse.from(resumeService.getMembersResumePreviews(memberId)));
    }

    @PostMapping("")
    @ApiOperation(value = "자기소개서 생성")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message =
                    "2004: 존재하지 않는 유저 <br>"),
    })
    public ResponseEntity<CommonResponse<Long>> createResume(@RequestBody ResumeDto resumeDto) {
        long memberId = jwtService.getMemberIdFromJwt();
        long resumeId = resumeService.createMembersResume(memberId, resumeDto.getTitle(), resumeDto.getQnas());
        return ResponseEntity.ok(CommonResponse.from(resumeId));
    }

    @GetMapping("/{resumeId}")
    @ApiOperation(value = "자기소개서 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message =
                    "2004: 존재하지 않는 유저 <br>" +
                            "3001: 존재하지 않는 자기소개서"),
    })
    public ResponseEntity<CommonResponse<ResumeDto>> getResume(@PathVariable("resumeId") Long resumeId) {
        return ResponseEntity.ok(CommonResponse.from(resumeService.getResumeById(resumeId)));
    }

    @PutMapping("/{resumeId}")
    @ApiOperation(value = "자기소개서 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message =
                    "2004: 존재하지 않는 유저 <br>" +
                            "3001: 존재하지 않는 자기소개서"),
    })
    public ResponseEntity<CommonResponse<Void>> modifyResume(@PathVariable("resumeId") Long resumeId,
                                                             @RequestBody ResumeDto resumeDto) {
        resumeService.updateResume(resumeId, resumeDto.getTitle(), resumeDto.getQnas());
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @DeleteMapping("/{resumeId}")
    @ApiOperation(value = "자기소개서 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message =
                    "2004: 존재하지 않는 유저 <br>" +
                            "3001: 존재하지 않는 자기소개서"),
    })
    public ResponseEntity<CommonResponse<Void>> deleteResume(@PathVariable("resumeId") Long resumeId) {
        resumeService.deleteResumeById(resumeId);
        return ResponseEntity.ok(new CommonResponse<>());
    }
}
