package com.diveinku.jasome.src.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.diveinku.jasome.src.commons.ValidationMessage.EMPTY_FIELD;
import static com.diveinku.jasome.src.commons.ValidationMessage.INVALID_EMAIL;

@NoArgsConstructor @AllArgsConstructor
@Getter
public class MemberEmailAvailabilityReq {
    @NotBlank(message = EMPTY_FIELD)
    @Email(message = INVALID_EMAIL)
    String email;
}
