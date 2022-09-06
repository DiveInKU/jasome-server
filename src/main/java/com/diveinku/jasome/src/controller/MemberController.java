package com.diveinku.jasome.src.controller;

import com.diveinku.jasome.src.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/duplication")
    public ResponseEntity<Void> getEmailDuplicateOrNot(
            @RequestParam(value = "email") String email) {
        memberService.assertEmailNotExists(email);
        return ResponseEntity.ok().build();
    }


    // @PostMapping("/")
    // public ResponseEntity<Void> registerUser(@RequestBody PostMemberReq postMemberReq){
    //     String encodedPassword = passwordEncoder.encode();
    // }
}
