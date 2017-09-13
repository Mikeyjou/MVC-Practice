/**
 * IUsersDao
 * Author : Sian
 * Put Users Data to Database method
 */
package com.lavidatec.template.dao;

import com.lavidatec.template.entity.UsersModel;
import com.lavidatec.template.vo.UsersVo;
import java.util.List;
import java.util.Optional;

/**
 * 2017/03/15 - Add UsersPersist.
 */
public interface IUsersDao {

    /**
     * Users 資料新增.
     *
     * @param usersOptional Users 物件
     * @return status 0 or1
     * @throws java.lang.Exception Exception
     */
    int usersPersist(Optional<UsersModel> usersOptional)
            throws Exception;

    /**
     * Users 資料更新.
     *
     * @param usersOptional Users 物件
     * @return status 0 or1
     * @throws java.lang.Exception Exception
     */
    int usersMerge(Optional<UsersModel> usersOptional)
            throws Exception;

    /**
     * Users 資料移除.
     *
     * @param usersOptional Users 物件
     * @return status 0 or1
     * @throws java.lang.Exception Exception
     */
    int usersRemove(Optional<UsersModel> usersOptional)
            throws Exception;

    /**
     * Users 資料搜尋.
     *
     * @param usersVo usersVo
     * @return Optional UsersModel
     * @throws java.lang.Exception Exception
     */
    Optional<UsersModel> usersFind(
            UsersVo usersVo)
            throws Exception;

    /**
     * Users 資料搜尋.
     *
     * @param usersVo usersVo
     * @return Optional UsersModel
     * @throws java.lang.Exception Exception
     */
    Optional<List<UsersModel>> usersFindList(
            UsersVo usersVo)
            throws Exception;
}
