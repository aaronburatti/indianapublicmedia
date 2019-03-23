package com.example.nebulese.myapplication.api;

public class ResponseClass {
    int ErrorCode;
    String onErrorMessage;
    String mMessaage;
    private Boolean isSuccess = false;

    public ResponseClass(int errorCode, String onErrorMessage, String mMessaage){
        ErrorCode = errorCode;
        this.onErrorMessage = onErrorMessage;
        this.mMessaage = mMessaage;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getOnErrorMessage() {
        return onErrorMessage;
    }

    public void setOnErrorMessage(String onErrorMessage) {
        this.onErrorMessage = onErrorMessage;
    }

    public String getmMessaage() {
        return mMessaage;
    }

    public void setmMessaage(String mMessaage) {
        this.mMessaage = mMessaage;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public void setSuccess(boolean success){
        isSuccess = success;
    }

    public boolean isSuccess(){
        return isSuccess;
    }
}
