/*
 * UsersAction
 * Author : Mikey-2017/09/10
 * 
 */
package com.lavidatec.template.action;


import com.lavidatec.template.entity.UsersModel;
import com.lavidatec.template.pojo.PasswordHash;
import com.lavidatec.template.service.IUserService;
import com.lavidatec.template.service.UserServiceImpl;
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
    
    private ApiError apiError = new ApiError();
    
    //取得HttpServletRequest的參數
    private Map<String,String> getParam(HttpServletRequest request) throws IOException{        
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
            userService.userPersist(Optional.of(userModel));
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
        Map<String,String> paramMap = getParam(request);
        
        String account = paramMap.get("account");
        String pwd = paramMap.get("password");
       
        if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(pwd)){
            UsersModel userModel = new UsersModel();
            userModel.setAccount(account);
            userModel.setPassword(pwd);
            userService.userRemove(Optional.of(userModel));
            return "";
        }else{
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
            String token = userService.userLogin(Optional.of(userModel));
            return token;
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
            userService.userLogout(Optional.of(userModel));
            return "";
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
            UsersModel userModel = new UsersModel();
            userModel.setAccount(account);
            userModel.setPassword(pwd);
            userModel.setIdentifier(identifier);
            String token = userService.userBook(Optional.of(userModel), no);
            return token;
        }else{
            throw apiError.new MissParametersException();
        }
    }
    
    //取消訂單
    @RequestMapping(value = "/order", method = RequestMethod.DELETE)
    @ResponseBody
    public String cancelOrder(HttpServletRequest request)
            throws Exception{
        Map<String,String> paramMap = getParam(request);
        
        String account = paramMap.get("account");
        String pwd = paramMap.get("password");
        String token = paramMap.get("token");
        if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(token)){
            UsersModel userModel = new UsersModel();
            userModel.setAccount(account);
            userModel.setPassword(pwd);
            userService.userCancelOrder(Optional.of(userModel), token);
            return "";
        }else{
            throw apiError.new MissParametersException();
        }
    }
}