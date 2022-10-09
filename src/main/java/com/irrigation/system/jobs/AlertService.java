package com.irrigation.system.jobs;

import com.irrigation.system.models.Plot;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AlertService implements AlertServiceInterface {

    // config datasource for job
    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {
        InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
        storageProvider.setJobMapper(jobMapper);
        return storageProvider;
    }

    /**
     *  sent Alert if tries proceeded
     *  defined as background job
     * @param plot
     */
    @Override
    @Job(name = "send Alert")
    public void canBeAlerted(Plot plot) {
        // check attempts time
        if (plot.getRecalled_attempts() >= plot.getAttempts_to_recall())
        {
            // Alert system integration
            System.out.printf("send alert for %s attempts %d", plot.getName(), plot.getRecalled_attempts() );
            System.out.println();
        }
    }
}
