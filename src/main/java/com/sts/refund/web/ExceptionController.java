package com.sts.refund.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * API 에러 처리
 */
@ControllerAdvice
@Slf4j
public class ExceptionController {


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex)  {
        log.info("[ExceptionController] class={}, err={}", ex.getClass().getName(), ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}