package com.nazjara.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

//    @ExceptionHandler(NumberFormatException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ModelAndView handleNumberFormatException(Exception exception) {
//        log.error(exception.getMessage());
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        modelAndView.addObject("status", "400 Bad Request");
//        modelAndView.addObject("exception", exception);
//
//        return modelAndView;
//    }
}