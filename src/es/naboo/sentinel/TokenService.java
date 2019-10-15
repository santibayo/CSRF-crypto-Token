package es.naboo.sentinel;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.UUID;

public class TokenService {
    private final static String HMAC_ALGORITHM = "HmacSHA256";

    private Token createToken(String secret){
        String uuid = UUID.randomUUID().toString();
        long time = getCalculatedTime();
        Token token = new Token(time,uuid);
        token.setSecret(secret);
        return token;
    }

    private long getCalculatedTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public final String generateToken(String secret, String signatureKey) throws  InvalidKeyException,NoSuchAlgorithmException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        Token token = createToken(secret);
        String tokenStr = createTokenStr(signatureKey, token);
        return tokenStr;
    }

    protected final String createTokenStr(final String signatureKey, final Token token) throws InvalidKeyException,NoSuchAlgorithmException {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        String message  = token.buildData();
        SecretKeySpec secretKeySpec = new SecretKeySpec(signatureKey.getBytes(),mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[]result =  mac.doFinal(message.getBytes());
        String signature = Base64.getEncoder().encodeToString(result);
        token.setSignature(signature);
        return Base64.getEncoder().encodeToString(token.toString().getBytes());
    }

    public final boolean verifyToken(final String token,final String secret,final String signatureKey,final int rangeMins)throws NoSuchAlgorithmException, InvalidKeyException,TokenException {
        //decoding
        if (token == null){
            return false;
        }

        if (token.length()>200){
            throw new TokenException("Token length is not expected");
        }

        String b64DecodedToken = new String(Base64.getDecoder().decode(token));
        String []tokenFields = b64DecodedToken.split("\\:");
        if (tokenFields.length!=3){
            throw new TokenException(String.format("_TokenException Fields are not correct"));
        }
        // verification
        if (!verifyContentData(tokenFields)) return false;
        /*
         * 0 = milisecs;
         * 1 = uuid;
         * 2 = signature;
         */
        Token verifyToken = new Token(Long.valueOf(tokenFields[0]),tokenFields[1]);
        verifyToken.setSecret(secret);
        if (!isInTime(verifyToken,rangeMins)){
            throw new TokenException(String.format("Token is outside valid range [ %d ] : %s",verifyToken.getMilisecs(),verifyToken.getRandomData()));
        }
        // Checks done!
        String tokenStringToVerifiy =  this.createTokenStr(signatureKey,verifyToken);
        if (token.equals(tokenStringToVerifiy)){
            return true;
        }
        return false;

    }

    private boolean verifyContentData(final String[] tokenFields) {
        boolean isNumber = tokenFields[0].matches("^[0-9]*$");
        boolean isUuid = tokenFields[1].matches("^[a-zA-Z0-9-]*$");
        boolean isBase64 = tokenFields[2].matches("^[a-zA-Z0-9\\+\\=\\/]*$");
        if (isNumber && isUuid && isBase64){
            return true;
        }
        return false;
    }

    private boolean isInTime(final Token token, int rangeMins){
        long generationTime = token.getMilisecs();
        long now = this.getCalculatedTime();

        long elapsedTime = now - generationTime;
        long rangeMilis = rangeMins * 60L * 1000L;
        if (elapsedTime > rangeMilis){
            return false;
        }
        return true;
    }





}
