package com.irrigation.system.repositories;

import com.irrigation.system.models.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlotRepositoryInterface extends JpaRepository<Plot, Long> {

    /**
     *  get plots which need irrigation
     * @param slot
     * @return
     */
    @Query(value = "SELECT * FROM plots where slot <= ?", nativeQuery = true)
    List<Plot> plotsBySlot(LocalDateTime slot);
}
