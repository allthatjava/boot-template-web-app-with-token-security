package brian.template.boot.web.app.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brian.template.boot.web.app.domain.AppPost;
import brian.template.boot.web.app.repository.AppPostRepository;

@Service
public class AppPostService {

	@Autowired
	private AppPostRepository repo;

	public List<AppPost> getAllPost() {
		return StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public AppPost getPost(int postId) {

		return repo.findByPostId(postId);
	}

	public AppPost addPost(AppPost post) {
		return repo.save(post);
	}

	public AppPost updatePost(AppPost post) {
		return repo.save(post);
	}
	
	public void deletePost(Integer postId) {
		repo.deleteById(postId);
	}
}
