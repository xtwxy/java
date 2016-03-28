package protocol.types;

import java.util.Observable;
import java.util.Observer;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import protocol.CodecException;
import protocol.CompletionStatus;
import protocol.Validator;

public class DecodeByte8AlignFrameTest extends TestCase {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	boolean updated = false; 

	public void testDecode() {
		byte[] input = { 0x76, 0x72, 0x7e, 0x01 };
		
		Byte8 byte8 = new Byte8();
		byte8.addObserver(new Observer(){

			@Override
			public void update(Observable o, Object arg) {
				updated = true;
			}
			
		});
		
		byte8.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws Exception {
				logger.info(Hex.encodeHexString(new byte[] {(Byte)value}));
				if(value == null || !(0x7e == (Byte)value)) {
					throw new CodecException("value != 0x7e");
				}
			}
			
		});
		
		int offset = 0;
		byte8.reset();
		CompletionStatus s = new CompletionStatus();
		while(true) {
			int offsetAfter = 0;
			try {
				offsetAfter = byte8.decode(input, offset, input.length, s);
			} catch (CodecException e) {
				offset += 1;
				continue;
			}
			offset = offsetAfter;
			break;
		}
		assertEquals(true, s.isCompleted());
		assertEquals(true, updated);
		assertEquals(0x7e, (byte)byte8.value());
		assertEquals(3, offset);
	}
}
