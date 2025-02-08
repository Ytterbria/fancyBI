package com.ytterbria.fancyback.annotation;

import java.lang.annotation.*;

/**
 * 权限校验
 *
 * @author ytterbria
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented //这个注解可以被javadoc工具提取到文档中
public @interface AuthCheck {

    /**
     * 必须有某个角色
     *
     */
    String mustRole() default "";

}

