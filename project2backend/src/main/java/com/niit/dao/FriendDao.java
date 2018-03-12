package com.niit.dao;

import java.util.List;

import com.niit.model.Friend;
import com.niit.model.User;

public interface FriendDao 
{
	List<User> suggestedUserList(String username);

	void friendRequest(Friend friend);// insert into friend

	List<Friend> pendingRequests(String username);

	void updatePendingRequest(Friend friend);

	List<User> listOfFriends(String username);
	
}