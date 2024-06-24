package ysoserial;

import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;

import java.io.*;
import java.util.concurrent.Callable;

public class Serializer implements Callable<byte[]> {
	private final Object object;
	public Serializer(Object object) {
		this.object = object;
	}

	public byte[] call() throws Exception {
		return serialize(object);
	}

	public static byte[] serialize(final Object obj) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		serialize(obj, out);
		return out.toByteArray();
	}

	public static void serialize(final Object obj, final OutputStream out) throws IOException {
		final ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(obj);
	}

//    public static byte[] Hessian_serialize(final Object obj) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        Hessian_serialize(obj, out);
//        return out.toByteArray();
//    }

    public static void Hessian_serialize(final Object obj, String filename) throws IOException {
        SerializerFactory serializerFactory=new SerializerFactory(); //无需继承Serializable也可进行序列化和反序列化
        serializerFactory.setAllowNonSerializable(true);
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        HessianOutput hessianOutput2 = new HessianOutput(fileOutputStream);
        hessianOutput2.setSerializerFactory(serializerFactory);
        hessianOutput2.writeObject(obj);
        hessianOutput2.close();
    }

}
