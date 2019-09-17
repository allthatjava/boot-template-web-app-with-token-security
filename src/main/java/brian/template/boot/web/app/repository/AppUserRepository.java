package brian.template.boot.web.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import brian.template.boot.web.app.domain.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, String>{

	public AppUser findByUserId(String userId);
}
