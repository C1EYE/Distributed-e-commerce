package com.c1eye.dsmail.member.exception;

/**
 * @author c1eye
 * time 2022/3/21 20:27
 */
public class UsernameExistException extends RuntimeException{
    public UsernameExistException() {
        super("用户名已被使用");
    }
}
