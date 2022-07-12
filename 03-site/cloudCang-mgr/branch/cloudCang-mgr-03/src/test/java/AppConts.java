import com.cloud.cang.mgr.common.utils.IdGenerator;

/**
 * @version 1.0
 * @Author: zhouhong
 * @Date: 2018/9/6 15:50
 */
public class AppConts {
    public static final String APP_ID = "C2018080800001";
    public static final String APP_SERCET_KEY = "c38f00905ce74573";
    public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKnvuLiGM3FtHwYDNJwSk/dFj3kIL5P1jyVC76mj2jmafSYC++w8ZbtcpFlGW4QAzp5Gy9dyigghfOEHfBK/uco/BBL7N4oRj2799hGOX4XmaVt7DueTEImvY5FN5HvXnYeCpcO78MDqY9inFZ7qjbflynK8IfP+I1KujpDgBvYbAgMBAAECgYAR9YGiDpy1KgETU5dlvxjgEvvcoJ7WlibwmyhU1zoiyE7di4cwKhneOSYtQSA+zQ8I7xZvyG0J+vAFoBLesGBj8FSKhycPFTW0gw27dtNwPNn06lyANclVgrjgR+QOgBpJ62DOCwSd2YWCsQ6RHGH55/pHFSmNBpm1/5GyhTUO4QJBAP80pTNym+c89+GaKBzG0xGSJsga6wyHUrLHMpDBH7IgMF4gdjUOSqyZS4cwfb+u3rF2e9YkOuRnYVKCn0DtgokCQQCqdyGmS/s9cH0k/8hz7b8EqZpoPLT0xieI1Ly+A0MndNaZvOpTtF/8K4g5gBGbtnmfNz4bte+A1NVLf7S/kVqDAkEAiwWtvMIBRc2Dp0Un9s0kq9y2/akURCXvme5DkHF0B7/bOVtrqWlYqn9zkniK+AGV1Iyb10KzRjs+3AjGIkOAKQJAWh+8CajYPuZvM5WMDEahHDzzJrMZ1OPC2SPoWeltormyD/wCQ5j0umTlk8jzBklWSegyDQKGiq17y3vMOxAFMwJARyKS2DKW9wwuoOItKHyVY5vSY1WjPvgFCC2MuSWeEfGFOo7OZMLkfkYeMBMYfi0kqZhtLOZZREL0mDNPeyQmfQ==";
    public static final String PLATFORM_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+hwYSppfaPCPz9FEm55OL6PKIvo/PZQbXpoL+UDKxrg2sOOvYRYFikuF5PR6tQwTBvbxXc3MPufzntQIHApEt0+4k6sRPCs0gBe1gCSJePRDfqp2ITaCIXjloboGRCQd/POLIAPQ0YSwubMzg3SZxYFpxk7U37mMc9Ju4r9o4dwIDAQAB";
    public static final String HOST = "http://10.0.101.222:7091/gateway";
    /*public static final String APP_ID = "cang10000001";
    public static final String APP_SERCET_KEY = "8b2c56f0e5db4709";
    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALzUE/XsCBkHl4SGGnchW4/wgk3Tqk+Va7gom6Gh9JJFkF79742BGZGHpqsM9I1BtP18ewC4PomEjkKzrtwUwuhBtZs/+Dk2YTcjosORCrSJxJ+bemzEl1BE0Jv6VTxVRdlTPhtt5MeaS36x95MIdZxZk4xbAqGbjp3acJKhdF/RAgMBAAECgYEAsNJ/5pgidR3Q2v6hJdGM5ls4Ch0drOylNIyCQHdiiN6vdOSiQIYWJVs7zY2SK/zNuVmp7WCa3zfL59ZO9jRS1oGBMIwhSa+pACD63qML1YhpNSgqaCw1Fy+jHPn2NQFHFXkz6ywpWeZz5wiD7L6SHqMCwcPM4W7EortHAEAsRAECQQDyfhfTfvkoTbUZhVkH1+QEZPIBGY7I/KZwmENvrwty06ACrSVY8o+S7t9sPhGyhIWPXxi2epCgNsiEq3Wt9oBhAkEAx1i+IHRSA16WB27EzkiYY6yGnHlpDArpFNNnPtufcApcWnrmtsdC5nmkBIeZ+FTdRQW4YqddD8ZAvbkNIvXVcQJARWni2B/Yorjbxlptidk65CchPz9ZqB7qZgCwDSBGXrBFdX6q2jNU9fJ68jkyR6+3q7rFEDc1fycvbwrFvO57AQJAbcIazNu+3EQT7vu6OKoX3al8ZGMkx5CqTOEpE57w5kuOFfNdXH3SA/wtpqDS0bxIKFzB7O+hebH6q3tcFEeuwQJBALWcujvFLcYwPzkeBtHWHYTpHlJE7UHd3yaN5fZOBAC58Xs+jCVGlqC43OJiYb1MomSPVi3aFjSGF6vxXxLU1Kw=";
    public static final String PLATFORM_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCmpA49NvXf27DkGLv/vroj2Kn1RMhtuuPMZU8//wQK+8DYkB4i5mQ9bpBRnz2fqmp0Wj34yl06K1tYpL5mHkyxPPfygcDsFuUStfC1crhURV4+GvitlhSbGefskojYgVGu/g9YCJjK1m0ODIsBcpNdwS6NILxX7We6W2OXUCQOQIDAQAB";
    //public static final String HOST = "http://172.18.250.112:9811/gateway";
    public static final String HOST = "http://cloudapi.37cang.cn/gateway";*/

}
