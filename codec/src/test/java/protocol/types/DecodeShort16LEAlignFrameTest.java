package protocol.types;

import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import protocol.CodecException;
import protocol.CompletionStatus;
import protocol.Validator;

public class DecodeShort16LEAlignFrameTest extends TestCase {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	boolean updated = false; 

	public void testDecode() {
		byte[] input = { 0x76, 0x72, 0x7e, 0x01 };
		
		Short16LE short16LE = new Short16LE();
		short16LE.addObserver(new Observer(){

			@Override
			public void update(Observable o, Object arg) {
				updated = true;
			}
			
		});
		
		short16LE.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws Exception {
				if(value == null || !(0x7e72 == (Short)value)) {
					throw new CodecException("value != 0x7e72");
				}
			}
			
		});
		
		int offset = 0;
<<<<<<< HEAD
		short16LE.reset();
=======
		short16LE.initValue();
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		CompletionStatus s = new CompletionStatus();
		while(true) {
			int offsetAfter = 0;
			try {
				offsetAfter = short16LE.decode(input, offset, input.length, s);
			} catch (CodecException e) {
				offset += 1;
				continue;
			}
			offset = offsetAfter;
			break;
		}
		assertEquals(true, s.isCompleted());
		assertEquals(true, updated);
		assertEquals(0x7e72, (short)short16LE.getValue());
		assertEquals(3, offset);
	}
}
