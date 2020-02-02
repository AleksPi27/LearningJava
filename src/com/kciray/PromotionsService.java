package com.kciray;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.stereotype.Component;

@Component
public class PromotionsService implements BeanNameAware, BeanFactoryAware {
    private String beanName;
    @Override
    public void setBeanName(String name)
    {
        beanName=name;
    }

    public String getBeanName()
    {
        return beanName;
    }
private BeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory factory)
    {
        beanFactory=factory;
    }

    public BeanFactory getBeanFactory()
    {
        return beanFactory;
    }

}
