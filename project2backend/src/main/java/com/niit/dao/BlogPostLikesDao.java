package com.niit.dao;

import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.User;

public interface BlogPostLikesDao {
	
BlogPostLikes userLikes(BlogPost blogPost,User user);
BlogPost updateLikes(BlogPost blogPost, User user);
}

