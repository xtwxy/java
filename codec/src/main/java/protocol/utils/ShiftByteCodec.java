package protocol.utils;

import protocol.ByteCodecable;
import protocol.CodecException;
import protocol.CompletionCallback;
import protocol.CompletionStatus;

public class ShiftByteCodec {
	
	public static int decode(ByteCodecable f, byte[] input, int offset, int length, CompletionCallback h) throws CodecException {

		int offsetBefore = offset;
		int offsetAfter = 0;

		CompletionStatus s = new CompletionStatus();
		while(true) {
			try {
				offsetAfter = f.decode(input, offsetBefore, input.length, s);				
			} catch (CodecException e) {
				offsetBefore += 1;
				continue;
			}
			h.completed(s.isCompleted());
			break;
		}
		return offsetAfter;
	}
	
	public static int encode(ByteCodecable f, byte[] input, int offset, int length, CompletionCallback h) throws CodecException {
		
		int offsetAfter = f.encode(input, offset, input.length, h);
				
		return offsetAfter;
		
	}
}
