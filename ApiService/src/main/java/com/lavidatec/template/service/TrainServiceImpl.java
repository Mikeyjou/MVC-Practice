/**
 * Train_Service
 * Author : Mikey
 *
 */
package com.lavidatec.template.service;


import com.lavidatec.template.dao.ITrainsDao;
import com.lavidatec.template.dao.TrainsDaoImpl;
import com.lavidatec.template.entity.TrainsModel;
import static com.lavidatec.template.service.UserServiceImpl.LOGGER;
import com.lavidatec.template.vo.TrainsVo;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Lazy
@Repository("ITrainService")
public class TrainServiceImpl implements ITrainService{
    
    /**
     * log init.
     */
    static final Logger LOGGER
            = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Override
     public final void trainPersist(final Optional<TrainsModel> trains)
            throws Exception {
        LOGGER.info("trainPersistStart");
        ITrainsDao iTrainsDao = new TrainsDaoImpl();
        iTrainsDao.trainsPersist(trains);
        LOGGER.info("trainPersistEnd");
    }
    
     @Override
    public final void trainMerge(final Optional<TrainsModel> trains)
            throws Exception {
        LOGGER.info("trainMergeStart");
        ITrainsDao iTrainsDao = new TrainsDaoImpl();
        iTrainsDao.trainsMerge(trains);
        LOGGER.info("trainMergeEnd");
    }
    
    //尋找火車
    @Override
    public final Optional<TrainsModel> trainFind(final TrainsVo trainsVo)
            throws Exception {
        LOGGER.info("trainFindStart");
        ITrainsDao iTrainsDao = new TrainsDaoImpl();
        Optional<TrainsModel> result = iTrainsDao.trainsFind(trainsVo);
        LOGGER.info("trainFindEnd");
        return result;
    }
    
    //尋找多筆火車
    @Override
    public final Optional<List<TrainsModel>> trainFindList(final TrainsVo trainsVo)
            throws Exception {
        LOGGER.info("trainFindListStart");
        ITrainsDao iTrainsDao = new TrainsDaoImpl();
        Optional<List<TrainsModel>> result = iTrainsDao.trainsFindList(trainsVo);
        LOGGER.info("trainFindListEnd");
        return result;
    }
    
    //移除火車
    @Override
    public final void trainRemove(final Optional<TrainsModel> trains)
            throws Exception {
        LOGGER.info("trainRemoveStart");
        ITrainsDao iTrainsDao = new TrainsDaoImpl();
        iTrainsDao.trainsRemove(trains);
        LOGGER.info("trainRemoveEnd");
    }
}
