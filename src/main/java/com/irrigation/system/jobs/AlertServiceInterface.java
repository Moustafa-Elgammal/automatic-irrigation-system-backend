package com.irrigation.system.jobs;

import com.irrigation.system.models.Plot;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Service;

public interface AlertServiceInterface {
    @Job(name = "send Alert")
    public void canBeAlerted(Plot plot);
}
