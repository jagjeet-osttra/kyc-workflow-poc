package com.osttra.alpine.repositories;

import com.osttra.alpine.entities.KycDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KycDetailsRepository extends JpaRepository<KycDetails,Long> {

    Optional<KycDetails> findByProcessInstanceId(String processInstanceId);
    Optional<KycDetails> findByTaskId(String taskId);
}
