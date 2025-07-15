package com.osttra.alpine.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycResponseDto {
    String instanceId;
    String data;
    String error;
}
