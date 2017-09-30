package protocol;

import java.util.List;

public class DataElement implements ValueConsumer, ValueProducer {
	private Initializer initializer;
	private List<Validator> validators;
	private ValueProducer updatesFrom;
	private List<ValueConsumer> updatesTo;

	public DataElement(Initializer initializer, 
			List<Validator> validators, 
			ValueProducer updatesFrom,
			List<ValueConsumer> updatesTo) {
		this.initializer = initializer;
		this.validators = validators;
		this.updatesFrom = updatesFrom;
		this.updatesTo = updatesTo;
		initialize();
	}

	public Initializer getInitializer() {
		return initializer;
	}

	public void setInitializer(Initializer initializer) {
		this.initializer = initializer;
	}

	public List<Validator> getValidators() {
		return validators;
	}

	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}

	public ValueProducer getUpdatesFrom() {
		return updatesFrom;
	}

	public void setUpdatesFrom(ValueProducer updatesFrom) {
		this.updatesFrom = updatesFrom;
	}

	public List<ValueConsumer> getUpdatesTo() {
		return updatesTo;
	}

	public void setUpdatesTo(List<ValueConsumer> updatesTo) {
		this.updatesTo = updatesTo;
	}

	public void initialize() {
		
	}
}
