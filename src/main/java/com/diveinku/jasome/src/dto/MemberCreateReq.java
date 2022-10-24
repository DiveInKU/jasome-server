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
@ToString
public class MemberCreateReq {
    @NotBlank(message = EMPTY_FIELD)
    @Email(message = INVALID_EMAIL)
    private String email;

    @NotBlank(message = EMPTY_FIELD)
    @Pattern(regexp = MEMBER_NAME_FORMAT, message = INVALID_NAME)
    private String name;

    @NotBlank(message = EMPTY_FIELD)
    @Pattern(regexp = MEMBER_PASSWORD_FORMAT, message = INVALID_PASSWORD)
    private String password;

}
