package com.educandoweb.workshopmongo.resources;
 
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.workshopmongo.domain.Post;
import com.educandoweb.workshopmongo.resources.util.URL;
import com.educandoweb.workshopmongo.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {
	
	@Autowired
	private PostService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post post = service.findById(id);
		return ResponseEntity.ok().body(post);
	}
	
	@GetMapping("/titlesearch")
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(defaultValue="") String text) {
		text = URL.decodeParam(text);
		List<Post> list = service.findByTitle(text);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/fullsearch")
	public ResponseEntity<List<Post>> fullSearch(@RequestParam(defaultValue="") String text,
			@RequestParam(defaultValue="") String minDate,
			@RequestParam(defaultValue="") String maxDate) {
		text = URL.decodeParam(text);
		
		LocalDate min = URL.convertDate(minDate, LocalDate.of(2000, 1, 1));
		LocalDate max = URL.convertDate(maxDate, LocalDate.now());

		List<Post> list = service.fullSearch(text, min, max);
		return ResponseEntity.ok().body(list);
	}
}
