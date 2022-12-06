package com.example.codegenerator.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author loquy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultModel<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public int code;

    public String message;

    public T data;

    public static <T> ResultModel<T> success() {
        return new ResultModel<>(ResultEnums.SUCCESS.getCode(), ResultEnums.SUCCESS.getMessage(), null);
    }


    public static <T> ResultModel<T> success(T data) {
        return new ResultModel<>(ResultEnums.SUCCESS.getCode(), ResultEnums.SUCCESS.getMessage(), data);
    }


    public static <T> ResultModel<T> success(T data, String message) {
        return new ResultModel<>(ResultEnums.SUCCESS.getCode(), message, data);
    }


    public static <T> ResultModel<T> fail(String message) {
        return new ResultModel<>(ResultEnums.FAIL.getCode(), message, null);
    }


    public static <T> ResultModel<T> fail(int code, String message) {
        return new ResultModel<>(code, message, null);
    }


    public static <T> ResultModel<T> fail(int code, String message, T data) {
        return new ResultModel<>(code, message, data);
    }

    public enum ResultEnums {

        /**
         * 请求失败
         */
        FAIL(-1, "失败"),

        /**
         * 请求成功
         */
        SUCCESS(0, "成功");

        /**
         * 返回状态码
         */
        private final int code;

        /**
         * 返回消息
         */
        private final String message;

        ResultEnums(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
