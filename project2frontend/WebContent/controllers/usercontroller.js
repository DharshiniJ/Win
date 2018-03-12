/**
 * UserController
 */
app.controller('UserController',function($scope,UserService,$location,$rootScope,$cookieStore){

	if($rootScope.currentUser!=undefined){
		UserService.getUser().then(function(response){
			$scope.user=response.data; 
		},function(response){
			delete $rootscope.curretUser;
			$cookieStore.remove('currentUser')
			if(response.status==401){
				$location.path("/login");
			}
			
	})
	}
	
	$scope.registerUser=function(){//2
		console.log($scope.user)
		UserService.registerUser($scope.user)//3
		.then(function(response){
			$scope.msg="Register successfully...Please Login"
			$location.path("/login");
		},function(response){
			console.log(response.data)
			console.log(response.status)
			$scope.error=response.data  //ErrorClazz object in JSON
		})//9
	}
	
	$scope.login=function(){
		UserService.login($scope.user).then(function(response){//200,User
			$rootScope.currentUser=response.data
			$cookieStore.put('currentUser',response.data)
			$location.path("/home");
		},function(response){//401,500....
			if(response.status==401){
				$scope.error=response.data//errorClazz in JSON fmt
				$location.path("/login");
			}
		})
	}
	$scope.editUserProfile=function(){
		UserService.editUserProfile($scope.user).then(function(response){
			alert('Updated Successfully')
			$location.path("/home");
		},function(response){
			if(response.status==401){
				$location.path("/login");
				}
			if(response.status==500){
				$scope.error=response.data
				$location.path("/editprofile");
			}
		})
	}
		
	})
 















