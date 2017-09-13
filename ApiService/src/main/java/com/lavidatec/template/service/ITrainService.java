/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lavidatec.template.service;

import com.lavidatec.template.entity.TrainsModel;
import com.lavidatec.template.vo.TrainsVo;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Mikey
 */
public interface ITrainService {
    void trainPersist(final Optional<TrainsModel> trains)
            throws Exception;
    
    void trainMerge(final Optional<TrainsModel> trains)
            throws Exception;
    
    Optional<TrainsModel> trainFind(final TrainsVo trainsVo)
            throws Exception;
    
    Optional<List<TrainsModel>> trainFindList(final TrainsVo trainsVo)
            throws Exception;
    
    void trainRemove(final Optional<TrainsModel> trains)
            throws Exception;
}
