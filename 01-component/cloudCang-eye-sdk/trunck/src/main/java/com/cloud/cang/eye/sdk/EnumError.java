package com.cloud.cang.eye.sdk;

//return value list
public enum EnumError {
	SUCCESS("0", "Success"),
	
	INVALID_PARAM("-1001", "invalid parameter"),
	
	SDK_NOT_INIT("-1002", "SDK not initialized"),
	
	SDK_RE_INIT("-1003", "SDK can’t be re-initialized"),
	
	TOO_MUCH_MODULE("-1004", "Too much recognition modules"),
	
	SDK_INIT_ERROR("-1005", "SDK init error"),
	
	CONNECT_INVENTORY_ERROR("-1006", "Can’t connect to inventory module"),
	
	CONNECT_ERROR("-1007", "Can’t connect to eye server"),
	
	CAMERA_NOT_WORK("-2001", "Camera not working properly"),
	
	CAMERA_NOT_FOUND("-2002", "Camera not found"),
	
	NO_DISK_SPACE("-2003", "Not enough disk space in module"),
	
	MODULE_NOT_CONNECT("-2004", "Module cannot be connected"),
	
	START_UPDATE_ERROR("-2011", "Start updating error"),
	
	BUSY_UPDATING("-2012", "Busy for updating"),
	
	CHANNEL_NOT_EXIST("-2021", "Channel not exist"),
	
	CHANNEL_ALREADY_OPEN("-2022", "Channel already open"),
	
	CHANNEL_NOT_OPEN("-2023", "Channel not open"),
	
	CELL_ID_USED("-2024", "Cell ID already be used"),
	
	RECOG_TIMEOUT("-3001", "Recognition timeout"),
	
	GOODS_PLACE_ERROR("-3002", "Abnormal placement of goods"),
	
	//20180719 added 
	SERVER_NOT_INIT("-5001", "Server not initialized"),
	
	BUSY_CALLING("-5002", "Busy for calling"),
	
	UNKNOWN_ERROR("-9000", "Unknown exception/error"),
	
	DATA_FORMAT_ERROR("-9001", "data format error");	

    private String code;
    
    private String msg;

    private EnumError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsgFromCode(String code) {
        for (EnumError error : EnumError.values()) {
            if (code.equals(error)) {
            	return error.getMsg();
            }
        }
        return UNKNOWN_ERROR.getMsg();
    }
}