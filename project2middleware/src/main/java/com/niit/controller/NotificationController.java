package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.NotificationDao;
import com.niit.model.ErrorClazz;
import com.niit.model.Notification;

@Controller
public class NotificationController {
	@Autowired
	private NotificationDao notificationDao;
	@RequestMapping(value="/getnotification/{viewed}")
	public ResponseEntity<?> getNotification(@PathVariable int viewed,HttpSession session){
		String username=(String)session.getAttribute("username");
		if(username==null) {
			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		List<Notification> notifications=notificationDao.getNotification(username, viewed);
		return new ResponseEntity<List<Notification>>(notifications,HttpStatus.OK);
		
	}
	@RequestMapping(value="/updatenotification/{notificationId}",method=RequestMethod.PUT)
	public ResponseEntity<?> updateNotification(@PathVariable int notificationId,HttpSession session){
	String username=(String)session.getAttribute("username");
	if(username==null) {
		ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
	}
	Notification notification=notificationDao.updateNotification(notificationId);
	return new ResponseEntity<Notification>(notification,HttpStatus.OK); 
}
}
