package com.diveinku.jasome.src.controller;

import com.diveinku.jasome.src.commons.CommonResponse;
import com.diveinku.jasome.src.dto.InterviewQuestionDto;
import com.diveinku.jasome.src.service.InterviewService;
import com.diveinku.jasome.src.service.S3UploaderService;
import com.diveinku.jasome.src.util.JwtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/interviews")
public class InterviewController {
    private final JwtService jwtService;
    private final InterviewService interviewService;
    private final S3UploaderService s3UploaderService;

    @Autowired
    public InterviewController(JwtService jwtService, InterviewService interviewService, S3UploaderService s3UploaderService) {
        this.jwtService = jwtService;
        this.interviewService = interviewService;
        this.s3UploaderService = s3UploaderService;
    }


    @GetMapping("/questions")
    @ApiOperation(value = "사용자 정의 질문 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message =
                    "2004: 존재하지 않는 유저 <br>"),
    })
    public ResponseEntity<CommonResponse<List<InterviewQuestionDto>>> getInterviewQuestions() {
        long memberId = jwtService.getMemberIdFromJwt();
        return ResponseEntity.ok(new CommonResponse<>(interviewService.getQuestions(memberId)));
    }

    @PostMapping("/questions")
    @ApiOperation(value = "사용자 정의 질문 업데이트 (전체 지우고 다시 추가)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message =
                    "2004: 존재하지 않는 유저 <br>"),
    })
    public ResponseEntity<CommonResponse<Void>> addInterviewQuestions(
            @RequestBody List<InterviewQuestionDto> questions) {
        long memberId = jwtService.getMemberIdFromJwt();
        interviewService.addQuestions(memberId, questions);
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @GetMapping("/questions/random")
    @ApiOperation(value = "랜덤 질문 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message =
                    "2004: 존재하지 않는 유저 <br>"),
    })
    public ResponseEntity<CommonResponse<List<InterviewQuestionDto>>> getInterviewQuestions(
            @RequestParam boolean isCommonRandom,
            @RequestParam boolean isMemberRandom,
            @RequestParam int questionCount) {
        long memberId = jwtService.getMemberIdFromJwt();
        return ResponseEntity.ok(new CommonResponse<>(
                interviewService.getRandomQuestions(memberId, isCommonRandom, isMemberRandom, questionCount)
        ));
    }

    @PostMapping("/result/video")
    @ApiOperation(value = "(테스트용) 면접 결과 동영상 저장한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message =
                    "2004: 존재하지 않는 유저 <br>"
                            + "4001: 파일 변환 실패 <br>"),
    })
    public ResponseEntity<CommonResponse<Void>> postInterviewVideo(
            @RequestPart("video") MultipartFile video
    ) {
        long memberId = jwtService.getMemberIdFromJwt();
        String dirName = "jasome/users/" + memberId + "/interviews";
        s3UploaderService.upload(video, dirName);
        return ResponseEntity.ok(new CommonResponse<>());
    }
}
