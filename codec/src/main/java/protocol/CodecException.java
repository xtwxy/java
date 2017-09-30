package protocol;

public class CodecException extends Exception {

	public CodecException(String msg) {
		super(msg);
	}
	
	public CodecException(Exception e) {
		super(e);
	}

	private static final long serialVersionUID = 1102621209014241963L;

}
