package com.cloudcreativity.intellijworker.utils;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
    String TEST_HOST = "http://192.168.31.19:8081/";
    String ONLINE_HOST = "http://service.milidianshang.cn/";
    String HOST = AppConfig.DEBUG ? TEST_HOST : ONLINE_HOST;
    String HOST_APP = AppConfig.DEBUG ? TEST_HOST : ONLINE_HOST;


    @POST("worker/login")
    @FormUrlEncoded
    Observable<String> login(@Field("idCardNum") String idCardNum,
                             @Field("password") String password);

    @POST("worker/edit")
    @FormUrlEncoded
    Observable<String> editPwd(@Field("password") String oldPwd,
                               @Field("newPassword") String newPwd);


    @POST("worker/getPageListOfPro")
    @FormUrlEncoded
    Observable<String> getProList(@Field("pageNum") int pageNum);


    @POST("worker/getCardInfoTimeOfWorker")
    @FormUrlEncoded
    /*
     * @param cardTime  年月 2019-02
     */
    Observable<String> getCardByMonth(@Field("pId") int pid,
                                      @Field("wId") int wId,
                                      @Field("cardTime") String cardTime);

    @POST("worker/getCardInfoByDate")
    @FormUrlEncoded
    /**
     * @param cardTime  年月日 2019-02-02
     */
    Observable<String> getCardByDay(@Field("pId") int pid,
                                    @Field("wId") int wId,
                                    @Field("cardTime") String cardTime);

    @POST("worker/getPageListOfSalary")
    @FormUrlEncoded
    Observable<String> getSalary(@Field("pId") int pid,
                                 @Field("wId") int wid,
                                 @Field("pageNum") int pageNum);
}
