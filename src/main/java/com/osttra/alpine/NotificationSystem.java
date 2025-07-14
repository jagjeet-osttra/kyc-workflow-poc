package com.osttra.alpine;

import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Named
@Slf4j
public class NotificationSystem implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(NotificationSystem.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String rejectionReason;

        rejectionReason = (String) delegateExecution.getVariable("RejectionReason");
        log.info("Your KYC has not been approved!!");
        log.info("Reason specified: {}",rejectionReason);
        //TODO Notification logic
    }
}
