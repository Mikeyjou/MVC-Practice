/*
 * TrainsModel
 * Author : Mikey-2017/09/08
 * TrainsModel DB Mapping Object
 */
package com.lavidatec.template.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "Unit_Trains")
public class TrainsModel implements Serializable{    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @GeneratedValue(generator = "nativeGenerator")
//  @GenericGenerator(name = "nativeGenerator", strategy = "sequence")
    @Column(name = "p_key", unique = true, nullable = false,
            precision = 20, scale = 0)
    private Long pKey;
//  @Id
    @Column(name = "No", unique = true)
    private String no;
    @Column(name = "Type")
    private String type;
    @Column(name = "Price")
    private String price;
    @Column(name = "Date")
    private String date;
    @Column(name = "StationFrom")
    private String stationFrom;
    @Column(name = "StationTo")
    private String stationTo;
    @Column(name = "TicketsLimit")
    private int ticketsLimit;
    @Column(name = "UpdateTime")
    private LocalDateTime updateTime;
}
