package dc2800.server.proto;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import protocol.CodecException;
import protocol.CompletionCallback;
import protocol.types.ElementArray;

public class FrameEncodeTest extends TestCase {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final int BUFFER_SIZE = 2048;
	private boolean complete = false;
	
<<<<<<< HEAD
	public void testEncode() {
=======
	public void testEncode() throws CodecException {
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		ElementArray data = new ElementArray();
		Frame f = Frame.createForEncoding((byte) 1, (byte) 1, (byte) 1, data);
		byte[] buffer = new byte[BUFFER_SIZE];
		
		CompletionCallback h = new CompletionCallback() {

			@Override
			public void completed(boolean yes) {
				complete = yes;
			}
			
		};
		int offset = 0;
<<<<<<< HEAD
=======
		f.reset();
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		try {
			offset = f.encode(buffer, offset, buffer.length, h);
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < offset; ++i) {
				byte c = buffer[i];
				sb.append(Hex.encodeHexString(new byte[] {c})).append(" ");
			}
			logger.info(sb.toString());
			
		} catch (CodecException e) {
			e.printStackTrace();
		}
		assertEquals(true, complete);
	}
}
