package com.osttra.alpine.dtos;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KycRequestDto {
    private String legalName;
    private String leiNumber;
    private String email;
}
