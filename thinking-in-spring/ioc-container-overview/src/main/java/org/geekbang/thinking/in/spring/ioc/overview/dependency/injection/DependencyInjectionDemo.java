package org.geekbang.thinking.in.spring.ioc.overview.dependency.injection;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.geekbang.thinking.in.spring.ioc.overview.repository.UserReository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description     依赖注入
 *      spring ioc 依赖来源
 *          1、自定义Bean
 *          2、容器内建Bean对象
 *          3、容器内建依赖
 *
 * @create 2021-03-24 21:54
 **/
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        // 配置xml配置文件
        // 启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

//        test01(beanFactory);
//        test02(beanFactory);
//        test03(beanFactory);
//        test04(beanFactory);
        test05(beanFactory);

    }


    /**
     * spring ioc 依赖来源
     *      1、自定义Bean
     *      2、容器内建Bean对象
     *      3、容器内建依赖
     * @param beanFactory
     */
    public static void test05(BeanFactory beanFactory){
        // 1、自定义Bean
        UserReository userReository = beanFactory.getBean("userReository2", UserReository.class);

        // 2、依赖注入（内建依赖）
        System.out.println(userReository.getBeanFactory());

        // 依赖查找（查找不到会错误）
//        System.out.println(beanFactory.getBean(BeanFactory.class));

        // 3、容器内建Bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("获取 Environment 类型的 Bean:" + environment);
    }


    /**
     * 自动注入 ObjectFactory 对象
     *      为什么 objectFactoryContext 中的对象跟 beanFactory是相同的
     * @param beanFactory
     */
    public static void test04(BeanFactory beanFactory){
        UserReository userReository = beanFactory.getBean("userReository2", UserReository.class);

        // 注入的ObjectFactory对象中是ApplicationContext
        ObjectFactory<ApplicationContext> objectFactoryContext = userReository.getObjectFactoryContext();
        System.out.println(objectFactoryContext.getObject());

        System.out.println(objectFactoryContext.getObject() == beanFactory);
    }


    /**
     * 依赖查找、依赖注入
     *      通过依赖查找、依赖注入的例子来获取BeanFactory对象，我们发现，虽然都是依赖关系的处理，
     *      但是是有区别的，至少打印出来的结果说明，两者的机制不一样
     * @param beanFactory
     */
    public static void test03(BeanFactory beanFactory){
        UserReository userReository = beanFactory.getBean("userReository", UserReository.class);
        UserReository userReository2 = beanFactory.getBean("userReository2", UserReository.class);

        // 依赖注入的BeanFactory对象
        System.out.println(userReository2.getBeanFactory());
        // 依赖查找BeanFactory，会报错，找不到bean  No qualifying bean
        System.out.println(beanFactory.getBean(BeanFactory.class));
    }


    /**
     * 注入内建对象
     *      BeanFactory 并不是一个普通的对象，不能通过 BeanFactory 容器来获取
     * @param beanFactory
     */
    public static void test02(BeanFactory beanFactory){
        UserReository userReository = beanFactory.getBean("userReository", UserReository.class);
        UserReository userReository2 = beanFactory.getBean("userReository2", UserReository.class);

        System.out.println(userReository2.getBeanFactory());
        System.out.println(userReository2.getBeanFactory() == beanFactory);
    }


    /**
     * 手动注入、自动注入
     *      手动注入Bean，可以调整Bean的注入顺序，自动注入会默认按照Bean的定义顺序来注入
     * @param beanFactory
     */
    public static void test01(BeanFactory beanFactory){
        UserReository userReository = beanFactory.getBean("userReository", UserReository.class);
        UserReository userReository2 = beanFactory.getBean("userReository2", UserReository.class);

        System.out.println(userReository);
        System.out.println(userReository2);
    }



}
