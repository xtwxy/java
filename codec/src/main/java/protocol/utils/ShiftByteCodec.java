package protocol.utils;

import protocol.ByteCodecable;
import protocol.CodecException;

public class ShiftByteCodec {
	
	public static int decode(ByteCodecable f, byte[] input, int offset, int length) throws CodecException {

		int offsetAfter = 0;

		while(true) {
			try {
				offsetAfter = f.decode(input, offset, input.length);
			} catch (CodecException e) {
				offset += 1;
				continue;
			}
			break;
		}
		return offsetAfter;
	}
	
	public static int encode(ByteCodecable f, byte[] input, int offset, int length) throws CodecException {
		return f.encode(input, offset, input.length);
	}
}
