app.controller('FriendController', function($scope, $location, FriendService,$rootScope, $routeParams) {

	$scope.updatePendingRequest = function(pendingrequest, updatedstatus) {
		pendingrequest.status = updatedstatus
		FriendService.updatePendingRequest(pendingrequest).then(
				function(response) {
					getAllPendingRequests()
				}, function(response) {
					if (response) {
						if (response.status == 401)
							$location.path('/login')
					}
				})
	}
	function getSuggestedUsers() {
		FriendService.getSuggestedUsers().then(function(response) {
			$scope.suggestedusers = response.data
			// getMutualFriends($scope.suggestedusers)
		}, function(response) {
			if (response.status == 401)
				$location.path = "/login"
				
		})
	}

	function getAllPendingRequests() {
		FriendService.getAllPendingRequests().then(function(response) {
			$scope.pendingrequests = response.data
		}, function(response) {
			if (response.status == 401)
				$location.path('/login')
		})
	}

	function getListOfFriends() {
		FriendService.getListOfFriends().then(function(response) {
			$scope.friends = response.data
		}, function(response) {
			if (response.status == 401)
				$location.path('/login')
		})
	}
	$scope.sendFriendRequest = function(toId) {
		FriendService.sendFriendRequest(toId).then(function(response) {
			alert('Friend request has been sent')
			getSuggestedUsers();
			
		}, function(response) {
			if (response.status == 401)
				$location.path('/login')
			else
				console.log(response.status)
		})
	}
	$scope.getuserdetails=function(fromId){
		$scope.showDetails=true
		FriendService.getuserdetails(fromId).then(function(response){
			$scope.details=response.data
		},function(response){
			if(response.status==401){
				$scope.error=response.data
				$location.path('/login')
			}
		
		})
	}

	getSuggestedUsers();
	getAllPendingRequests();
	getListOfFriends();

})