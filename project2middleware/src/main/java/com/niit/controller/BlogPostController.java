package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.niit.dao.BlogPostDao;
import com.niit.dao.BlogPostLikesDao;
import com.niit.dao.UserDao;
import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.ErrorClazz;
import com.niit.model.User;

@Controller
public class BlogPostController {
	@Autowired
	private BlogPostDao blogPostDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BlogPostLikesDao blogPostLikesDao;
	@RequestMapping(value = "/saveblog", method = RequestMethod.POST)
	public ResponseEntity<?> saveBlogPost(@RequestBody BlogPost blogPost, HttpSession session) {
		String username = (String) session.getAttribute("username");
		if (username == null) {
			ErrorClazz error = new ErrorClazz(5, "Unauthorized access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);// 401
		}
		User user = userDao.getUserByUsername(username);
		blogPost.setPostedOn(new Date());
		blogPost.setPostedBy(user);
		try {
			blogPostDao.saveBlogPost(blogPost);
		} catch (Exception e) {
			ErrorClazz error = new ErrorClazz(6, "Unable to insert blog details " + e.getMessage());
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.INTERNAL_SERVER_ERROR);// 500
		}
		return new ResponseEntity<BlogPost>(blogPost, HttpStatus.OK);
	}

	@RequestMapping(value="/getblogs/{approved}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogs(@PathVariable int approved,HttpSession session){
		String username=(String)session.getAttribute("username");
		if(username==null){
			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);//401
		}
		if(approved==0){
			User user=userDao.getUserByUsername(username);
		if(!user.getRole().equals("ADMIN")){
			ErrorClazz error=new ErrorClazz(7,"Access Denied");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		}
		List<BlogPost> blogPosts=blogPostDao.getBlogs(approved);
		return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
		
	}
	@RequestMapping(value="/getblog/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogPost(@PathVariable int id){

		BlogPost blogPost=blogPostDao.getBlogById(id);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	@RequestMapping(value="/updateapprovalstatus",method=RequestMethod.PUT)
	public ResponseEntity<?> updateApprovalStatus(@RequestBody BlogPost blogPost,
			          @RequestParam(required=false) String rejectionReason,HttpSession session){
		String username=(String)session.getAttribute("username");
		if(username==null){
			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);//401
		}
		try{
			
			System.out.println(blogPost);
		blogPostDao.updateBlogPost(blogPost,rejectionReason);
		}catch(Exception e){
			ErrorClazz error=new ErrorClazz(7,"Unable to update blogpost approval status "+e.getMessage());
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value="/userLikes/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> userLikes(@PathVariable int id,HttpSession session){
		String username=(String)session.getAttribute("username");
		if(username==null){
			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);//401
		}
		User user=userDao.getUserByUsername(username);
		BlogPost blogPost=blogPostDao.getBlogById(id);
		
		BlogPostLikes blogPostLikes=blogPostLikesDao.userLikes(blogPost, user);
		return new ResponseEntity<BlogPostLikes>(blogPostLikes,HttpStatus.OK);
	}
	@RequestMapping(value="/updatelikes",method=RequestMethod.PUT)
	public ResponseEntity<?> updateLikes(@RequestBody BlogPost blogPost,HttpSession session){
		String username=(String)session.getAttribute("username");
		if(username==null){
			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);//401
		}
		User user=userDao.getUserByUsername(username);
		BlogPost updatedBlogPost=blogPostLikesDao.updateLikes(blogPost, user);
		return new ResponseEntity<BlogPost>(updatedBlogPost,HttpStatus.OK);
	}
	@RequestMapping(value="/addcomment",method=RequestMethod.POST)
	
	public ResponseEntity<?> addBlogComment(@RequestParam String commentText , @RequestParam int id
			,HttpSession session){
		String username=(String)session.getAttribute("username");
		if(username==null){
			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);//401
		}
		User commentedBy=userDao.getUserByUsername(username);
		
		BlogComment blogComment=new BlogComment();
		
		blogComment.setCommentText(commentText);
		blogComment.setCommentedBy(commentedBy);
		BlogPost blogPost=blogPostDao.getBlogById(id);
		blogComment.setBlogPost(blogPost);
		blogComment.setCommentedOn(new Date());
		
		
		try{
		blogPostDao.addComment(blogComment);
		}catch(Exception e){
			ErrorClazz error=new ErrorClazz(7,"Unable to post comments " + e.getMessage());
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);//500
		}
		 blogPost=blogPostDao.getBlogById(id);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
		
	}
}

