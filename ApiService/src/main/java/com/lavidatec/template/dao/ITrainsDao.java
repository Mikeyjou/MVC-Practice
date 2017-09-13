/*
 * TrainsDao interface
 * Author : Mikey-2017/09/08
 * 
 */
package com.lavidatec.template.dao;

import com.lavidatec.template.entity.TrainsModel;
import com.lavidatec.template.entity.UsersModel;
import com.lavidatec.template.vo.TrainsVo;
import com.lavidatec.template.vo.UsersVo;
import java.util.List;
import java.util.Optional;

public interface ITrainsDao {
    /**
     * Trians 資料新增.
     *
     */
    int trainsPersist(Optional<TrainsModel> trainsOptional)
            throws Exception;

    /**
     * Trians 資料更新.
     *
     */
    int trainsMerge(Optional<TrainsModel> trainsOptional)
            throws Exception;

    /**
     * Trians 資料移除.
     *
     */
    int trainsRemove(Optional<TrainsModel> trainsOptional)
            throws Exception;

    /**
     * Trians 資料搜尋.
     *
     */
    Optional<TrainsModel> trainsFind(
            TrainsVo usersVo)
            throws Exception;

    /**
     * Trians 資料搜尋.
     *
     */
    Optional<List<TrainsModel>> trainsFindList(
            TrainsVo usersVo)
            throws Exception;
}
