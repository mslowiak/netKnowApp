package netKnow;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by MQ on 2017-04-26.
 */
public class PasswordEncrypter {
    
    public static boolean isPasswordMatching(String enteredPassword, String dbPassword){
        enteredPassword = encryptPassword(enteredPassword);
        return enteredPassword.equals(dbPassword);
    }

    public static String encryptPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte byteData[]  = md.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0; i<byteData.length; i++){
                stringBuffer.append(Integer.toString((byteData[i] & 0xff) +  0x100, 16).substring(1));
            }
            password = stringBuffer.toString();

        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

}
