package com.cloudcreativity.peoplepass.utils;

/**
 * 整个程序的网络接口配置
 */
public interface APIService {
    /**
     * 网络请求的配置
     */
    long timeOut =10;//网络超时
    /**
     * 整体的接口配置
     */
//    String TEST_HOST = "http://192.168.31.229/";
    String TEST_HOST = "http://service.milidianshang.cn/";
    String ONLINE_HOST = "http://service.milidianshang.cn/";
    String HOST = AppConfig.DEBUG ? TEST_HOST : ONLINE_HOST;
    String HOST_APP = AppConfig.DEBUG ? TEST_HOST+"vmall/" : ONLINE_HOST+"vmall/";
}
