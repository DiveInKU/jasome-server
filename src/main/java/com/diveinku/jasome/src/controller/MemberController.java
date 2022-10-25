package com.diveinku.jasome.src.controller;

import com.diveinku.jasome.src.commons.CommonResponse;
import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.dto.MemberCreateReq;
import com.diveinku.jasome.src.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/members")
@Validated // PathVariable과 RequestParam으로 들어오는 값 유효성 체크 위함
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/availability")
    @ApiOperation(value = "이메일 중복 체크. 사용 가능한 이메일일 때만 200 OK")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message = "2001: 중복된 이메일 <br> " +
                    "1111: 잘못된 이메일 형식"),
    })
    public ResponseEntity<CommonResponse<Void>> checkEmailAvailability(@RequestParam("email")
                                                                       @Email String email) {
        memberService.validateDuplicateEmail(email);
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @PostMapping("/new")
    @ApiOperation(value = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "1000: 요청 성공"),
            @ApiResponse(code = 400, message = "2001: 중복된 이메일 <br>" +
                    "1111: 잘못된 이메일 형식 <br>" +
                    "1112: 잘못된 비밀번호 형식(비밀번호는 6자 이상, 20자 이상의 영어와 숫자가 포함된 문자열) <br>" +
                    "1113: 잘못된 이름 형식(이름은 1자 이상, 20자 이하의 문자열)"),
    })
    public ResponseEntity<CommonResponse<Long>> create(@RequestBody @Valid MemberCreateReq memberCreateReq) {
        String encodedPassword = passwordEncoder.encode(memberCreateReq.getPassword());
        Member member = new Member(memberCreateReq.getEmail(), memberCreateReq.getName(), encodedPassword);
        Long id = memberService.join(member);
        return ResponseEntity.ok(CommonResponse.from(id));
    }

}
