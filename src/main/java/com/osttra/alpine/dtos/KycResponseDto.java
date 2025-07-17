package com.osttra.alpine.dtos;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KycResponseDto {

    private Long id;
    private String processInstanceId;
    private String taskId;
    private String legalName;
    private String leiNumber;
    private String email;
    private boolean isL1Approved;
    private String l1Comments;
    private String l1RejectionReason;
    private boolean isL2Approved;
    private String l2Comments;
    private String l2RejectionReason;
}
