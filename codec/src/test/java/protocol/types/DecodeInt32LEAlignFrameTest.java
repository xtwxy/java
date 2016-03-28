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
import protocol.utils.ByteConverter;

public class DecodeInt32LEAlignFrameTest extends TestCase {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	boolean updated = false; 

	public void testDecode() {
		byte[] input = { 0x1e, 0x76, 0x72, 0x7e, 0x01 };
		
		Integer32LE int32LE = new Integer32LE();
		int32LE.addObserver(new Observer(){

			@Override
			public void update(Observable o, Object arg) {
				updated = true;
			}
			
		});
		
		int32LE.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws Exception {
				if(value == null || !(0x017e7276 == (Integer)value)) {
					System.out.println(Hex.encodeHexString(ByteConverter.int32LeToBytes((Integer) value)));
					throw new CodecException("value != 0x017e7276");
				}
			}
			
		});
		
		int offset = 0;
		int32LE.initValue();
		CompletionStatus s = new CompletionStatus();
		while(true) {
			int offsetAfter = 0;
			try {
				offsetAfter = int32LE.decode(input, offset, input.length, s);
			} catch (CodecException e) {
				offset += 1;
				continue;
			}
			offset = offsetAfter;
			break;
		}
		assertEquals(true, s.isCompleted());
		assertEquals(true, updated);
		assertEquals(0x017e7276, (int)int32LE.getValue());
		assertEquals(5, offset);
	}
}
