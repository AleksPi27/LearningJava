package com.kciray;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.stereotype.Component;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class ProductService implements BeanFactoryAware {
    private BeanFactory beanFactory;
    @Autowired
    private PromotionsService promotionsService;
    public PromotionsService promotionsService()
    {
        return promotionsService;
    }
    public void setPromotionsService(PromotionsService promotionsService)
    {
        this.promotionsService=promotionsService;
    }
@Override
    public void setBeanFactory(BeanFactory factory)
{
    beanFactory=factory;
}

public BeanFactory getBeanFactory  ()
{
    int a;
    a=5;
    System.out.println(a);









    return beanFactory;

}
}
