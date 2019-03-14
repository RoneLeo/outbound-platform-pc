package com.chiyun.outboundplatform.framework;

import com.chiyun.outboundplatform.web.DictionaryController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Linqi on 2019-03-14.
 */
@Component
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments var1) throws Exception {

        //  System.out.println("开始加载字典！！！");

       //   System.out.println("加载完毕！！！");
    }
}
