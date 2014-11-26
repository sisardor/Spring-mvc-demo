package com.blackiceinc.web.exceptions.contollers;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.blackiceinc.exceptions.CustomerNotRegisteredException;

@Controller
public class ExceptionHandlingController {
	// Specify the name of a specific view that will be used to display the error:
	  @ExceptionHandler({SQLException.class,DataAccessException.class, CustomerNotRegisteredException.class})
	  public String databaseError() {
	    // Nothing to do.  Returns the logical view name of an error page, passed to
	    // the view-resolver(s) in usual way.
	    // Note that the exception is _not_ available to this view (it is not added to
	    // the model) but see "Extending ExceptionHandlerExceptionResolver" below.
		  
		  System.out.println("\n\nERRRRRROR\n");
	    return "databaseError";
	  }
}
