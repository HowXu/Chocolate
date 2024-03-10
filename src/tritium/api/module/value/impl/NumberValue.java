package tritium.api.module.value.impl;

import tritium.api.module.Module;
import tritium.api.module.value.Value;
import tritium.api.module.value.clamper.NumberClamper;
import tritium.api.module.value.parse.NumberParser;

public class NumberValue<T extends Number> extends Value<T> {

	private final T minimum, maximum, inc;

	public NumberValue(String label, T value, T minimum, T maximum, T inc) {
		super(label, value);
		this.minimum = minimum;
		this.maximum = maximum;
		this.inc = inc;
	}

	public T getMinimum() {
		return this.minimum;
	}

	public T getMaximum() {
		return this.maximum;
	}

	public T getInc() {
		return this.inc;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = NumberClamper.clamp((T) value, (T) minimum, (T) maximum);
	}

	public T getSliderValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		try {
			this.setValue(NumberParser.parse(value, (Class<T>) ((Number) this.value).getClass()));
		} catch (NumberFormatException e) {
		}
	}

	@Override
	public void init(Module module) {
		
	}
}
