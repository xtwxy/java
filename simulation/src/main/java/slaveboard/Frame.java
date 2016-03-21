package slaveboard;

public class Frame {
	public static final int FRAME_OVERHEAD = 7;
	public Frame(byte type, byte[] b) {
		frameType = type;
		data = b;
	}
	
	public byte getType() {
		return frameType;
	}

	public byte[] getData() {
		return data;
	}
	
	public int getFrameLength() {
		return (data.length + FRAME_OVERHEAD);
	}
	
	private byte frameType;
	private byte[] data;
}
