/*
 * TrainsDaoImplements
 * Author : Mikey-2017/09/08
 * 
 */
package com.lavidatec.template.dao;

import com.lavidatec.template.comment.JPAUtil;
import com.lavidatec.template.entity.TrainsModel;
import com.lavidatec.template.vo.TrainsVo;
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

@Lazy
@Repository("ITrainsDao")
public class TrainsDaoImpl implements ITrainsDao{
    /**
     * log init.
     */
    static final Logger LOGGER
            = LoggerFactory.getLogger(TrainsDaoImpl.class);
    
    @Override
    public final int trainsPersist(
            final Optional<TrainsModel> trainsOptional)
            throws Exception {
        LOGGER.info("Start trainsPersist");

        //回傳結果預設值
        int result = 0;
        //建立連線物件
        EntityManager em
                = JPAUtil.getEntityManagerFactory().createEntityManager();

        //建立資料交換物件
        EntityTransaction et = null;
        try {

            //傳入物件是否為空
            if (trainsOptional.isPresent()) {
                //轉換物件資訊
                TrainsModel trainsModel = trainsOptional.orElse(new TrainsModel());
                et = em.getTransaction();
                et.begin();
                //寫入物件
                LOGGER.debug("Start persist");
                trainsModel.setUpdateTime(LocalDateTime.now());
                em.persist(trainsModel);
                LOGGER.info("Train Here" + trainsModel);
                et.commit();

                if (null != trainsModel.getPKey()) {
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

        LOGGER.info("End trainsPersist");
        return result;
    }

    @Override
    public final int trainsMerge(
            final Optional<TrainsModel> trainsOptional)
            throws Exception {
        LOGGER.info("Start trainsMerge");

        //回傳結果預設值
        int result = 0;
        //建立連線物件
        EntityManager em
                = JPAUtil.getEntityManagerFactory().createEntityManager();

        //建立資料交換物件
        EntityTransaction et = null;
        try {

            //傳入物件是否為空
            if (trainsOptional.isPresent()) {
                //轉換物件資訊
                TrainsModel trainsModel = trainsOptional.orElse(new TrainsModel());
                et = em.getTransaction();
                et.begin();
                //更新物件
                LOGGER.debug("Start merge");
                em.merge(trainsModel);
                et.commit();

                if (null != trainsModel.getPKey()) {
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

        LOGGER.info("End trainsMerge");
        return result;
    }

    @Override
    public final int trainsRemove(
            final Optional<TrainsModel> trainsOptional)
            throws Exception {
        LOGGER.info("Start trainsRemove");

        //回傳結果預設值
        int result = 0;
        //建立連線物件
        EntityManager em
                = JPAUtil.getEntityManagerFactory().createEntityManager();

        //建立資料交換物件
        EntityTransaction et = null;
        try {

            //傳入物件是否為空
            if (trainsOptional.isPresent()) {
                //轉換物件資訊
                TrainsModel trainsModel = trainsOptional.orElse(new TrainsModel());
                //設定邏輯刪除
//                usersModel.setLogicalDelete(1);
                et = em.getTransaction();
                et.begin();
                //更新物件
                LOGGER.debug("Start remove");
                em.remove(trainsModel);
                et.commit();

                if (null != trainsModel.getPKey()) {
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

        LOGGER.info("End trainsRemove");
        return result;
    }

    @Override
    public final Optional<TrainsModel> trainsFind(
            final TrainsVo trainsVo)
            throws Exception {
        LOGGER.info("Start trainsFind");

        //建立連線物件
        EntityManager em
                = JPAUtil.getEntityManagerFactory().createEntityManager();

        //建立搜尋字串
        StringBuilder sbSql = new StringBuilder();
        //建立回傳物件
        Optional<TrainsModel> trainsOptional
                = Optional.empty();
        try {

            TrainsModel trainsModel;

            sbSql.append("From TrainsModel");
            sbSql.append(" Where 1=1");

            // 加入查詢參數
            List<String> criList = new ArrayList<>();
            Map<String, Object> paramMap = new HashMap<>();
            //判斷是否有加入條件
            if (StringUtils.isNotBlank(trainsVo.getNo())) {
                criList.add("no = :no");
                paramMap.put("no", trainsVo.getNo());
            }
            if (StringUtils.isNotBlank(trainsVo.getType())) {
                criList.add("type = :type");
                paramMap.put("type", trainsVo.getType());
            }
            if (StringUtils.isNotBlank(trainsVo.getDate())) {
                criList.add("date = :date");
                paramMap.put("date", trainsVo.getDate());
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
            Query query = em.createQuery(
                    sbSql.toString(), TrainsModel.class);

            //設定參數
            paramMap.keySet().forEach((parameterName) -> {
                query.setParameter(parameterName, paramMap.get(parameterName));
            });

            LOGGER.debug("Start Find");
            trainsModel
                    = (TrainsModel) query.getResultList().get(0);
            //取出查詢資訊
            trainsOptional
                    = Optional.ofNullable(trainsModel);
        } catch (Exception e) {
            LOGGER.error("Find error:" + e);
        } finally {
            //關閉連線
            em.close();
        }

        LOGGER.info("End trainsFind");
        return trainsOptional;
    }

    @Override
    public final Optional<List<TrainsModel>> trainsFindList(
            final TrainsVo trainsVo)
            throws Exception {
        LOGGER.info("Start trainsFind");

        //建立連線物件
        EntityManager em
                = JPAUtil.getEntityManagerFactory().createEntityManager();

        //建立搜尋字串
        StringBuilder sbSql = new StringBuilder();
        //建立回傳物件
        Optional<List<TrainsModel>> trainsListOptional
                = Optional.empty();
        try {

            sbSql.append("From TrainsModel");
            sbSql.append(" Where 1=1");

            // 加入查詢參數
            List<String> criList = new ArrayList<>();
            Map<String, Object> paramMap = new HashMap<>();
            //判斷是否有加入條件
            if (StringUtils.isNotBlank(trainsVo.getNo())) {
                criList.add("no = :no");
                paramMap.put("no", trainsVo.getNo());
            }
            if (StringUtils.isNotBlank(trainsVo.getType())) {
                criList.add("type = :type");
                paramMap.put("type", trainsVo.getType());
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
            TypedQuery<TrainsModel> query = em.createQuery(
                    sbSql.toString(), TrainsModel.class);

            //設定參數
            paramMap.keySet().forEach((parameterName) -> {
                query.setParameter(parameterName, paramMap.get(parameterName));
            });

            List<TrainsModel> trainsModel = new ArrayList<>();
            //取出查詢資訊
            LOGGER.debug("Start Find");
            trainsModel
                    = query.getResultList();

            trainsListOptional
                    = Optional.ofNullable(trainsModel);
        } catch (Exception e) {
            LOGGER.error("Find error:" + e);
        } finally {
            //關閉連線
            em.close();
        }

        LOGGER.info("End trainsFind");
        return trainsListOptional;
    }
}
