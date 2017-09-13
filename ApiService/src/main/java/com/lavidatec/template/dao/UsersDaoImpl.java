/**
 * UsersDaoImpl
 * Author : Sian
 * Put Users Data to Database method
 */
package com.lavidatec.template.dao;

import com.lavidatec.template.comment.JPAUtil;
import com.lavidatec.template.entity.UsersModel;
import com.lavidatec.template.service.IUserService;
import com.lavidatec.template.service.UserServiceImpl;
import com.lavidatec.template.vo.UsersVo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 * 2017/03/10 - Add UsersPersist.
 */
@Lazy
@Repository("IUsersDao")
public class UsersDaoImpl implements IUsersDao {

    /**
     * log init.
     */
    static final Logger LOGGER
            = LoggerFactory.getLogger(UsersDaoImpl.class);
    
    @Override
    public final int usersPersist(
            final Optional<UsersModel> usersOptional)
            throws Exception {
        LOGGER.info("Start usersPersist");

        //回傳結果預設值
        int result = 0;
        //建立連線物件
        EntityManager em
                = JPAUtil.getEntityManagerFactory().createEntityManager();

        //建立資料交換物件
        EntityTransaction et = null;
        try {

            //傳入物件是否為空
            if (usersOptional.isPresent()) {
                //轉換物件資訊
                UsersModel usersModel = usersOptional.orElse(new UsersModel());
                et = em.getTransaction();
                et.begin();
                //寫入物件
                LOGGER.debug("Start persist");
                usersModel.setUpdateTime(LocalDateTime.now());
                em.persist(usersModel);
                et.commit();

                if (null != usersModel.getPKey()) {
                    result = 1;
                }
            }
        } catch (Exception e) {

            LOGGER.error("Persist error:" + e);
            //Persist error rollback
            if (et != null && et.isActive()) {
                et.rollback();
            }
        } finally {
            //關閉連線
            em.close();
        }

        LOGGER.info("End usersPersist");
        return result;
    }

    @Override
    public final int usersMerge(
            final Optional<UsersModel> usersOptional)
            throws Exception {
        LOGGER.info("Start usersMerge");

        //回傳結果預設值
        int result = 0;
        //建立連線物件
        EntityManager em
                = JPAUtil.getEntityManagerFactory().createEntityManager();

        //建立資料交換物件
        EntityTransaction et = null;
        try {

            //傳入物件是否為空
            if (usersOptional.isPresent()) {
                //轉換物件資訊
                UsersModel usersModel = usersOptional.orElse(new UsersModel());
                et = em.getTransaction();
                et.begin();
                //更新物件
                LOGGER.debug("Start merge");
                em.merge(usersModel);
                et.commit();

                if (null != usersModel.getPKey()) {
                    result = 1;
                }
            }
        } catch (Exception e) {

            LOGGER.error("Merge error:" + e);
            //Merge error rollback
            if (et != null && et.isActive()) {
                et.rollback();
            }
        } finally {
            //關閉連線
            em.close();
        }

        LOGGER.info("End usersMerge");
        return result;
    }

    @Override
    public final int usersRemove(
            final Optional<UsersModel> usersOptional)
            throws Exception {
        LOGGER.info("Start usersRemove");

        //回傳結果預設值
        int result = 0;
        //建立連線物件
        EntityManager em
                = JPAUtil.getEntityManagerFactory().createEntityManager();

        //建立資料交換物件
        EntityTransaction et = null;
        try {

            //傳入物件是否為空
            if (usersOptional.isPresent()) {
                //轉換物件資訊
                UsersModel usersModel = usersOptional.orElse(new UsersModel());
                //設定邏輯刪除
//                usersModel.setLogicalDelete(1);
                et = em.getTransaction();
                et.begin();
                //更新物件
                LOGGER.debug("Start remove");
//                em.remove(usersModel);
                em.remove(em.contains(usersModel) ? usersModel : em.merge(usersModel));
                et.commit();

                if (null != usersModel.getPKey()) {
                    result = 1;
                }
            }
        } catch (Exception e) {

            LOGGER.error("Remove error:" + e);
            //Merge error rollback
            if (et != null && et.isActive()) {
                et.rollback();
            }
        } finally {
            //關閉連線
            em.close();
        }

        LOGGER.info("End usersRemove");
        return result;
    }

    @Override
    public final Optional<UsersModel> usersFind(
            final UsersVo usersVo)
            throws Exception {
        LOGGER.info("Start usersFind");

        //建立連線物件
        EntityManager em
                = JPAUtil.getEntityManagerFactory().createEntityManager();

        //建立搜尋字串
        StringBuilder sbSql = new StringBuilder();
        //建立回傳物件
        Optional<UsersModel> usersOptional
                = Optional.empty();
        try {

            UsersModel usersModel;

            sbSql.append("From UsersModel");
            sbSql.append(" Where 1=1");

            // 加入查詢參數
            List<String> criList = new ArrayList<>();
            Map<String, Object> paramMap = new HashMap<>();
            //判斷是否有加入條件
            if (StringUtils.isNotBlank(usersVo.getAccount())) {
                criList.add("account = :account");
                paramMap.put("account", usersVo.getAccount());
            }
            if (StringUtils.isNotBlank(usersVo.getPassword())) {
                criList.add("password = :password");
                paramMap.put("password", usersVo.getPassword());
            }
            if (StringUtils.isNotBlank(usersVo.getIdentifier())) {
                criList.add("identifier = :identifier");
                paramMap.put("identifier", usersVo.getIdentifier());
            }
            if (StringUtils.isNotBlank(usersVo.getOrderToken())) {
                LOGGER.info(usersVo.getOrderToken());
                criList.add("orderList LIKE '%" + usersVo.getOrderToken() + "%'");
            }
            if (!criList.isEmpty()) {
                // 使用者輸入查詢條件
                criList.forEach((cri) -> {
                    sbSql.append(" AND ").append(cri).append(" ");
                });
            }

            sbSql.append(" Order By updateTime Desc");

            LOGGER.info("SQL : " + sbSql);
            //建立查詢物件
            Query query = em.createQuery(
                    sbSql.toString(), UsersModel.class);

            //設定參數
            paramMap.keySet().forEach((parameterName) -> {
                query.setParameter(parameterName, paramMap.get(parameterName));
            });

            LOGGER.debug("Start Find");
            usersModel
                    = (UsersModel) query.getResultList().get(0);
            //取出查詢資訊
            usersOptional
                    = Optional.ofNullable(usersModel);
        } catch (Exception e) {
            LOGGER.error("Find error:" + e);
        } finally {
            //關閉連線
            em.close();
        }

        LOGGER.info("End usersFind");
        return usersOptional;
    }

    @Override
    public final Optional<List<UsersModel>> usersFindList(
            final UsersVo usersVo)
            throws Exception {
        LOGGER.info("Start usersFind");

        //建立連線物件
        EntityManager em
                = JPAUtil.getEntityManagerFactory().createEntityManager();

        //建立搜尋字串
        StringBuilder sbSql = new StringBuilder();
        //建立回傳物件
        Optional<List<UsersModel>> usersListOptional
                = Optional.empty();
        try {

            sbSql.append("From UsersModel");
            sbSql.append(" Where 1=1");

            // 加入查詢參數
            List<String> criList = new ArrayList<>();
            Map<String, Object> paramMap = new HashMap<>();
            //判斷是否有加入條件
            if (StringUtils.isNotBlank(usersVo.getAccount())) {
                criList.add("account = :account");
                paramMap.put("account", usersVo.getAccount());
            }
            if (StringUtils.isNotBlank(usersVo.getPassword())) {
                criList.add("password = :password");
                paramMap.put("password", usersVo.getPassword());
            }
            if (StringUtils.isNotBlank(usersVo.getIdentifier())) {
                criList.add("identifier = :identifier");
                paramMap.put("identifier", usersVo.getIdentifier());
            }

            if (!criList.isEmpty()) {
                // 使用者輸入查詢條件
                criList.forEach((cri) -> {
                    sbSql.append(" AND ").append(cri).append(" ");
                });
            }

            sbSql.append(" Order By updateTime Desc");

            LOGGER.debug("SQL : " + sbSql);
            //建立查詢物件
            TypedQuery<UsersModel> query = em.createQuery(
                    sbSql.toString(), UsersModel.class);

            //設定參數
            paramMap.keySet().forEach((parameterName) -> {
                query.setParameter(parameterName, paramMap.get(parameterName));
            });

            List<UsersModel> usersModels = new ArrayList<>();
            //取出查詢資訊
            LOGGER.debug("Start Find");
            usersModels
                    = query.getResultList();

            usersListOptional
                    = Optional.ofNullable(usersModels);
        } catch (Exception e) {
            LOGGER.error("Find error:" + e);
        } finally {
            //關閉連線
            em.close();
        }

        LOGGER.info("End usersFind");
        return usersListOptional;
    }
    
    public static void main(String[] args) throws Exception {
        UsersModel userModel = new UsersModel();
        IUsersDao iUsersDao = new UsersDaoImpl();
        IUserService iUserService = new UserServiceImpl();
        String token = "00ae35ba63d04b7cb91e24ebe8f0a2bb,xxx";
        userModel.setAccount("mikey");
        userModel.setOrderList(token);
        iUsersDao.usersPersist(Optional.of(userModel));
        UsersVo usersVo = new UsersVo();
        usersVo.setOrderToken("00ae35ba63d04b7cb91e24ebe8f0a2bb");
        System.out.print("234234" + iUsersDao.usersFind(usersVo) + "123123");
        iUserService.userCancelOrder(Optional.of(userModel), "00ae35ba63d04b7cb91e24ebe8f0a2bb");
    }
}