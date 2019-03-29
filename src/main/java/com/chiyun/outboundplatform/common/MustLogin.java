package com.chiyun.outboundplatform.common;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MustLogin {
    int[] rolerequired();//0：全部都可访问;其他则为选择角色可访问
}
