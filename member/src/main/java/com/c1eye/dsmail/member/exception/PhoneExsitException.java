package com.c1eye.dsmail.member.exception;

/**
 * @author c1eye
 * time 2022/3/21 20:28
 */
public class PhoneExsitException extends  RuntimeException{
    public PhoneExsitException() {
        super("电话号已被使用");
    }
}
