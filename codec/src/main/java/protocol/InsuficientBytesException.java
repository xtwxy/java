package protocol;

public class InsuficientBytesException extends CodecException {
	
	private static final long serialVersionUID = -3303304570408528370L;

	public InsuficientBytesException(Exception e) {
		super(e);
	}
	public InsuficientBytesException(String msg) {
		super(msg);
	}

}
