package org.barclays.controller;

import org.barclays.errors.ErrorCodes;
import org.barclays.errors.ErrorHandling;
import org.barclays.model.User;
import org.barclays.repository.UserRepository;
import org.barclays.utils.PasswordManager;
import org.barclays.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    public UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Object loginAction(@RequestBody LinkedHashMap reqBody,HttpServletRequest httpReq, HttpServletResponse httpResp){
        LinkedHashMap resp = new LinkedHashMap();

        if(!reqBody.containsKey("email") || !reqBody.containsKey("password")){
            resp.put("error", ErrorHandling.getError(ErrorCodes.LOGIN_MISSING_DETAILS));
            httpResp.setStatus(500);
            return resp;
        }

        if(Boolean.parseBoolean(checkAuthentication(httpReq).get("isAuthenticated").toString())){
            resp.put("error", ErrorHandling.getError(ErrorCodes.LOGIN_ALREADY_AVAILABLE));
            return resp;
        }

        String email = reqBody.get("email").toString();
        String password = reqBody.get("password").toString();

        try{
            User user = userRepository.findByEmail(email);
            if(user == null){
                resp.put("error",ErrorHandling.getError(ErrorCodes.LOGIN_NOT_FOUND));
                return resp;
            }
            else{
                if(PasswordManager.verifyPassword(password,user.getPassword())){
                    httpReq.getSession().setAttribute("role",user.getType());
                    httpReq.getSession().setAttribute("user-id",user.getId());

                    resp.put("status","success");
                }
                else{
                    resp.put("error", ErrorHandling.getError(ErrorCodes.LOGIN_INVALID));
                    httpResp.setStatus(401);
                    return resp;
                }
            }
        }
        catch(Exception e){
            resp.put("error", ErrorHandling.getError(ErrorCodes.UNKNOWN_ERROR));
            httpResp.setStatus(500);
            return resp;
        }

        return resp;
    }

    @RequestMapping(method = RequestMethod.GET, value="/check")
    public LinkedHashMap checkAuthentication(HttpServletRequest httpReq) {

        LinkedHashMap resp = new LinkedHashMap();
        if(Utils.checkAuthenticated(httpReq)){
            resp.put("status","success");
            resp.put("isAuthenticated","true");
            User loggedInUser = Utils.getLoggedInUser(httpReq,userRepository);
            LinkedHashMap userDetails = new LinkedHashMap();
            userDetails.put("name",loggedInUser.getName());
            userDetails.put("email",loggedInUser.getEmail());
            resp.put("user",userDetails);
        }
        else{
            resp.put("status","success");
            resp.put("isAuthenticated","false");
        }

        return resp;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Object logoutAction(HttpServletRequest httpReq) {
        LinkedHashMap resp = new LinkedHashMap();

        httpReq.getSession().invalidate();
        resp.put("status","success");

        return resp;
    }

}
