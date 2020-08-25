package com.md.recipe.recipe.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException( Exception exception) {
        log.error("Exception occur", exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/404error");
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNumberFormatException( Exception exception) {
        log.error("Exception occur", exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/bad_request");
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }
}
