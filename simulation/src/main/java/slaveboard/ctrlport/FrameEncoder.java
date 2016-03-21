package slaveboard.ctrlport;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class FrameEncoder implements ProtocolEncoder {

	@Override
	public void dispose(IoSession session) throws Exception {
	}

	@Override
	public void encode(IoSession session, Object msg, ProtocolEncoderOutput out) throws Exception {
		Frame f = (Frame) msg;
		
		IoBuffer b = IoBuffer.allocate(f.getFrameLength(), false);
		
		b.put(FrameUtils.SOH);
		
		b.put(f.getType());
		
		int frameLength = f.getFrameLength();
		b.put((byte)(frameLength & 0xff));
		b.put((byte)(frameLength >> 8 & 0xff));
		
		b.put(f.getData());
		
		int checksum = FrameUtils.checksum(f);
		b.put((byte)(checksum & 0xff));
		b.put((byte)(checksum >> 8 & 0xff));
		
		b.put(FrameUtils.EOT);
		
		b.flip();
		
		out.write(b);
	}
}
