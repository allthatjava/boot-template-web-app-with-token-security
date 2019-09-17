package brian.template.boot.web.app.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brian.template.boot.web.app.domain.AppUser;
import brian.template.boot.web.app.repository.AppUserRepository;

@Service
public class AppUserService {

	@Autowired
	private AppUserRepository repo;

	public List<AppUser> getAllAppUsers() {
		return StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public AppUser getAppUser(String userId) {
		return repo.findByUserId(userId);
	}

	public AppUser addAppUser(AppUser appUser) {
		return repo.save(appUser);
	}

	public AppUser updateAppUser(AppUser appUser) {
		return repo.save(appUser);
	}

	public void deleteAppUser(String userId) {
		repo.deleteById(userId);
	}
}
