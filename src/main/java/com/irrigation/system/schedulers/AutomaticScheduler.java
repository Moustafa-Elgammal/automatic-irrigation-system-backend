package com.irrigation.system.schedulers;

import com.irrigation.system.models.Plot;
import com.irrigation.system.jobs.AlertServiceInterface;
import com.irrigation.system.services.PlotServiceInterface;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
@EnableAsync
@ConditionalOnProperty(name="scheduler.enabled", matchIfMissing = true)
public class AutomaticScheduler {

    @Autowired
    PlotServiceInterface plotService;

    @Autowired JobScheduler jobScheduler;

    @Autowired
    AlertServiceInterface alert;

    // cron exp
    // second minute hour day month weekday
    // * : means every
    @Scheduled(cron = "0 */1 * * * *")
    public void run()
    {
        // current time
        LocalDateTime now = LocalDateTime.now();

        // get plots which need to be irrigated
        List<Plot> plots = plotService.toBeIrrigate(now);

        // irrigate each plot through job
        for(Plot plot : plots)
        {
            // enqueue job to irrigate a plot
            jobScheduler.enqueue(() -> irrigate(plot));
        }
    }

    /**
     * irrigation job method to be fired and forgotten in background process
     * @param plot
     */
    @Job(name = "Irrigate plot")
    public void irrigate(Plot plot){

        // process and check irrigation
        if(plotService.irrigate(plot)){
            System.out.println("successfully irrigated " + plot.getName() );
        }else{

            // failed irrigation acknowledge
            System.out.println("failed to irrigate " + plot.getName() );
            System.out.println("Recalled number " + plot.getRecalled_attempts());

            // sub job of Alert Service
            jobScheduler.enqueue(() -> alert.canBeAlerted(plot));
        }
    }
}
