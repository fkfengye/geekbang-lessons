package org.geekbang.thinking.in.spring.ioc.overview.repository;

import javafx.application.Application;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

/**
 * @author fankai
 * @project_name geekbang-lessons
 * @description
 * @create 2021-03-25 21:20
 **/
public class UserReository {

    private Collection<User> users;     // 自定义 Bean

    private BeanFactory beanFactory;    // 内建非 Bean 对象（依赖）

    private ObjectFactory<User> objectFactoryUser;

    private ObjectFactory<ApplicationContext> objectFactoryContext;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ObjectFactory<User> getObjectFactoryUser() {
        return objectFactoryUser;
    }

    public void setObjectFactoryUser(ObjectFactory<User> objectFactoryUser) {
        this.objectFactoryUser = objectFactoryUser;
    }

    public ObjectFactory<ApplicationContext> getObjectFactoryContext() {
        return objectFactoryContext;
    }

    public void setObjectFactoryContext(ObjectFactory<ApplicationContext> objectFactoryContext) {
        this.objectFactoryContext = objectFactoryContext;
    }

    @Override
    public String toString() {
        return "UserReository{" +
                "users=" + users +
                ", beanFactory=" + beanFactory +
                ", objectFactoryUser=" + objectFactoryUser +
                ", objectFactoryContext=" + objectFactoryContext +
                '}';
    }
}
