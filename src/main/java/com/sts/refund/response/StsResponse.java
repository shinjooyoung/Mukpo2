package com.sts.refund.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StsResponse<T> {

    private int status;
    private String message;
    private T data;


    public static<T> StsResponse<T> response(int status, String message) {
        return response(status, message, null);
    }

    public static<T> StsResponse<T> response(int status, String message, T data) {
        return StsResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

}
