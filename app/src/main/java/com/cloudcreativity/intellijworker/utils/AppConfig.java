package com.cloudcreativity.intellijworker.utils;

/**
 * 这个app的属性配置
 */
public class AppConfig {
    /**
     * 是否是开发调试阶段
     */
    public static boolean DEBUG = true;
    /**
     * 网络数据缓存的文件夹名称
     */
    public static final String CACHE_FILE_NAME = "app_cache";
    /**
     * 网络图片缓存的文件夹名称
     */
    public static final String CACHE_IMAGE_NAME = "app_image_cache";
    /**
     * 这是SharePreference的名称
     */
    public static final String SP_NAME = "intellij_worker_config";

    /**
     * 这是统一的文件名
     */
    public static String FILE_NAME = "intellij_worker_image_%d.%s";

    /**
     * 这是APP热更新的下载缓存文件名
     */
    static String APP_HOT_UPDATE_FILE = "intellij_worker_hot_update.apk";

}
