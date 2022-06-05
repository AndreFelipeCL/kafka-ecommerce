package ecommerce;

import lombok.Getter;

@Getter
public final class User {
	private String uuid;
	private String email;

	public User(String uuid, String email) {
		this.uuid = uuid;
		this.email = email;
	}

	public User(String uuid) {
		this(uuid, null);
	}

	public String reportPath() {
		return "target/" + uuid + "-report.txt";
	}

	@Override
	public String toString() {
		return "User[" + "uuid=" + uuid + ", " + "email=" + email + ']';
	}
}
