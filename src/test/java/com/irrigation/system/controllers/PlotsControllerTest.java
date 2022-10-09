package com.irrigation.system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irrigation.system.models.Plot;
import com.irrigation.system.services.PlotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PlotsController.class)
class PlotsControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PlotService mockService;

    /**
     * get plots list
     *
     * @throws Exception
     */
    @Test
    void getPlotsDetailsTest() throws Exception {

        Plot p = new Plot();
        p.setId(1);
        p.setName("test");
        p.setAmount(1.2);
        p.setPeriod(1);

        Plot _p = new Plot();
        _p.setId(3);
        _p.setName("test");
        _p.setAmount(1.2);
        _p.setPeriod(1);

        ArrayList<Plot> plots = new ArrayList<Plot>();

        plots.add(p);
        plots.add(_p);

        when(mockService.getAll()).thenReturn(plots);

        String expected = new ObjectMapper().writeValueAsString(plots);

        mvc.perform(get("/api/v1/plots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

    }

    /**
     * get existed plot details
     *
     * @throws Exception
     */
    @Test
    void getExistedPlotTest() throws Exception {

        Plot p = new Plot();
        p.setId(1);
        p.setName("test");
        p.setAmount(1.2);
        p.setPeriod(1);

        when(mockService.plot(1)).thenReturn(p);

        String expected = new ObjectMapper().writeValueAsString(p);

        mvc.perform(get("/api/v1/plots/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

    }

    /**
     * get not existed plot details
     *
     * @throws Exception
     */
    @Test
    void getNotExistedPlotTest() throws Exception {

        when(mockService.plot(0)).thenReturn(null);

        mvc.perform(get("/api/v1/plots/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteExistedPlotTest() throws Exception {
        when(mockService.delete(1)).thenReturn(true);

        mvc.perform(delete("/api/v1/plots/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteNotExistedPlotTest() throws Exception {
        when(mockService.delete(1)).thenReturn(false);

        mvc.perform(delete("/api/v1/plots/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * create plot test
     * @throws Exception
     */
    @Test
    void createPlot() throws Exception {

        Plot p = new Plot();
        p.setId(1);
        p.setName("test");
        p.setAmount(1.2);
        p.setPeriod(1);

        String expected = new ObjectMapper().writeValueAsString(p);

        mvc.perform(post("/api/v1/plots", p).contentType(MediaType.APPLICATION_JSON).content(expected))
                .andDo(print())
                .andExpect(status().isOk());
    }
}