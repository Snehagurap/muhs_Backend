package in.cdac.university.globalService.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SequenceRestartScheduler {

    //  ┌───────────── second (0-59)
    //	│ ┌───────────── minute (0 - 59)
    //	│ │ ┌───────────── hour (0 - 23)
    //	│ │ │ ┌───────────── day of the month (1 - 31)
    //	│ │ │ │ ┌───────────── month (1 - 12) (or JAN-DEC)
    //	│ │ │ │ │ ┌───────────── day of the week (0 - 7)
    //	│ │ │ │ │ │          (or MON-SUN -- 0 or 7 is Sunday)
    //	│ │ │ │ │ │
    //	* * * * * *

    // * "0 0 * * * *" = the top of every hour of every day.
    // * "*/10 * * * * *" = every ten seconds.
    // * "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
    // * "0 0 8,10 * * *" = 8 and 10 o'clock of every day.
    // * "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
    // * "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
    // * "0 0 0 25 12 ?" = every Christmas Day at midnight
    //
    // (*) means match any
    //
    // */X means "every X"
    //
    // ? ("no specific value") - useful when you need to specify something in one of the two fields in which the character is allowed,
    // but not the other. For example, if I want my trigger to fire on a particular day of the month (say, the 10th),
    // but I don't care what day of the week that happens to be, I would put "10" in the day-of-month field and "?" in the day-of-week field.

    final List<String> sequenceList = List.of(
            // Committee
            "ucom.seq_gblt_committee_mst",
            "ucom.seq_gblt_event_mst",
            "ucom.seq_gblt_committee_member_dtl",
            "ucom.seq_gblt_lic_committee_ruleset_mst",
            "ucom.seq_gblt_lic_committee_mst",
            "ucom.seq_gblt_lic_committee_dtl",
            // University
            "university.seq_gmst_subject_mst",
            "university.seq_gmst_college_mst",
            "university.seq_gmst_course_mst",
            "university.seq_gmst_config_template_item_mst",
            "university.seq_cmst_college_faculty_mst",
            "university.seq_cmst_college_course_mst",
            "university.seq_gmst_config_template_item_mst",
            "university.seq_gmst_emp_mst",
            "university.seq_gmst_applicant_mst",
            "university.seq_gmst_config_mastertemplate_mst",
            "university.seq_gmst_config_template_component_mst",
            "university.seq_gmst_faculty_stream_dtl",
            "university.seq_gmst_config_template_item_api_mst",
            "university.seq_gmst_course_pattern_dtl",

            // Planning Board
            "upb.seq_gblt_notification_master",

            // Template Data
            "templedata.seq_gblt_config_application_data_mst"
    );

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0 0 1 * *")
    public void restartSequenceEveryMonth() {
        log.info("Resetting sequence on " + new Date());
        for (String sequenceName : sequenceList) {
            log.info("Sequence Name {}", sequenceName);
            jdbcTemplate.execute("ALTER SEQUENCE if exists " + sequenceName + " RESTART;");
        }
        log.info("Resetting Done on " + new Date());
    }
}
