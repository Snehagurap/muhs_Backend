package in.cdac.university.globalService.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class IdleConnectionCheckScheduler {

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

    @Autowired
    JdbcTemplate jdbcTemplate;

    //@Scheduled(cron = "0 */5 * * * *")
    public void restartSequenceEveryMonth() {
        log.info("Checking for idle connection " + new Date());
        jdbcTemplate.execute("WITH inactive_connections AS (" +
                "    SELECT pid, rank() over (partition by client_addr order by backend_start ASC) as rank" +
                "    FROM pg_stat_activity" +
                "    WHERE pid <> pg_backend_pid( )" +
                "    AND application_name !~ '(?:psql)|(?:pgAdmin.+)'" +
                "    AND datname = current_database() " +
                "    AND usename = current_user " +
                "    AND state in ('idle', 'idle in transaction', 'idle in transaction (aborted)', 'disabled') " +
                ")" +
                "SELECT pg_terminate_backend(pid)" +
                "FROM inactive_connections " +
                "WHERE rank > 1");
    }
}
