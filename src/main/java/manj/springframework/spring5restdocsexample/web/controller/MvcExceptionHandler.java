package manj.springframework.spring5restdocsexample.web.controller;

import java.util.ArrayList;
import java.util.List;


import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MvcExceptionHandler {
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List> validationErrorHandler(ConstraintViolationException ex){
		
		List<String> errorsList = new ArrayList<>(ex.getConstraintViolations().size());
		ex.getConstraintViolations().forEach(error -> errorsList.add(error.toString()));
		
		//I think we can use below line instead
		//ex.getConstraintViolations().stream().map(error -> error.toString()).collect(Collectors.toList());

        return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
		
	}

}
