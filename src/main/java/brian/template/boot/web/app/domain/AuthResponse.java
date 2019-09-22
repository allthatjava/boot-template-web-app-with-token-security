package brian.template.boot.web.app.domain;

import java.io.Serializable;

public class AuthResponse implements Serializable {

	private String token;
	
	public AuthResponse() {}

	public AuthResponse(String jwttoken) {
		this.token = jwttoken;
	}

	public String getToken() {
		return this.token;
	}
}
