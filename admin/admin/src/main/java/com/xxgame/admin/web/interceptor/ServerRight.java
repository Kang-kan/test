package com.xxgame.admin.web.interceptor;

import java.lang.annotation.*;

/**
 * 服务器权限
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServerRight {

    /**
     * 与之关联的参数下标。
     * @return
     */
    int argsIndex() default -1;

}
