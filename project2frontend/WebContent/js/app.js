/**
 * Angular Js Module
 */
var app = angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/register',{
		templateUrl:'views/registrationform.html',
		controller:'UserController'
	})
	.when('/login',{
		templateUrl:'views/login.html',
		controller:'UserController'
	})
	.when('/editprofile',{
		templateUrl:'views/userprofile.html',
		controller:'UserController'
	})
	.when('/addjob',{
		templateUrl:'views/jobform.html',
		controller :'JobController'
	}).when('/alljobs',{
		templateUrl:'views/jobslist.html',
		controller :'JobController'
	})
	.when('/addblog',{
		templateUrl:'views/blogform.html', //V to Controller
		controller:'BlogPostController'
	})
	.when('/getblogs',{
		templateUrl:'views/blogslist.html',//Controller to V
		controller:'BlogPostController'
	})
	.when('/admin/getblog/:id',{
		templateUrl:'views/approvalform.html',
		controller:'BlogPostDetailsController'
	})
	.when('/getblog/:id',{
		templateUrl:'views/blogdetails.html',
		controller:'BlogPostDetailsController'
	})
	.when('/home',{
		templateUrl:'views/home.html',
		controller:'HomeController'
	})
	.when('/uploadpic',{
		templateUrl:'views/profilepicture.html'
			
	})
	.when('/jobdetail/:id',{
		templateUrl:'views/jobdetails.html',
		controller:'JobController'
	})
	.when('/suggestedusers', {
        templateUrl : 'views/suggestedusers.html',
        controller:'FriendController'
    })
    .when('/pendingrequests',{
		templateUrl:'views/pendingRequests.html',
		controller:'FriendController'
	})
	.when('/getfriends',{
		templateUrl:'views/listoffriends.html',
		controller:'FriendController'
	})
	.when('/chat',{
		templateUrl:'views/chat.html',
		controller:'SockController'
	})
	.otherwise({templateUrl:'views/home.html',controller:'HomeController'})
})
app.run(function($rootScope, $cookieStore, UserService, $location) {
	//alert("app.run got loaded.")
	if($rootScope.currentUser == undefined)
	{
		$rootScope.currentUser = $cookieStore.get('currentUser');
		console.log("Cookie: ");
		console.log($cookieStore.get('currentUser'));
	}
	else
	{
		console.log("Unable to set cookie");
	}
	
	$rootScope.logout = function() {
		
		UserService.logout().then(function(response) {
			delete $rootScope.currentUser;
			$cookieStore.remove('currentUser');
			$location.path("/login");
		}, function(response) {
			console.log(response.status);
			$location.path("/login");
		})
	}
})
