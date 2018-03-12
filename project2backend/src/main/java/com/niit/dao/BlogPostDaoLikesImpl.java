package com.niit.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.User;

@Repository
@Transactional
public class BlogPostDaoLikesImpl implements BlogPostLikesDao {
	@Autowired
private SessionFactory sessionFactory;
	public BlogPostLikes userLikes(BlogPost blogPost, User user) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPostLikes where blogPost.id=? and user.username=? ");
		System.out.println("BlogPost id  " + blogPost.getId());
		System.out.println("Username " + user.getUsername());
		query.setInteger(0, blogPost.getId());
		query.setString(1, user.getUsername());
		BlogPostLikes blogPostLikes=(BlogPostLikes)query.uniqueResult();
		System.out.println(blogPostLikes);
		return blogPostLikes;
		
	}

	public BlogPost updateLikes(BlogPost blogPost, User user) {
		Session session=sessionFactory.getCurrentSession();
		BlogPostLikes blogPostLikes=userLikes(blogPost,user);
		if(blogPostLikes==null){ 
			BlogPostLikes insertLikes=new BlogPostLikes();
			insertLikes.setBlogPost(blogPost);
			insertLikes.setUser(user);
			session.save(insertLikes); 
			blogPost.setLikes(blogPost.getLikes() + 1);
			session.update(blogPost);
		}
		else{//unlike
			session.delete(blogPostLikes); 
			blogPost.setLikes(blogPost.getLikes()-1); 
			session.merge(blogPost); 	}
		return blogPost;
	}

}


