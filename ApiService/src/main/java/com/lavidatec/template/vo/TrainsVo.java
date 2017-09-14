/*
 * TicketsVo
 * Author : Mikey-2017/09/08
 * 
 */
package com.lavidatec.template.vo;

import lombok.Data;

@Data
public class TrainsVo {
    private String No = null;
    private String Type = null;
    private String Date = null;
    private Integer optimisticLock = null;
}
