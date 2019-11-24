package com.tpj.teamproject.controller;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.tpj.teamproject.common.Url.ECLASS;
import static com.tpj.teamproject.common.Url.ECLASS_RELAYSTATE;
import static com.tpj.teamproject.common.Url.ECLASS_RSP;
import static com.tpj.teamproject.common.Url.LOGIN_URL;
import static com.tpj.teamproject.common.Url.USER_AGENT;

public class ParseLogin {
    protected String userID;
    protected String userPW;

    boolean isValid;

    public static Map<String, String> loginCookie = null;
    private static ParseLogin login = null;

    protected ParseLogin(String id, String pw) {
        this.userID = id;
        this.userPW = pw;
        isValid = loginCheck(userID,userPW,ECLASS_RSP,ECLASS_RELAYSTATE);
    }

    public static ParseLogin getInstance(String id, String pw){
        if(login==null){
            login = new ParseLogin(id,pw);
        }
        return login;
    }

    protected Map<String, String> getData(String userID,String userPW, String RSP, String RelayState){
        Map<String, String> data = new HashMap<>();
        data.put("userID",userID);
        data.put("userPW",userPW);
        data.put("RelayState",RelayState);
        data.put("RSP",RSP);
        return data;
    }


    protected Connection.Response getResponse(Connection.Method method, Map<String, String> data, String url){
        try {
            return Jsoup.connect(url)
                    .data(data)
                    .userAgent(USER_AGENT)
                    .followRedirects(true)
                    .method(method)
                    .execute();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static Connection.Response getResponse(Map<String, String> cookies, String url){
        try {
            return Jsoup.connect(url)
                    .header("Connection","keep-alive")
                    .userAgent(USER_AGENT)
                    .cookies(cookies)
                    .followRedirects(true)
                    .timeout(25000)
                    .maxBodySize(0)
                    .execute();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean loginCheck(String userID, String userPW, String RSP, String RelayState){
        String redirectUrl = "https://sso.mju.ac.kr/SSO/ssoLinkService?RSP="+RSP+"&RelayState="+RelayState;
        Map<String, String> data = getData(userID,userPW,RSP,RelayState);
        Connection.Response loginPage =getResponse(Connection.Method.POST,data, LOGIN_URL);
        loginCookie = getResponse(loginPage.cookies(),redirectUrl).cookies();
        try {
            return !loginPage.parse().outerHtml().contains("SSO39");
        } catch (IOException e) {
            return false;
        }
    }

    public String getName(){
        try {
            return getResponse(loginCookie,ECLASS)
                    .parse()
                    .select("#introBody > h2 > a")
                    .text();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean loginCheck(){
        return isValid;
    }
}