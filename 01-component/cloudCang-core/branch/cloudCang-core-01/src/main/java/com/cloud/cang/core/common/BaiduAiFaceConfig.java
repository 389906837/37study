package com.cloud.cang.core.common;

/**
 * @version 1.0
 * @ClassName BaiduAiFaceConfig
 * @Description 百度人脸识别参数配置
 * @Author zengzexiong
 * @Date 2018年7月26日11:25:05
 */
public class BaiduAiFaceConfig {
    /**
     * 人脸识别常量
     */
//    public static final String AI_APP_ID = "11540321";                              //百度应用ID
//    public static final String AI_API_KEY = "LfKlNi20K1D9GefyqsA886n4";             //百度应用key
//    public static final String AI_SECRET_KEY = "8tZChYFjw0TPPqmtTGYWZ4PS6UBBpRsS";  //百度应用密钥

    /**
     * 小屏人脸识别应用配置信息
     */
    public static final String AI_APP_ID = "11607702";                              //百度应用ID
    public static final String AI_API_KEY = "C2TphKcFRCdW3507bW8FZjHV";             //百度应用key
    public static final String AI_SECRET_KEY = "gvOb5jmNCTm18z8Lg8ERFD9lLw4fZtGz";  //百度应用密钥


    /**
     * 年会人脸识别应用配置信息
     */
    public static final String AM_APP_ID = "14757666";                              //百度应用ID
    public static final String AM_API_KEY = "sLd13DCGp0xkFcgF7uAySt2q";             //百度应用key
    public static final String AM_SECRET_KEY = "tcT0wGxYRprIzkZoYTDCHpPUCLEgSGYb";  //百度应用密钥

    public static final String GROUP_ID = "dev_37cang";      //用户组id（由数字、字母、下划线组成），长度限制128B

    /**
     * 图片信息(总数据大小应小于10M)，图片上传方式根据image_type来判断
     */
    public final static String IMAGE = "image";

    /**
     * 图片类型
     * BASE64:图片的base64值，base64编码后的图片数据，需urlencode，编码后的图片大小不超过2M；
     * URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
     * FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个
     */
    public final static String IMAGE_TYPE = "image_type";


    /**
     * 用户id（由数字、字母、下划线组成），长度限制128B
     */
    public final static String USER_ID = "user_id";

    //选填
    /**
     * 用户资料，长度限制256B
     */
    public final static String USER_INFO = "user_info";
    /**
     * 图片质量控制
     * NONE: 不进行控制 LOW:较低的质量要求
     * NORMAL: 一般的质量要求
     * HIGH: 较高的质量要求
     * 默认 NONE
     */
    public final static String QUALITY_CONTROL = "quality_control";

    /**
     * 活体检测控制
     * NONE: 不进行控制 LOW:较低的活体要求(高通过率 低攻击拒绝率)
     * NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率)
     * HIGH: 较高的活体要求(高攻击拒绝率 低通过率)
     * 默认NONE
     */
    public final static String LIVENESS_CONTROL = "liveness_control";
}
