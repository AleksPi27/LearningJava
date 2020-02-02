import com.kciray.ProductService;
import com.kciray.PromotionsService;
import org.springframework.beans.factory.BeanFactory;

public class Main {

    public static void main(String[] args)
    {
        BeanFactory beanFactory=new BeanFactory();
        beanFactory.instantiate("com.kciray");
        beanFactory.populateProperties();

        beanFactory.injectBeanNames();
        beanFactory.injectBeanFactory();
        ProductService productService = (ProductService) beanFactory.getBean("productService");
        System.out.println(productService);
        System.out.println("Dependency is : "+productService.promotionsService().getBeanName());
        PromotionsService promotionsService = productService.promotionsService();
        System.out.println("Bean name is "+promotionsService.getBeanName());
        System.out.println("Bean name is "+promotionsService.getBeanFactory());
        System.out.println("Bean name is "+productService.getBeanFactory());
    }
}

