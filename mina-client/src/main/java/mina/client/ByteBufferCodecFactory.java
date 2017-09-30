package mina.client;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteBufferCodecFactory implements ProtocolCodecFactory {
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;
	private Logger logger = LoggerFactory.getLogger(ByteBufferCodecFactory.class);

	public ByteBufferCodecFactory() {
		encoder = new ByteBufferEncoder();
		decoder = new ByteBufferDecoder();
	}
	
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		logger.info("get decoder");
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		logger.info("get encoder");
		return encoder;
	}

}
