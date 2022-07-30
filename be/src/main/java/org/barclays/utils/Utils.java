package org.barclays.utils;

import org.barclays.errors.ErrorCodes;
import org.barclays.errors.ErrorHandling;
import org.barclays.model.User;
import org.barclays.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Optional;

public class Utils {

    public static boolean checkAuthenticated(HttpServletRequest httpReq){
        if(httpReq.getSession().getAttribute("role") != null && httpReq.getSession().getAttribute("user-id") != null)
            return true;

        return false;
    }

    public static LinkedHashMap generateInvalidLoginError(HttpServletResponse httpResp){
        LinkedHashMap resp = new LinkedHashMap();

        resp.put("error", ErrorHandling.getError(ErrorCodes.UNKNOWN_ACCESS));
        httpResp.setStatus(401);

        return resp;
    }

    public static User getLoggedInUser(HttpServletRequest httpReq,UserRepository userRepository){
        if(httpReq.getSession().getAttribute("user-id") != null){
            Long userId = Long.parseLong(httpReq.getSession().getAttribute("user-id").toString());
            Optional<User> fetch = userRepository.findById(userId);
            User usr = fetch.get();
            return usr;
        }

        return null;
    }

}
