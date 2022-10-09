package com.irrigation.system.controllers;

import com.irrigation.system.models.Plot;
import com.irrigation.system.services.PlotServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@CrossOrigin("*")
@RestController
public class HomeController {

    @Autowired
    PlotServiceInterface plotService;

    /**
     * get all plots res
     */
    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

}
