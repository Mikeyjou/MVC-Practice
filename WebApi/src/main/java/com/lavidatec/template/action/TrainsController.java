/*
 * UsersAction
 * Author : Mikey-2017/09/11
 * 
 */
package com.lavidatec.template.action;

import com.lavidatec.template.entity.TrainsModel;
import com.lavidatec.template.service.ITrainService;
import com.lavidatec.template.service.TrainServiceImpl;
import com.lavidatec.template.vo.TrainsVo;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Train")
public class TrainsController {
    
    private ITrainService trainService = new TrainServiceImpl();
    
    private ApiError apiError = new ApiError();
    
//   新增火車資訊
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String addTrain(@RequestParam("no") String no, 
                          @RequestParam("type") String type,
                          @RequestParam("date") String date,
                          @RequestParam("ticketsLimit") String ticketsLimit)
            throws Exception{
        if(!no.isEmpty() && !type.isEmpty() && !date.isEmpty() && !ticketsLimit.isEmpty()){
            TrainsModel trainModel = new TrainsModel();
            trainModel.setDate(date);
            trainModel.setNo(no);
            trainModel.setType(type);
            trainModel.setTicketsLimit(Integer.parseInt(ticketsLimit));
            trainService.trainPersist(Optional.of(trainModel));
            return "";
        }else{
            throw apiError.new MissParametersException();
        }
    }
    
//    依照條件搜尋火車資訊
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<TrainsModel>> searchTrain(@RequestParam("no") Optional<String> searchNo, 
                            @RequestParam("type") Optional<String> searchType,
                            @RequestParam("date") Optional<String> searchDate) 
            throws Exception{
        TrainsVo trainVo = new TrainsVo();
        if(searchNo.isPresent())
            trainVo.setNo(searchNo.get());
        if(searchType.isPresent())
            trainVo.setType(searchType.get());
        if(searchDate.isPresent())
            trainVo.setDate(searchDate.get());
        System.out.print(trainVo);
        Optional<List<TrainsModel>> trainFindList = trainService.trainFindList(trainVo);
        System.out.print(trainFindList.get());
//        return trainFindList.get();
        return new ResponseEntity<List<TrainsModel>>(trainFindList.get(), HttpStatus.OK);

    }
}
