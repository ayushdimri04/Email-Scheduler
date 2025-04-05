package com.example.email_scheduler.web;

import com.example.email_scheduler.payload.EmailRequest;
import com.example.email_scheduler.payload.EmailResponse;
import com.example.email_scheduler.quartz.job.EmailJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/email-scheduler")
public class EmailSchedulerController {

    @Autowired
    private Scheduler scheduler;

    private static final Logger logger = LoggerFactory.getLogger(EmailSchedulerController.class);

    @GetMapping("/get")
    public ResponseEntity<String> getApiTest(){
        return ResponseEntity.ok("Get API Test Pass");
    }

    @PostMapping(value = "/schedule-email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailResponse> scheduleEmail(@RequestBody EmailRequest emailRequest) {
        try {
            // Convert the provided datetime and timezone into a ZonedDateTime object
            ZonedDateTime dateTime = ZonedDateTime.of(emailRequest.getDateTime(), emailRequest.getTimeZone());

            // Check if the requested date-time is in the past
            if (dateTime.isBefore(ZonedDateTime.now())) {
                EmailResponse emailResponse = new EmailResponse(false, "Date-time must be in the future");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailResponse);
            }

            // Build job detail and trigger based on the email request
            JobDetail jobDetail = buildJobDetail(emailRequest);
            Trigger trigger = buildTrigger(jobDetail, dateTime);

            // Schedule the job
            scheduler.scheduleJob(jobDetail, trigger);

            // Respond with the success message
            EmailResponse emailResponse = new EmailResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Email scheduled successfully");
            return ResponseEntity.ok(emailResponse);

        } catch (Exception e) {
            // Log the error properly using a logger
            logger.error("Error scheduling email", e); // Assuming you have a logger initialized

            // Respond with a server-side error message
            EmailResponse emailResponse = new EmailResponse(false, "Server Side Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
        }
    }

    private JobDetail buildJobDetail(EmailRequest scheduleEmailRequest){
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email",scheduleEmailRequest.getEmail());
        jobDataMap.put("subject",scheduleEmailRequest.getSubject());
        jobDataMap.put("body",scheduleEmailRequest.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(),"email-jobs")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();

    }

    private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(),"email-trigger")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

}
