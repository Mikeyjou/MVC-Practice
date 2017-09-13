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
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Miss some parameters")  // 400
    public class MissParametersException extends RuntimeException {}
    
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Username or password is wrong")  // 404
    public class UserNotFoundException extends RuntimeException {}
    
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="This username is duplicate")  // 400
    public class UserDuplicateException extends RuntimeException {}
    
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="This identifier is wrong")  // 400
    public class UserIdentifierNotFoundException extends RuntimeException {}
    
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="This order token is not find")  // 400
    public class UserOrderTokenNotFoundException extends RuntimeException {}
    
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such train")  // 404
    public class TrainNotFoundException extends RuntimeException {}
    
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="This train number is duplicate")  // 400
    public class TrainDuplicateException extends RuntimeException {}
    
    @ResponseStatus(value=HttpStatus.NO_CONTENT, reason="No tickets anymore")  // 204
    public class TrainTicketSoldOutException extends RuntimeException {}
}
