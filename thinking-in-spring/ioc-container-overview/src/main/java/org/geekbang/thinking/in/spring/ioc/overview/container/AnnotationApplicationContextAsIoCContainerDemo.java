package org.geekbang.thinking.in.spring.ioc.overview.container;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 *      AnnotationApplicationContext 作为 IOC容器实例
 * @create 2021-03-26 22:43
 **/
public class AnnotationApplicationContextAsIoCContainerDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 将当前类 AnnotationApplicationContextAsIoCContainerDemo 作为配置类（Configuration Class）
        applicationContext.register(AnnotationApplicationContextAsIoCContainerDemo.class);

        // 启动应用上下文
        applicationContext.refresh();

        // 依赖查找集合对象
        lookupCollectionByType(applicationContext);
    }


    @Bean
    public User user(){
        User user = new User();
        user.setId(1L);
        user.setName("小马哥");
        return user;
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


}
