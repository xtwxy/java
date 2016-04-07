package protocol.types;

public class Resetter {
	protected Object v;

	public Resetter(Object b) {
		v = b;
	}

	public Resetter() {
	}

	public Object reset() {
		return v;
	}
}
