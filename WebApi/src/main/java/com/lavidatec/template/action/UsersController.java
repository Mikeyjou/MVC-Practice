/*
 * UsersAction
 * Author : Mikey-2017/09/10
 * 
 */
package com.lavidatec.template.action;


import com.lavidatec.template.entity.TrainsModel;
import com.lavidatec.template.entity.UsersModel;
import com.lavidatec.template.pojo.PasswordHash;
import com.lavidatec.template.service.ITrainService;
import com.lavidatec.template.service.IUserService;
import com.lavidatec.template.service.TrainServiceImpl;
import com.lavidatec.template.service.UserServiceImpl;
import com.lavidatec.template.vo.TrainsVo;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;


@Controller
@RequestMapping("/User")
public class UsersController {
    
    private IUserService userService = new UserServiceImpl();
    private ITrainService trainService = new TrainServiceImpl();
    
    private ApiError apiError = new ApiError();
    
    //取得HttpServletRequest的參數
    private Map<String,String> getParam(HttpServletRequest request) 
            throws IOException,
                   ArrayIndexOutOfBoundsException{        
        Map<String,String> result = new HashMap<>();
        String[] requestStr = IOUtils.toString(request.getReader()).split("&");
        for(String s: requestStr){
            String[] tmp = s.split("=");
            tmp[0] = URLDecoder.decode(tmp[0], "UTF-8");
            tmp[1] = URLDecoder.decode(tmp[1], "UTF-8");             
            result.put(tmp[0], tmp[1]);
        }
        return result;
    }
    
    //使用者註冊
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestParam("account") String account, 
                          @RequestParam("password") String pwd)
            throws Exception{
        if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(pwd)){
            UsersModel userModel = new UsersModel();
            userModel.setAccount(account);
            userModel.setPassword(pwd);
            //確認使用者不存在
            if(!userService.userFind(Optional.of(userModel)).isPresent())
                userService.userPersist(Optional.of(userModel));
            else{
                throw apiError.new UserDuplicateException();
            }
        }else{
            throw apiError.new MissParametersException();
        }
        return "";
    }
    
    //使用者帳號刪除
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(HttpServletRequest request)
            throws Exception{
        try{
            Map<String,String> paramMap = getParam(request);

            String account = paramMap.get("account");
            String pwd = paramMap.get("password");
            if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(pwd)){
                UsersModel userModel = new UsersModel();
                userModel.setAccount(account);
                userModel.setPassword(pwd);
                //確認使用者存在
                if(userService.userFind(Optional.of(userModel)).isPresent()){
                    userService.userRemove(Optional.of(userModel));    
                }else{
                   throw apiError.new UserNotFoundException();
                }
            }
            return "";  
        }catch(ArrayIndexOutOfBoundsException e){
            throw apiError.new MissParametersException();
        }
    }
    
    //使用者登入
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String loginUser(@RequestParam("account") String account, 
                           @RequestParam("password") String pwd)
            throws Exception{
        if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(pwd)){
            UsersModel userModel = new UsersModel();
            userModel.setAccount(account);
            userModel.setPassword(pwd);
            //確認使用者存在
            if(userService.userFind(Optional.of(userModel)).isPresent()){
                return userService.userLogin(Optional.of(userModel));
            }else{
                throw apiError.new UserNotFoundException();
            }
        }else{
            throw apiError.new MissParametersException();
        }
    }   
    
    //使用者登出
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logoutUser(@RequestParam("account") String account, 
                            @RequestParam("password") String pwd)
            throws Exception{
        if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(pwd)){
            UsersModel userModel = new UsersModel();
            userModel.setAccount(account);
            userModel.setPassword(pwd);
            //確認使用者存在
            if(userService.userFind(Optional.of(userModel)).isPresent()){
                userService.userLogout(Optional.of(userModel));
                return "";
            }else{
                throw apiError.new UserNotFoundException();
            }
        }else{
            throw apiError.new MissParametersException();
        }
    }
    
    //訂票
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ResponseBody
    public String bookTicket(@RequestParam("account") String account, 
                            @RequestParam("password") String pwd,
                            @RequestParam("identifier") String identifier,
                            @RequestParam("no") String no)
            throws Exception{
        if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(identifier) && StringUtils.isNotBlank(no)){
            //確認該車存在
            TrainsVo trainVo = new TrainsVo();
            trainVo.setNo(no);
            Optional<TrainsModel> train = trainService.trainFind(trainVo);
            if(!train.isPresent()){
                throw apiError.new TrainNotFoundException();
            }else{
                if(train.get().getTicketsLimit() <= 0)
                    throw apiError.new TrainTicketSoldOutException();
            }
            UsersModel userModel = new UsersModel();
            userModel.setAccount(account);
            userModel.setPassword(pwd);
            //確認使用者存在
            if(!userService.userFind(Optional.of(userModel)).isPresent()){
                throw apiError.new UserNotFoundException();
            }
            //確認使用者辨識碼正確
            userModel.setIdentifier(identifier);
            if(!userService.userFind(Optional.of(userModel)).isPresent()){
                throw apiError.new UserIdentifierNotFoundException();
            }
            
            return  userService.userBook(Optional.of(userModel), no);
        }else{
            throw apiError.new MissParametersException();
        }
    }
    
    //取消訂單
    @RequestMapping(value = "/order", method = RequestMethod.DELETE)
    @ResponseBody
    public String cancelOrder(HttpServletRequest request)
            throws Exception{
        try{
            Map<String,String> paramMap = getParam(request);

            String account = paramMap.get("account");
            String pwd = paramMap.get("password");
            String token = paramMap.get("token");
            if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(token)){
                UsersModel userModel = new UsersModel();
                userModel.setAccount(account);
                userModel.setPassword(pwd);
                //確認使用者存在
                Optional<UsersModel> user = userService.userFind(Optional.of(userModel));
                if(!user.isPresent()){
                    throw apiError.new UserNotFoundException();
                }
                if(!user.get().getOrderList().contains(token)){
                    throw apiError.new UserOrderTokenNotFoundException();
                }
                userService.userCancelOrder(Optional.of(userModel), token);
            }
            return "";
        }catch(ArrayIndexOutOfBoundsException e){
            throw apiError.new MissParametersException();
        }
    }
}