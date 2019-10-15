package es.naboo.sentinel;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class Tests {


    public static final String SECRET = "Adm!n091.";
    public static final String SIGNATURE_KEY = "F!rX0)1$3zPlS%m,1Jk7ma";

    public static void main (String []args)throws Exception{
        testNotNull();
        testOk();
        testNotOverflow();
    }

    private static void testOk() throws InvalidKeyException, NoSuchAlgorithmException, TokenException {
        TokenService tokenService = new TokenService();
        String token = tokenService.generateToken(SECRET, SIGNATURE_KEY);
        boolean verify = tokenService.verifyToken(token,SECRET,SIGNATURE_KEY,10);
        Logger.getLogger("tests").info(String.format("status: %s",Boolean.valueOf(verify).toString()));
        Logger.getLogger("tests").info(String.format("token: %s",token));

    }

    private static void testNotNull()throws InvalidKeyException, NoSuchAlgorithmException, TokenException {
        TokenService tokenService = new TokenService();
        boolean verify = tokenService.verifyToken(null,SECRET,SIGNATURE_KEY,10);
        Logger.getLogger("tests").info(String.format("status: %s",Boolean.valueOf(verify).toString()));
    }

    private static void testNotOverflow()throws InvalidKeyException, NoSuchAlgorithmException, TokenException {
        TokenService tokenService = new TokenService();
        String token = "klijsjfpo8weumr09t82cur23948cu6230489-5um203f4895y23uk4jh5l2jk34h5l2j34g52jlhk345k2hj3v45k2ghv345kjg234v5jk2g3v45jk2gh3v45j2hgv345j2hgv345jh2g3v45j2hg3v45j2h3g4v5j2hg43v5j2hg43v52jhg4v52jh4g3v52jh4gv52j34hgv52jh3g45c2jh34gc52jh34gc52jhg34c52j3hg4c5j23gh4c52jhg34c52jh34gc52j34ghc52j34hc5234c52j3h4gc52j3f45c2hjgf34d5h23gfd452hgf34d5";
        try{
            boolean verify = tokenService.verifyToken(token,SECRET,SIGNATURE_KEY,10);
        }catch(TokenException e) {
            Logger.getLogger("tests").info(String.format("status: %s", e.getMessage()));
        }
    }




    private static void testTime() throws InvalidKeyException, NoSuchAlgorithmException, TokenException {
        TokenService tokenService = new TokenService();
        String token = tokenService.generateToken(SECRET, SIGNATURE_KEY);
        System.out.println(token);
        boolean verify = tokenService.verifyToken(token,SECRET,SIGNATURE_KEY,10);
        System.out.println(verify);
    }
}
