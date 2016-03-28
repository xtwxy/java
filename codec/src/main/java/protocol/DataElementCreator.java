package protocol;

import java.util.List;

public class DataElementCreator {
	private DefaultInitializer initializer;
	private List<Validator> validators;
	private ValueProducer updatesFrom;
	private List<ValueConsumer> updatesTo;

	public DataElement createDataElement() {
		return new DataElement(initializer, validators, updatesFrom, updatesTo);
	}

	public DefaultInitializer getInitializer() {
		return initializer;
	}

	public void setInitializer(DefaultInitializer initializer) {
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

}
