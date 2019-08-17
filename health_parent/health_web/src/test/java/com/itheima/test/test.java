package com.itheima.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sound.midi.Soundbank;

public class test {
    static BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();


    public static void main(String[] args) {
        String encode = bCryptPasswordEncoder.encode("abc");
        System.out.println(bCryptPasswordEncoder.matches("abc", encode));
        System.out.println(encode);
    }
}
