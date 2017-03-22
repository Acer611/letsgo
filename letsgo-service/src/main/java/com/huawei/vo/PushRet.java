package com.huawei.vo;

public class PushRet
{
    //返回信息
    private String message;
    //返回码
    private int resultcode;
    
    private String requestID;
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public int getResultcode()
    {
        return resultcode;
    }
    
    public void setResultcode(int resultcode)
    {
        this.resultcode = resultcode;
    }
    
    public String getRequestID()
    {
        return requestID;
    }
    
    public void setRequestID(String requestID)
    {
        this.requestID = requestID;
    }
    
}
