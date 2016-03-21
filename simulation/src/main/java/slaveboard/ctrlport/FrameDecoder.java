package slaveboard.ctrlport;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import slaveboard.ctrlport.FrameUtils.State;

public class FrameDecoder implements ProtocolDecoder {
	
	private Logger logger = LoggerFactory.getLogger(FrameDecoder.class);

	@Override
	public void decode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {
		while(true) {
			State state = (State) session.getAttribute("state", State.DECODE_SOH);
			switch (state) {
			case DECODE_SOH:
				logger.info("State.DECODE_SOH");
				if(buffer.hasRemaining()) {
					if(FrameUtils.SOH == buffer.get()) {
						session.setAttribute("state", State.DECODE_TYPE);
					}
				} else {
					return;
				}
				break;
			case DECODE_TYPE:
				logger.info("State.DECODE_TYPE");
				if(buffer.hasRemaining()) {
					session.setAttribute("type", buffer.get());
					logger.info("type = " + session.getAttribute("type"));
					session.setAttribute("state", State.DECODE_LENGTH);
				} else {
					return;
				}
				break;
			case DECODE_LENGTH:
				logger.info("State.DECODE_LENGTH");
				if(buffer.remaining() > 2) {
					int length = (0xff & buffer.get()) | (0xff & buffer.get() << 8);
					session.setAttribute("length", length);
					logger.info("type = " + session.getAttribute("length"));
					session.setAttribute("state", State.DECODE_DATA);
				} else {
					return;
				}
				break;
			case DECODE_DATA:
				logger.info("State.DECODE_DATA");
				
				final int bytesToRead = ((int) session.getAttribute("length") - 7);
				logger.info("buffer.remaining() = " + buffer.remaining() + ", bytesToRead = " + bytesToRead);
				if(buffer.remaining() < bytesToRead) {
					// continue reading.
					return;
				} else {
					// extract data section.
					byte[] data = new byte[bytesToRead];
					buffer.get(data);
					session.setAttribute("data", data);
					session.setAttribute("state", State.DECODE_CHKSUM);
				}
				break;
			case DECODE_CHKSUM:
				logger.info("State.DECODE_CHKSUM");
				if(buffer.remaining() > 2) {
					int checksum = (0xff & buffer.get()) | (0xff & buffer.get() << 8);

					session.setAttribute("checksum", checksum);
					session.setAttribute("state", State.DECODE_EOT);
				} else {
					return;
				}
				break;
			case DECODE_EOT:
				logger.info("State.DECODE_EOT");
				if(buffer.hasRemaining()) {
					if(FrameUtils.EOT == buffer.get()) {
						int checksum = (int) session.getAttribute("checksum");
						// create a data frame for next phase decoding.
						Frame f = new Frame((byte) session.getAttribute("type"),
								(byte[]) session.getAttribute("data"));
						int caculatedChecksum = FrameUtils.checksum(f);
						logger.info("checksum = " + checksum + ", caculatedChecksum = " + caculatedChecksum);
						if(checksum != caculatedChecksum) {
							logger.info("checksum error.");
						}
						logger.info("write out decoded message.");
						out.write(f);
						logger.info("bytes remaining: " + buffer.remaining());
						return;
					} else {
						// invalid data frame.
						session.setAttribute("state", State.DECODE_SOH);
					}
				} else {
					return;
				}
				break;
			default:
				break;
	
			}
		}
	}

	@Override
	public void dispose(IoSession session) throws Exception {
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
	}
}
