package slaveboard.serialport;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteBufferDecoder implements ProtocolDecoder {
	
	private Logger logger = LoggerFactory.getLogger(ByteBufferDecoder.class);

	@Override
	public void decode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
		//logger.info("buffer.remaining() = " + buffer.remaining());
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		out.write(bytes);
	}

	@Override
	public void dispose(IoSession session) throws Exception {
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
	}
}
