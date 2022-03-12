package com.c1eye.common.constant;

/**
 * @author c1eye
 * time 2022/3/12 09:12
 */

public class ProductConstant {

    public enum AttrEnum{
        ATTR_TYPE(1,"基本属性"),ATTR_TYPE_SALE(0,"销售属性");
        private int code;
        private String msg;

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
