package com.irrigation.system.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "plots")
@Setter
@Getter
public class Plot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotNull(message = "time is mandatory")
    private Timestamp slot;

    @NotNull(message = "amount is mandatory")
    private Double amount;

    @NotNull(message = "period is mandatory")
    private long period;

    private boolean sensor_status = true;

    @NotNull(message = "attempts is mandatory")
    private int attempts_to_recall;

    private int recalled_attempts;

    /**
     * increment plot recalls attempts
     */
    public void incrementRecallAttempts(){
        recalled_attempts++;
    }
}
