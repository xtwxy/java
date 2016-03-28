package protocol.types;

import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import protocol.CodecException;
import protocol.CompletionStatus;
import protocol.Validator;

public class DecodeShort16BEAlignFrameTest extends TestCase {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	boolean updated = false; 

	public void testDecode() {
		byte[] input = { 0x76, 0x72, 0x7e, 0x01 };
		
		Short16BE short16BE = new Short16BE();
		short16BE.addObserver(new Observer(){

			@Override
			public void update(Observable o, Object arg) {
				updated = true;
			}
			
		});
		
		short16BE.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws Exception {
				if(value == null || !(0x727e == (Short)value)) {
					throw new CodecException("value != 0x727e");
				}
			}
			
		});
		
		int offset = 0;
		short16BE.initValue();
		CompletionStatus s = new CompletionStatus();
		while(true) {
			int offsetAfter = 0;
			try {
				offsetAfter = short16BE.decode(input, offset, input.length, s);
				if(offsetAfter == 0) {
					// continue reading more byte...
				}
			} catch (CodecException e) {
				offset += 1;
				continue;
			}
			offset = offsetAfter;
			break;
		}
		assertEquals(true, s.isCompleted());
		assertEquals(true, updated);
		assertEquals(0x727e, (short)short16BE.getValue());
		assertEquals(3, offset);
	}
}
