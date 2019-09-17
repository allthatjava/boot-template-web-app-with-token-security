package brian.template.boot.web.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import brian.template.boot.web.app.domain.AppUser;
import brian.template.boot.web.app.exception.CreationException;
import brian.template.boot.web.app.exception.NotFoundException;
import brian.template.boot.web.app.service.AppUserService;

@RestController
public class AppUserController {

	private AppUserService service;

	@Autowired
	public AppUserController(AppUserService service) {
		this.service = service;
	}

	/**
	 * Returns all app users
	 * 
	 * @return
	 */
	@GetMapping(path = "/appUsers")
	public ResponseEntity<List<AppUser>> getAppUsers() {
		return new ResponseEntity<>(service.getAllAppUsers(), HttpStatus.OK);
	}

	/**
	 * Return the appUser with given id
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping(path = "/appUsers/{user_id}")
	public ResponseEntity<AppUser> getAppUser(@PathVariable("user_id") String userId) {
		AppUser p = service.getAppUser(userId);

		if (p == null)
			throw new NotFoundException("AppUser not found with user id : " + userId);

		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	/**
	 * Insert new AppUser
	 * 
	 * @param appUser
	 * @return
	 */
	@PostMapping(path = "/appUser")
	public ResponseEntity<AppUser> addAppUser(@RequestBody AppUser appUser) {

		if (null == appUser || null == appUser.getName())
			throw new IllegalArgumentException("Name cannot be null");

		String userId = appUser.getUserId();

		if (null == userId)
			throw new NotFoundException("User ID [%s] was not found", userId);

		appUser = service.addAppUser(appUser);

		if (null == appUser.getName())
			throw new CreationException("Error occurred while adding AppUser");

		return new ResponseEntity<>(appUser, HttpStatus.OK);
	}

	@PutMapping("/appUser")
	public ResponseEntity<AppUser> updateAppUser(@RequestBody AppUser appUser) {
		AppUser updatedAppUser = service.updateAppUser(appUser);

		return new ResponseEntity<>(updatedAppUser, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("/appUser/{user_id}")
	public ResponseEntity deleteAppUser(@PathVariable("user_Id") String userId) {

		service.deleteAppUser(userId);

		return new ResponseEntity(HttpStatus.OK);
	}
}
