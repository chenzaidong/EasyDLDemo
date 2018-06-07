package com.cansmart.easydldemo;

public class HttpConfig {
    private String token;
    private static HttpConfig mHttpConfig;

    public  static HttpConfig getInstance() {
        return SingleInstance.INSTANCE;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private static class SingleInstance{
        private static final HttpConfig INSTANCE = new HttpConfig();
    }
}
