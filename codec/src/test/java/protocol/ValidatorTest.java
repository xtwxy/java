package protocol;

import junit.framework.TestCase;
import protocol.Validator;

public class ValidatorTest extends TestCase {
	
	public void test() throws Exception {
		final Byte expected = 0x7e;
		Validator v = new Validator () {

			@Override
			public void validate(Object value) {
				assertEquals(expected, value);
			}
		};
		
		v.validate(expected);
	}
}
