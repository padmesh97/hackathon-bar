package org.barclays.errors;

import java.util.LinkedHashMap;

public class ErrorHandling {

    public static LinkedHashMap getError(ErrorCodes errCode){
        LinkedHashMap err = new LinkedHashMap();

        err.put("code",errCode.getErrorCode());
        err.put("message",errCode.getErrorMessage());

        return err;
    }
}
