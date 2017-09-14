/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lavidatec.template.vo;

import lombok.Data;

/**
 *
 * @author sian
 */
@Data
public class UsersVo {

    private String Account = null;
    private String Password = null;
    private String Identifier = null;
    private String OrderToken = null;
    private Integer optimisticLock = null;
}
