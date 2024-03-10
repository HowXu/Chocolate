package me.imflowow.tritium.utils.information;

public class Version {
	VersionType type;
	int major;
	int minor;
	int revision;

	public Version(VersionType type, int major, int minor, int revision) {
		this.type = type;
		this.major = major;
		this.minor = minor;
		this.revision = revision;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.major).append(".").append(this.minor).append(".").append(this.revision).append("-")
				.append(this.type.toString());
		return sb.toString();
	}

	public static enum VersionType {
		Development, Beta, Release;
	}

	public VersionType getType() {
		return type;
	}

	public void setType(VersionType type) {
		this.type = type;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}
}
