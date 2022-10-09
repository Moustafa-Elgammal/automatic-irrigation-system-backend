package com.irrigation.system.services;

import com.irrigation.system.models.Plot;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface PlotServiceInterface {
    public List <Plot> getAll();

    public Plot save(Plot plot);
    public boolean update(long id, Plot plot);

    public Plot plot(long id) throws Exception;

    public boolean delete(long id);

    public List <Plot> toBeIrrigate(LocalDateTime now);

    boolean irrigate(Plot plot);
}
