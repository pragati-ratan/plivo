package com.plivo.contactBook.controllers;

import com.plivo.contactBook.dto.ResponseDTO;
import com.plivo.contactBook.utils.Constants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO validationErrorHandler(MethodArgumentNotValidException e) {
        List<String> invalidParams = e.getBindingResult().getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList());
        ResponseDTO res = new ResponseDTO();
        res.setStatus(Constants.StatusCodes.BAD_REQUEST);
        res.setMessage(Constants.ErrorMessage.INVALID_PARAMS + " : " + String.join(", ", invalidParams));
        return res;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO validationErrorHandler(DataIntegrityViolationException e) {
        ResponseDTO res = new ResponseDTO();
        res.setStatus(Constants.StatusCodes.BAD_REQUEST);
        res.setMessage(Constants.ErrorMessage.INVALID_PARAMS);
        return res;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO validationErrorHandler(MethodArgumentTypeMismatchException e) {
        List<String> invalidParams = Arrays.asList(e.getName());
        ResponseDTO res = new ResponseDTO();
        res.setStatus(Constants.StatusCodes.BAD_REQUEST);
        res.setMessage(Constants.ErrorMessage.INVALID_PARAMS + " : " + String.join(", ", invalidParams));
        return res;
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseDTO responseStatusErrorHandler(ResponseStatusException e, HttpServletResponse response) {
        ResponseDTO res = new ResponseDTO();
        res.setStatus(e.getStatus().value());
        res.setMessage(e.getReason());
        response.setStatus(e.getStatus().value());
        return res;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseDTO ioErrorHandler(Exception e) {
        ResponseDTO res = new ResponseDTO();
        res.setStatus(Constants.StatusCodes.INTERNAL_SERVER_ERROR);
        return res;
    }

}
