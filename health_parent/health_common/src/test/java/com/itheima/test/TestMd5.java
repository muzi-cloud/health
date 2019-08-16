package com.itheima.test;

import com.itheima.health.utils.MD5Utils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName TestPOI
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/6 14:53
 * @Version V1.0
 */
public class TestMd5 {

    /**
     * 测试md5
     * @throws Exception
     */
    @Test
    public void testMd5() throws Exception {
        System.out.println(MD5Utils.md5("123"));
        System.out.println(MD5Utils.md5("123"));

        System.out.println(MD5Utils.md5("zhangsanzhangsan123"));
        System.out.println(MD5Utils.md5("lisi123"));
    }

    // spring security中的BCryptPasswordEncoder方法采用SHA-256 +随机盐+密钥对密码进行加密
    /**
     * （1）加密(encode)：新增密码
     * （2）密码匹配(matches)：登录场景
     * @throws Exception
     */
    @Test
    public void testBCryptPasswordEncoder() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password1 = bCryptPasswordEncoder.encode("admin");
        String password2 = bCryptPasswordEncoder.encode("admin");
        System.out.println(password1);
        System.out.println(password2);

        // 登录比对
        boolean flag = bCryptPasswordEncoder.matches("123", "$2a$10$RF1vvkCyFPS1JPNLPBsLz.cJyeRciWDTilT3C4oNQ3LCQc2oe2kbi");
        System.out.println(flag);
    }

}
