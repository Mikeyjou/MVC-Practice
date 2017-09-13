/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lavidatec.template.action;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Mikey
 */
public class ApiError {
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such user")  // 404
    public class UserNotFoundException extends RuntimeException {}
    
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Miss some parameters")  // 400
    public class MissParametersException extends RuntimeException {}
    
}
