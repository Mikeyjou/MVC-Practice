/**
 * GetFitbitToken
 * Creater : Sian
 * get fitbit user access
 */
package com.lavidatec.template.GetControl;

import com.lavidatec.template.entity.UsersModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2017/03/16 get user data to upf.
 * 2017/02/24 get access token and refresh token.
 */
@EnableOAuth2Client
@Configuration
@RequestMapping(value = "/Fitbit")
public class GetFitbitToken {

    /**
     * slf4j.
     */
    static final Logger L = LoggerFactory.getLogger(GetFitbitToken.class);

    /**
     * slf4j.
     */
    private static final String LOG_PROP = "/var/log/jlogs";

    /**
     * 取得使用者Access token.
     *
     * @param userID User ID
     * @param account UPF account
     * @return Access_Token access token
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    @RequestMapping(value = "/Get_Access_Token", method = RequestMethod.GET)
    @ResponseBody
    public final String getAccessToken(
            @RequestParam(value = "UserID") final String userID,
            @RequestParam(value = "Account") final String account)
            throws ClassNotFoundException {

        L.info("GetToken");

        Optional<UsersModel> usersmodel = Optional.empty();
//        try {
//            UnitUser_Service upfus = new UnitUser_Service();
//            Users_Model users_Model = new Users_Model();
//            users_Model.setAccount(Account);
//            users_Model.setDevice_Account(UserID);
//            users_Model.setService_Type("Fitbit");
//            users_model = Optional.ofNullable(users_Model);
//            upfus.User_Update(users_model);
//        } catch (Exception ex) {
//            L.debug(ex.toString());
//        }
        String re = "";
        Connection con;
        ResultSet rs;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://61.216.164.199:3306/UnitDB",
                    "dba", "LaVidaDba-2017!");

            PreparedStatement preparedStmt = con.prepareStatement(
                    "update Unit_Users set UpdateTime ="
                            + " ? where Account = 'sianwu2011'");
            preparedStmt.setString(1, LocalDateTime.now().toString());
            preparedStmt.executeUpdate();

            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(
                    "select count(Account) AS T from Unit_Users");
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                re = rs.getInt("T") + "";
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("5" + ex.toString());
        }

        return re;
//        return users_model.get().getAccount();
    }

    /**
     * 取得使用者Access token.
     *
     * @param userID User ID
     * @param account UPF account
     * @return Access_Token access token
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    @RequestMapping(value = "/Get_Access", method = RequestMethod.GET)
    @ResponseBody
    public final String getAccess(
            @RequestParam(value = "UserID") final String userID,
            @RequestParam(value = "Account") final String account)
            throws ClassNotFoundException {

        L.info("GetTokenaAAA");

        String re = "";
        Connection con;
        ResultSet rs;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://61.216.164.199:3306/UnitDB",
                    "dba", "LaVidaDba-2017!");

            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(
                    "select count(Account) AS T from Unit_Users");
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                re = rs.getInt("T") + "";
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("5" + ex.toString());
        }

        return re;
//        return UserID + "testt" + Account;
    }
}
