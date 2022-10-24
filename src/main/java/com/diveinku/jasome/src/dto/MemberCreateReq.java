package com.diveinku.jasome.src.dto;

import com.diveinku.jasome.src.commons.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.diveinku.jasome.src.commons.ValidationMessage.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateReq {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = MEMBER_NAME_FORMAT)
    private String name;

    @NotBlank
    @Pattern(regexp = MEMBER_PASSWORD_FORMAT)
    private String password;

}
