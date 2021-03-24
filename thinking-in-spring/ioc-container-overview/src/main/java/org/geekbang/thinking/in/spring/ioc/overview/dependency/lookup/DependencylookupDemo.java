package org.geekbang.thinking.in.spring.ioc.overview.dependency.lookup;

import org.geekbang.thinking.in.spring.ioc.overview.annotation.Super;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 *      依赖查找
 *      1、通过名称的方式来查找
 *      2、通过类型的方式来查找
 * @create 2021-03-24 21:54
 **/
public class DependencylookupDemo {

    public static void main(String[] args) {
        // 配置xml配置文件
        // 启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");

        // 按照类型查找
        lookupByType(beanFactory);
        // 按照类型查找集合对象
        lookupCollectionByType(beanFactory);

        // 通过注解的方式进行查找
        lookupByAnnotationType(beanFactory);

        // 根据名字进行实时查找
//        lookupInRealTime(beanFactory);
        // 根据名字进行延迟查找
//        lookupInLazy(beanFactory);

    }


    /**
     * 通过注解的方式查找
     * @param beanFactory
     */
    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map)listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("------------- 通过注解的方式查找 ---------------");
            System.out.println(users);
        }
    }

    /**
     * 通过类型查找
     * @param beanFactory
     */
    private static void lookupByType(BeanFactory beanFactory){
        User user = beanFactory.getBean(User.class);
        System.out.println("------------- 通过类型查找 ---------------");
        System.out.println(user);
    }


    /**
     * 按照类型查找集合对象
     * @param beanFactory
     */
    private static void lookupCollectionByType(BeanFactory beanFactory){
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("------------- 按照类型查找集合对象 ---------------");
            System.out.println(users);
        }
    }


    /**
     * 实时查找
     * @param beanFactory
     */
    private static void lookupInRealTime(BeanFactory beanFactory){
        User user = (User) beanFactory.getBean("user");
        System.out.println("------------- 实时查找 ---------------");
        System.out.println(user);
    }


    /**
     * 延迟查找
     * @param beanFactory
     */
    private static void lookupInLazy(BeanFactory beanFactory){
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("------------- 延迟查找 ---------------");
        System.out.println(user);
    }
}
