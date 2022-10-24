package com.diveinku.jasome.src.controller;

import com.diveinku.jasome.src.commons.CommonResponse;
import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.dto.MemberCreateReq;
import com.diveinku.jasome.src.service.MemberService;
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
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/availability")
    public ResponseEntity<CommonResponse<Void>> checkEmailAvailability(@RequestParam("email")
                                                                       @Email String email){
        memberService.validateDuplicateEmail(email);
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @PostMapping("/new")
    public ResponseEntity<CommonResponse<Long>> create(@RequestBody @Valid MemberCreateReq memberCreateReq){
        String encodedPassword = passwordEncoder.encode(memberCreateReq.getPassword());
        Member member = new Member(memberCreateReq.getEmail(), memberCreateReq.getName(), encodedPassword);
        Long id = memberService.join(member);
        return ResponseEntity.ok(CommonResponse.from(id));
    }

}
