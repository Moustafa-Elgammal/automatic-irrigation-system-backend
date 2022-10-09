package com.irrigation.system.controllers;

import com.irrigation.system.models.Plot;
import com.irrigation.system.services.PlotServiceInterface;
import org.hibernate.annotations.SQLInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1")
public class PlotsController {

    @Autowired
    PlotServiceInterface plotService;

    /**
     * get all plots res
     */
    @GetMapping("plots")
    public ResponseEntity<List<Plot>> plots() {
        return new ResponseEntity<List<Plot>>(plotService.getAll(), HttpStatus.OK);
    }


    /**
     * create new plot record
     *
     * @param plot
     * @return
     */
    @PostMapping("plots")
    public HttpEntity<? extends Object> create(@Valid @RequestBody Plot plot, BindingResult bindingResult) {
        // validation check
        if (bindingResult.hasErrors()) return errorsResponse(bindingResult);

        return new ResponseEntity<Plot>(
                plotService.save(plot),
                HttpStatus.OK
        );
    }

    /**
     * update plot details
     *
     * @param plot
     * @param id
     * @return ResponseEntity
     */
    @PostMapping("plots/{id}")
    public HttpEntity<? extends Object> updatePlot(@Valid @RequestBody Plot plot, BindingResult bindingResult, @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) return errorsResponse(bindingResult);

        // check update status
        if (plotService.update(id, plot)) {
            try {
                return new ResponseEntity<Plot>(plotService.plot(id), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // failed to update res
        return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
    }

    /**
     * get plot details
     *
     * @return ResponseEntity
     */
    @GetMapping("plots/{id}")
    public HttpEntity<?> plot(@PathVariable("id") long id) {
        try { // handle existence exception
            Plot plot = plotService.plot(id);
            if (null != plot)
                return new ResponseEntity<Plot>(plot, HttpStatus.OK);

            return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * delete plot
     *
     * @param id
     * @return
     */
    @DeleteMapping("plots/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        // check delete
        if (plotService.delete(id))
            return new ResponseEntity<String>("deleted successfully", HttpStatus.OK);
        else
            return new ResponseEntity<String>("failed", HttpStatus.NOT_FOUND);
    }



    /**
     * errors message response
     * @param bindingResult
     * @return
     */
    private HttpEntity<ArrayList<String>> errorsResponse(BindingResult bindingResult) {

        // list
        ArrayList<String> errors = new ArrayList<>();

        //retrieve
        for (ObjectError e : bindingResult.getAllErrors()) {
            errors.add(e.getDefaultMessage());
        }

        //response
        return new ResponseEntity<ArrayList<String>>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

}
