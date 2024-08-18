package tritium.api.module;

public class GlobalModule extends Module {

	private boolean hidden;

	public GlobalModule(String label, String introduction) {
		super(label, introduction, false, false);
	}

	public GlobalModule(String label, String introduction, boolean enabled) {
		super(label, introduction, enabled, false);
	}
	
	public GlobalModule(String label, boolean hidden) {
		super(label, "", false, false);
		this.hidden = hidden;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}


}
