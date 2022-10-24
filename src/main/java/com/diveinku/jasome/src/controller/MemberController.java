package com.diveinku.jasome.src.controller;

import com.diveinku.jasome.src.commons.CommonResponse;
import com.diveinku.jasome.src.domain.Member;
import com.diveinku.jasome.src.dto.MemberCreateReq;
import com.diveinku.jasome.src.dto.MemberEmailAvailabilityReq;
import com.diveinku.jasome.src.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/availability")
    public ResponseEntity<CommonResponse<Void>> checkEmailAvailability(@RequestParam("email") @Valid
                                                       MemberEmailAvailabilityReq memberEmailAvailabilityReq){
        memberService.validateDuplicateEmail(memberEmailAvailabilityReq.getEmail());
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @PostMapping("/new")
    public ResponseEntity<CommonResponse<Long>> create(@RequestBody @Valid MemberCreateReq memberCreateReq){
        System.out.println("memberCreateReq = " + memberCreateReq);
        String encodedPassword = passwordEncoder.encode(memberCreateReq.getPassword());
        Member member = new Member(memberCreateReq.getEmail(), memberCreateReq.getName(), encodedPassword);
        Long id = memberService.join(member);
        return ResponseEntity.ok(CommonResponse.from(id));
    }

}
