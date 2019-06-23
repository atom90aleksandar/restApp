package com.rest.utils;

import com.rest.Post;
import com.rest.exceptions.ClientException;

public class PostUtils {

	public static void validatePost(Post post) throws ClientException {
		
		if(post.getId() == 0) {
			throw new ClientException("id field can not be None or empty");
		}
		if(post.getUserId() == 0) {
			throw new ClientException("userId field can not be None or empty");
		}
		if(null == post.getTitle()) {
			throw new ClientException("title field can not be null or empty");
		}
		if(post.getBody() == null) {
			throw new ClientException("body field can not be null or empty");
		}
	}

	public static Post validateUpdatedPost(Post updatedPost, Post existingPost) {
		
		if(updatedPost.getUserId() != 0) {
		  if(updatedPost.getUserId() != existingPost.getUserId()) {
			  existingPost.setUserId(updatedPost.getUserId());
		  }
		}
		if(null != updatedPost.getTitle()) {
		  if(updatedPost.getTitle() != existingPost.getTitle()) {
			  existingPost.setTitle(updatedPost.getTitle());
		  }
		}
		if(null != updatedPost.getBody()) {
		  if(updatedPost.getBody() != existingPost.getBody()) {
			  existingPost.setBody(updatedPost.getBody());
		  }
		}
		
		return existingPost;
		
	}
	
}
