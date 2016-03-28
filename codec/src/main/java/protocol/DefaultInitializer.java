package protocol;

public class DefaultInitializer implements Initializer {
	protected Object v;

	public DefaultInitializer(Object b) {
		v = b;
	}

	public DefaultInitializer() {
	}

	public Object initialValue() {
		return v;
	}
}
