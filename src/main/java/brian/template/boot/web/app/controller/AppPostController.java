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

import brian.template.boot.web.app.domain.AppPost;
import brian.template.boot.web.app.exception.CreationException;
import brian.template.boot.web.app.exception.NotFoundException;
import brian.template.boot.web.app.service.AppPostService;

@RestController
public class AppPostController {

	private AppPostService service;

	@Autowired
	public AppPostController(AppPostService postService) {
		this.service = postService;
	}

	/**
	 * Returns all posts
	 * 
	 * @return
	 */
	@GetMapping("/posts")
	public ResponseEntity<List<AppPost>> getPosts() {
		return new ResponseEntity<>(service.getAllPost(), HttpStatus.OK);
	}

	/**
	 * Return the post with given id
	 * 
	 * @param postId
	 * @return
	 */
	@GetMapping("/post/{post_id}")
	public ResponseEntity<AppPost> getPost(@PathVariable("post_id") Integer postId) {
		AppPost p = service.getPost(postId);

		if (p == null)
			throw new NotFoundException("Post not found with post id : " + postId);

		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	/**
	 * Insert new Post
	 * 
	 * @param post
	 * @return
	 */
	@PostMapping("/post")
	public ResponseEntity<AppPost> addPost(@RequestBody AppPost post) {

		if (null == post || null == post.getSubject() || null == post.getContent())
			throw new IllegalArgumentException("Subject or Content cannot be null");

		String userId = post.getUserId();

		if (null == userId)
			throw new NotFoundException("User ID [%s] was not found", userId);

		post = service.addPost(post);

		if (null == post.getPostId())
			throw new CreationException("Error occurred while adding Post");

		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PutMapping("/post")
	public ResponseEntity<AppPost> updatePost(@RequestBody AppPost post) {
		AppPost updatedPost = service.updatePost(post);

		return new ResponseEntity<>(updatedPost, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("/post/{post_id}")
	public ResponseEntity deletePost(@PathVariable("post_id") Integer postId) {

		service.deletePost(postId);

		return new ResponseEntity(HttpStatus.OK);
	}
}
