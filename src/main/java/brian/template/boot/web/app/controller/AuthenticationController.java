package brian.template.boot.web.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import brian.template.boot.web.app.config.auth.JwtTokenUtil;
import brian.template.boot.web.app.config.auth.JwtUserDetailsService;
import brian.template.boot.web.app.domain.AuthRequest;
import brian.template.boot.web.app.domain.AuthResponse;

@RestController
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception{
	
		// test credentials
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new Exception("Bad Credentials");
		} catch (AuthenticationException e) {
			throw new Exception("Other Credentials error");
		}
		
		// If reached here, credential test has been passed
		final UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getUsername());
		
		// Generate token with user name
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		// Return 
		return ResponseEntity.ok(new AuthResponse(token));
	}
}
