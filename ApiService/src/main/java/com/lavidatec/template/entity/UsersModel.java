/**
 * User_Profile_Model
 * Author : Sian
 * Users_Model DB Mapping Object
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

/**
 * .
 * 2017/03/15 - Add Users_Model
 */
@Data
@Entity
@Table(name = "Unit_Users")
public class UsersModel implements Serializable {

    /**
     * .
     *
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @GeneratedValue(generator = "nativeGenerator")
//  @GenericGenerator(name = "nativeGenerator", strategy = "sequence")
    @Column(name = "p_key", unique = true, nullable = false,
            precision = 20, scale = 0)
    private Long pKey;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "Account", unique = true)
    private String account;
    @Column(name = "Password")
    private String password;
    @Column(name = "OrderList")
    private String orderList;
    @Column(name = "Identifier")
    private String identifier;
    @Column(name = "UpdateTime")
    private LocalDateTime updateTime;
}
