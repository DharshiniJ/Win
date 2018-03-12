/**
 * BlogPostDetailController
 */
app.controller('BlogPostDetailsController',function($scope,$location,$routeParams,BlogService){
	var id=$routeParams.id
	$scope.isRejected=false;
	$scope.showComment=false;
	
	//select * from blogpost_s180133 where id=?
	if(id != undefined){
			BlogService.getBlogPost(id).then(function(response){
		$scope.blogPost=response.data
	},function(response){
		if(response.status==401){
			$location.path('/login')
		}
	})
	}
	//select * from blogpostlikes_s180133 where blogpost_id=? and user_username=?
	if(id != undefined){
	BlogService.userLikes(id).then(function(response){
		if(response.data=='')//user has not yet liked the blogpost
			$scope.liked=false
			else
				$scope.liked=true//user has liked the blogpost already
			alert($scope.liked)
	},function(response){
		if(response.status==401){
			$location.path('/login')
		}
	})
	}
	
	$scope.showRejectionTxt=function(val){
		$scope.isRejected=val
	}
	
	$scope.updateBlogPost=function(){
		BlogService.updateBlogPost($scope.blogPost,$scope.rejectionReason).then(function(response){
          $location.path('/getblogs')			
		},function(response){
			if(response.status==401){
				$location.path('/login')
			}
			if(response.status==500){
				alert(response.data)
				$scope.error=response.data
			}
		})
	}

	
	$scope.updateLikes=function(){
		BlogService.updateLikes($scope.blogPost).then(function(response){
			$scope.blogPost=response.data;
			$scope.liked=!$scope.liked;
		},function(response){
			if(response.status==401){
				$location.path('/login')
			}
		})
	}
	
	$scope.addComment=function(){
		if($scope.commentText==undefined){
			alert('Please enter comment')
		}
		else
		BlogService.addComment($scope.commentText,id).then(function(response){
			alert(response.status)
			$scope.commentText=''
			$scope.blogPost=response.data //blogPost with lists of blogcomments
		},function(response){
			if(response.status==401){
				$location.path('/login')
			}
			if(response.status==500){
				$scope.error=response.data
			}
		})	
	}
	
	$scope.showComments=function(){
		alert('show comments')
		$scope.showComment=!$scope.showComment
	}	

})
