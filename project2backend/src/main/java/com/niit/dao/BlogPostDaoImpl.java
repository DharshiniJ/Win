package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.Notification;
import com.niit.model.User;


@Repository
@Transactional
public class BlogPostDaoImpl implements BlogPostDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void saveBlogPost(BlogPost blogPost) {
		Session session = sessionFactory.getCurrentSession();
		session.save(blogPost);

	}

	public List<BlogPost> getBlogs(int approved) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from BlogPost where approved=" + approved);
		return query.list();
	}

		public BlogPost getBlogById(int id) {
			Session session = sessionFactory.getCurrentSession();
			BlogPost blogPost = (BlogPost) session.get(BlogPost.class, id);
			return blogPost;
		}

		public void updateBlogPost(BlogPost blogPost, String rejectionReason) {
			Session session = sessionFactory.getCurrentSession();
			Notification notification = new Notification();
			notification.setBlogTitle(blogPost.getBlogTitle());
			notification.setUsername(blogPost.getPostedBy().getUsername());// author
																			// who
																			// posted
																			// the
																			// blog
			if (blogPost.isApproved()) {// true admin approves the blogpost [Approve
										// radio button is selected by admin]
				session.update(blogPost);// update blogpost set approved=1 where
											// id=?
				notification.setApprovalStatus("Approved");
				session.save(notification);// insert into notification values (...)
			} else {// false admin rejects the blogpost [Reject radio button is
					// selected by admin]
				System.out.println(rejectionReason);
				if (rejectionReason.equals(""))
					notification.setRejectionReason("Not Mentioned by Admin");
				else
					notification.setRejectionReason(rejectionReason);
				notification.setApprovalStatus("Rejected");
				session.save(notification);// insert into notification values (...)
				session.delete(blogPost);// delete from blogpost where id=?

			}
		}

		public void addComment(BlogComment blogComment) {
	        Session session=sessionFactory.getCurrentSession();
	        session.save(blogComment);//insert into blogcomment_s180133 ...
			
		}

	}


