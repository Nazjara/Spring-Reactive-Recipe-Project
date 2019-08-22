package com.nazjara.controller;

import com.nazjara.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.thymeleaf.exceptions.TemplateInputException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNumberFormatException(Exception exception, Model model) {
        log.error(exception.getMessage());

        model.addAttribute("status", "400 Bad Request");
        model.addAttribute("exception", exception);

        return "error";
    }

    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(Exception exception, Model model) {
        log.error(exception.getMessage());

        model.addAttribute("status", "404 Not Found");
        model.addAttribute("exception", exception);

        return "error";
    }
}