package com._520.spring.test;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

public class SpringTest {

    // 模拟依赖注入
    @Test
    void test1() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<UserController> userControllerClass = UserController.class;
        UserController userController = new UserController();
        // 拿到userContoller中的所有字段
        Field[] declaredFields = userControllerClass.getDeclaredFields();
        Arrays.asList(declaredFields).forEach(System.out::println);
        // 拿到指定名字的字段
        Field userServiceField = userControllerClass.getDeclaredField("userService");
        userServiceField.setAccessible(true);
        UserService userService = new UserService();
        // 为userController对象设置字段
//        userServiceField.set(userController, userService);
        String name = userServiceField.getName();
        // 拿到set方法
        String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        System.out.println(setMethodName);
        // 拿到userController的setUserService方法
        Method method = userController.getClass().getMethod(setMethodName, UserService.class);
        method.invoke(userController, userService);
        System.out.println(userController.getUserService());
    }

    // 使用注解
    @Test
    void test2() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<UserController> userControllerClass = UserController.class;
        UserController userController = new UserController();
        // 拿到userContoller中的所有字段
        Field[] declaredFields = userControllerClass.getDeclaredFields();
        Stream.of(declaredFields).forEach(field -> {
            Autovierd annotation = field.getAnnotation(Autovierd.class);
            if (annotation == null){
                return;
            }
            System.out.println("field = " + field);
            Class<?> type = field.getType();
            try {
                Object o = type.getConstructor().newInstance();
                System.out.println(o.getClass());
                field.setAccessible(true);
                field.set(userController, o);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

        });
        System.out.println(userController.getUserService());
        System.out.println(userController.getUser());
    }
}
