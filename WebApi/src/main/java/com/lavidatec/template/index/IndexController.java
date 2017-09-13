/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lavidatec.template.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author sian
 */
@Controller
@RequestMapping("/")
public class IndexController {

    /**
     * asd.
     * @param orderId sdf
     * @return sdf sdf
     * 
     */
    @RequestMapping(value = {"/index/{orderId}"}, method = RequestMethod.GET)
    public final String homePage(@PathVariable final String orderId) {
        System.out.println("com" + orderId);
        return "index";
    }

}
