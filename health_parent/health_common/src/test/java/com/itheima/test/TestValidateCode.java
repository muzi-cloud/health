package com.itheima.test;

import com.itheima.health.utils.ValidateCodeUtils;
import org.junit.Test;

/**
 * @ClassName TestValidateCode
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/8 16:09
 * @Version V1.0
 */
public class TestValidateCode {

    @Test
    public void generateCode(){
        Integer value4 = ValidateCodeUtils.generateValidateCode(4);
        Integer value6 = ValidateCodeUtils.generateValidateCode(6);
        System.out.println(value4);
        System.out.println(value6);
    }
}
