package com.cloud.cang.open.api.common;


public final class APIConstant {

    public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKnvuLiGM3FtHwYDNJwSk/dFj3kIL5P1jyVC76mj2jmafSYC++w8ZbtcpFlGW4QAzp5Gy9dyigghfOEHfBK/uco/BBL7N4oRj2799hGOX4XmaVt7DueTEImvY5FN5HvXnYeCpcO78MDqY9inFZ7qjbflynK8IfP+I1KujpDgBvYbAgMBAAECgYAR9YGiDpy1KgETU5dlvxjgEvvcoJ7WlibwmyhU1zoiyE7di4cwKhneOSYtQSA+zQ8I7xZvyG0J+vAFoBLesGBj8FSKhycPFTW0gw27dtNwPNn06lyANclVgrjgR+QOgBpJ62DOCwSd2YWCsQ6RHGH55/pHFSmNBpm1/5GyhTUO4QJBAP80pTNym+c89+GaKBzG0xGSJsga6wyHUrLHMpDBH7IgMF4gdjUOSqyZS4cwfb+u3rF2e9YkOuRnYVKCn0DtgokCQQCqdyGmS/s9cH0k/8hz7b8EqZpoPLT0xieI1Ly+A0MndNaZvOpTtF/8K4g5gBGbtnmfNz4bte+A1NVLf7S/kVqDAkEAiwWtvMIBRc2Dp0Un9s0kq9y2/akURCXvme5DkHF0B7/bOVtrqWlYqn9zkniK+AGV1Iyb10KzRjs+3AjGIkOAKQJAWh+8CajYPuZvM5WMDEahHDzzJrMZ1OPC2SPoWeltormyD/wCQ5j0umTlk8jzBklWSegyDQKGiq17y3vMOxAFMwJARyKS2DKW9wwuoOItKHyVY5vSY1WjPvgFCC2MuSWeEfGFOo7OZMLkfkYeMBMYfi0kqZhtLOZZREL0mDNPeyQmfQ==";
    public static final String PLATFORM_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCp77i4hjNxbR8GAzScEpP3RY95CC+T9Y8lQu+po9o5mn0mAvvsPGW7XKRZRluEAM6eRsvXcooIIXzhB3wSv7nKPwQS+zeKEY9u/fYRjl+F5mlbew7nkxCJr2ORTeR7152HgqXDu/DA6mPYpxWe6o235cpyvCHz/iNSro6Q4Ab2GwIDAQAB";


    private static final String DEFAULT_CHARSET = "utf-8";
    private static final String DEFAULT_SIGN_TYPE = "RSA2";

    public static final String UPDATE_SERVER_MODEL = "update_server_model_";


    public static final int SEC_OF_HOUR = 60 * 60;

    public class Recognition{
        public static final String RECOGNITION = "cloud.api.recognition.bitmain";
        public static final String RECOGNITION_SYNCHRONIZE = "cloud.api.recognition.bitmain.sync";
        public static final String QUERY = "cloud.api.recognition.query";
        public static final String BALANCE = "cloud.api.recognition.getBalance";
        public static final String RECOGNITION_CALLBACK = "cloud.api.recognition.async";
    }

    public class RedisKey{
        public static final String APP_INFO = "_app_info";
        public static final String INTERFACE_INFO = "_interface_info";
        public static final String INTERFACE_ACCEPT = "_interface_accept";
    }

    public class BizLog{
        public static final int info = 10;
        public static final int error = 20;
        public static final int warn = 30;
    }

    public class BizOperType{
        public static final String IMAGE_RECOGNITION = "image_recognition";
        public static final String RECOGNITION_QUERY = "recognition_query";
        public static final String BALANCE_QUERY = "interface_balance_query";
    }

}
