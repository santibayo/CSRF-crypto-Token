package es.naboo.sentinel;

import java.io.StringWriter;

public class Token {
    private long milisecs;
    private String randomData;
    private String signature;
    private String secret;

    protected Token(long milisecs, String randomData) {
        this.milisecs = milisecs;
        this.randomData = randomData;
        this.signature = signature;
        this.secret = secret;
    }

    public long getMilisecs() {
        return milisecs;
    }

    public String getRandomData() {
        return randomData;
    }

    public String getSignature() {
        return signature;
    }

    public String getSecret() {
        return secret;
    }

    protected void setMilisecs(long milisecs) {
        this.milisecs = milisecs;
    }

    protected void setRandomData(String randomData) {
        this.randomData = randomData;
    }

    protected void setSignature(String signature) {
        this.signature = signature;
    }

    protected void setSecret(String secret) {
        this.secret = secret;
    }

    protected String buildData(){
        StringWriter stringWriter = new StringWriter();
        stringWriter.append(Long.toString(this.milisecs));
        stringWriter.append(this.randomData);
        stringWriter.append(this.secret);
        return stringWriter.toString();
    }


    public String toString(){
        StringWriter stringWriter = new StringWriter();
        stringWriter.append(Long.toString(this.milisecs));
        stringWriter.append(":");
        stringWriter.append(this.randomData);
        stringWriter.append(":");
        stringWriter.append(this.signature);
        return stringWriter.toString();
    }
}
