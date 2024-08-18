package tritium.api.module.value.impl;

import java.util.Optional;
import java.util.function.Predicate;

import tritium.api.module.Module;
import tritium.api.module.value.Value;
import tritium.api.module.value.parse.BooleanParser;



public class BooleanValue extends Value<Boolean> {

    public BooleanValue(String label, Boolean value) {
        super(label, value);
    }

    @Override
    public void setValue(String input) {
        Optional<Boolean> result = BooleanParser.parse(input);
        result.ifPresent(aBoolean -> this.value = aBoolean);
    }

    public void toggle() {
        this.value ^= true;
    }

    public boolean isEnabled() {
        return this.value;
    }

    public void setEnabled(boolean enabled) {
        this.value = enabled;
    }

	@Override
	public void init(Module module) {
		
	}
}