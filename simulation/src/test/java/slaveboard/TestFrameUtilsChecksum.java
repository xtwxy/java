package slaveboard;

import junit.framework.Assert;
import junit.framework.TestCase;
import slaveboard.ctrlport.FrameUtils;

public class TestFrameUtilsChecksum extends TestCase {
	public void test() {
		// 7e 01 07 00 86 00 6e
		byte[] b = {0x7e, 0x01, 0x07, 0x00};
		final short CHECKSUM = (0x86 | (0x00 << 8));
		Assert.assertEquals(CHECKSUM, FrameUtils.checksum(b));
	}
}
