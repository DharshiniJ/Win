/**
 * Friend Service
 */
app.factory('FriendService',function($http){
	var friendService={}
	
	var BASE_URL="http://localhost:8081/project2middleware"
		
		friendService.getSuggestedUsers=function(){
		return	$http.get(BASE_URL + "/suggestedusers")
		}
		
	friendService.sendFriendRequest=function(toId){
		return $http.post(BASE_URL +"/friendrequest/"+toId)
	}
	
	friendService.getAllPendingRequests=function(){
		return $http.get(BASE_URL + "/pendingrequests")
	}
	friendService.updatePendingRequest=function(pendingrequest){
		return $http.put(BASE_URL + "/updatependingrequest",pendingrequest)
	}
	friendService.getListOfFriends=function(){
		return $http.get(BASE_URL + "/getfriends")
	}
	friendService.getuserdetails=function(fromid){
		return $http.get(BASE_URL + "/getuserdetails/"+fromid)
	}
	
	
	
	
	

	return friendService;
})