package org.springframework.beans.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.stereotype.Component;
import org.springframework.beans.factory.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private Map<String,Object> singletons=new HashMap<>();
    public Object getBean(String beanName)
    {
        return singletons.get(beanName);
    }
    public void instantiate(String basePackage)
    {
        try
        {
            ClassLoader classLoader=ClassLoader.getSystemClassLoader();
                    String path=basePackage.replace('.','/');
            Enumeration<URL> resources=classLoader.getResources(path);
            while(resources.hasMoreElements())
            {
                URL resource=resources.nextElement();
                File file = new File(resource.toURI());

                for (File classFile : file.listFiles())
                {
                    String fileName=classFile.getName();
                    System.out.println(fileName);

                    if(fileName.endsWith(".class"))
                    {
                        String className=fileName.substring(0,fileName.lastIndexOf("."));
                        Class classObject=Class.forName(basePackage+"."+className   );

                        if (classObject.isAnnotationPresent(Component.class)||classObject.isAnnotationPresent(Service.class))
                        {
                            System.out.println("Component: "+classObject);
if(classObject.isAnnotationPresent(Service.class)) System.out.println("This is a service");
else System.out.println("This is a component");
                            Object instance = classObject.getDeclaredConstructor().newInstance();
                            String beanName=className.substring(0,1).toLowerCase()+className.substring(1);
                            singletons.put(beanName,instance);

                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void populateProperties() {
        System.out.println("==populateProperties==");

    for (Object object:singletons.values()) {
    for(Field field : object.getClass().getDeclaredFields())
    {
        if(field.isAnnotationPresent(Autowired.class)) {
        for (Object dependency: singletons.values())
        {
         if (dependency.getClass().equals(field.getType()))
         {
             String setterName="set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
             System.out.println("Setter name = "+setterName);
             try {
                 Method setter=object.getClass().getMethod(setterName,dependency.getClass());
                 setter.invoke(object,dependency);
             } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                 e.printStackTrace();
             }
         }
        }
        }
    }
    }
    }

    public void injectBeanNames() {
        for (String name: singletons.keySet())
        {
            Object bean=singletons.get(name);
            if(bean instanceof BeanNameAware)
            {
                ((BeanNameAware) bean).setBeanName(name);
            }
        }
    }

    public void injectBeanFactory()
    {
        for (Object object: singletons.values())
        {

            if(object instanceof BeanFactoryAware)
                ((BeanFactoryAware) object).setBeanFactory(BeanFactory.this);
        }
    }
}
