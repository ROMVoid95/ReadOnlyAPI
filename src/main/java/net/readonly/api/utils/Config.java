package net.readonly.api.utils;

public class Config {
    private int port;
    private String auth;
    private String userAgent;
    private boolean constantCheck;
    private int constantCheckDelay;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public boolean isConstantCheck() {
        return constantCheck;
    }

    public void setConstantCheck(boolean constantCheck) {
        this.constantCheck = constantCheck;
    }

    public int getConstantCheckDelay() {
        return constantCheckDelay;
    }

    public void setConstantCheckDelay(int constantCheckDelay) {
        this.constantCheckDelay = constantCheckDelay;
    }
}
