
import com.lavidatec.template.dao.IUsersDao;
import com.lavidatec.template.dao.UsersDaoImpl;
import com.lavidatec.template.entity.TrainsModel;
import com.lavidatec.template.entity.UsersModel;
import com.lavidatec.template.vo.UsersVo;
import java.util.List;
import java.util.Optional;
import com.lavidatec.template.dao.ITrainsDao;
import com.lavidatec.template.dao.TrainsDaoImpl;
import com.lavidatec.template.service.TrainServiceImpl;
import com.lavidatec.template.vo.TrainsVo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Suhmian
 */
public class Test {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello! World!");
        IUsersDao iUsersDao = new UsersDaoImpl();
        ITrainsDao iTrainsDao = new TrainsDaoImpl();
//        UsersModel userModel = new UsersModel();
//        userModel.setAccount("Test for it.......");
//        userModel.setPassword("中文一下uuuudu");
////        iUsersDao.usersPersist(Optional.of(userModel));
//        userModel.setAccount("F");
//        iUsersDao.usersPersist(Optional.of(userModel));
//        UsersModel userModel2 = new UsersModel();
//        userModel2.setAccount("F");
//        iUsersDao.usersRemove(Optional.of(userModel2));
        TrainServiceImpl trainService = new TrainServiceImpl();
        TrainsModel trainModel = new TrainsModel();
        trainModel.setDate("2017/09/11");
        trainModel.setNo("111");
        trainModel.setType("普悠瑪");
        trainModel.setPrice("843");
        trainModel.setTicketsLimit(20);
        trainService.trainPersist(Optional.of(trainModel));
        TrainsModel trainModel2 = new TrainsModel();
        trainModel2.setDate("2017/09/12");
        trainModel2.setPrice("843");
        trainModel2.setNo("113");
        trainModel2.setType("自強");
        trainModel2.setTicketsLimit(20);
        trainService.trainPersist(Optional.of(trainModel2));
        Optional<List<TrainsModel>> optional;
        TrainsVo trainVo = new TrainsVo();
        trainVo.setNo("111");
        System.out.print(trainService.trainFindList(trainVo));
    }
}
