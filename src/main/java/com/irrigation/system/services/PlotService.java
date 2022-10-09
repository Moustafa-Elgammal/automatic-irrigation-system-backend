package com.irrigation.system.services;

import com.irrigation.system.models.Plot;
import com.irrigation.system.repositories.PlotRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlotService implements PlotServiceInterface {

    @Autowired
    PlotRepositoryInterface plotRepository;

    /**
     * get all plots
     */
    @Override
    public List<Plot> getAll() {
        return plotRepository.findAll();
    }

    /**
     * create new plot record
     * @param plot
     * @return
     */
    @Override
    public Plot save(Plot plot) {
        plotRepository.save(plot);
        return plot;
    }

    /**
     *  update
     * @param id long
     * @param plot Plot
     * @return boolean
     */
    @Override
    public boolean update(long id, Plot plot) {
        if(!plotRepository.findById(id).isPresent())
            return false;

        plotRepository.save(plot);
        return true;
    }

    @Override
    public Plot plot(long id) throws Exception {

        // check existence
        if (!plotRepository.findById(id).isPresent()){
            throw new Exception("Not found"); // throw an exception with message
        }

        try {
            return plotRepository.findById(id).get();
        } catch (Exception exception)
        {
            throw new Exception("Not found"); // throw an exception with message
        }
    }

    /**
     * Delete Plot
     * @param id
     * @return
     */
    @Override
    public boolean delete(long id) {
        try {
            // get model
            Plot plot = this.plot(id);

            // delete
            plotRepository.delete(plot);

            return true;
        }catch (Exception ignored){
            // plot existence exception
            return  false;
        }
    }

    /**
     * get plots which need irrigation
     * @param now
     * @return
     */
    @Override
    public List<Plot> toBeIrrigate(LocalDateTime now) {
        return plotRepository.plotsBySlot(now);
    }

    /**
     * irrigate plot
     * and update time slot and other data
     * @param plot
     * @return
     */
    @Override
    public boolean irrigate(Plot plot) {

        // check sensor status
        if (!plot.isSensor_status()) { // sensor disabled

            // increment attempts calls
            plot.incrementRecallAttempts();

            //update
            update(plot.getId(), plot);

            return false;
        }

        // sensor enabled
        // crate time
        LocalDateTime time= LocalDateTime.now().plusDays(plot.getPeriod());

        //change time to be irrigated
        plot.setSlot(Timestamp.valueOf(time));

        // reset attempts
        plot.setRecalled_attempts(0);

        // return update status
        return update(plot.getId(), plot);
    }

}
