package com.osttra.alpine.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KycDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
