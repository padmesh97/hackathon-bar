package org.barclays.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordManager {

    public static String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String plainPassword,String hashedPassword){
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
