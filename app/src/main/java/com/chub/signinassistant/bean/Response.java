package com.chub.signinassistant.bean;

/**
 * Description：
 * Created by Chub on 2017/11/25.
 */

public class Response<T>{


    private int code;
    private T data;
    private T datas;
    private String error;

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return data == null ? datas : data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(T data) {
        this.data = data;
        this.datas = data;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ",\"data\":" + data +
                ",\"datas\":" + datas +
                ",\"error\":" + (error == null ? null : "\"" + error + "\"") +
                '}';
    }
}
