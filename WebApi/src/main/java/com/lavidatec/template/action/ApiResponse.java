/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lavidatec.template.action;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;
import org.json.JSONArray;
/**
 *
 * @author Mikey
 */
public class ApiResponse {
    
    private HttpStatus status;
    private String message;
    private JSONArray payload;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String debugMessage;
//    private List<ApiSubError> subErrors;

    private ApiResponse() {
        timestamp = LocalDateTime.now();
    }

    ApiResponse(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiResponse(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }
    
    ApiResponse(HttpStatus status, String message, JSONArray payload) {
        this();
        this.status = status;
        this.message = message;
        if(payload != null)
            this.payload = payload;
        else
            this.payload = new JSONArray();
    }
}
