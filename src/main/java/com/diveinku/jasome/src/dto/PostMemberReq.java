package com.diveinku.jasome.src.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostMemberReq {
    String email;
    String name;
    String password;
}
