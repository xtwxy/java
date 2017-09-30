package slaveboard.serialport;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ByteBufferEncoder implements ProtocolEncoder {

	@Override
	public void dispose(IoSession session) throws Exception {
	}

	@Override
	public void encode(IoSession session, Object msg, ProtocolEncoderOutput out) throws Exception {
		byte[] bytes = (byte[]) msg;
		
		IoBuffer b = IoBuffer.allocate(bytes.length, false);
		
		b.put(bytes);
		
		b.flip();
		
		out.write(b);
	}
}
