'use strict';

angular.module('ssa').controller('TwitterConnectionController',
		function($scope, $state) {
			$scope.message = 'Loading ...';
			var init = function() {
				console.log('TwitterConnectionController initialized');
			}
			init();
		}).controller('TwitterProfileController',
				function($scope, $state, profileRs) {
			$scope.loadProfile = function() {
				if (profileRs != null) {
					profileRs.$promise.then(function success(response) {
						$scope.profile = response;
						$scope.message = '';
					}, function error(error) {
						$scope.message = 'Couldn\'t able to fetch profile :(';
						console.log(error);
					});
				}
			}
			
			var init = function() {
				console.log('TwitterProfileController initialized');
				$scope.loadProfile();
			}
			init();
		}).controller('TwitterTweetsController',
				function($scope, $state, tweetsRq, Twitter) {
			$scope.tweets = [];
			
			$scope.showTweets = function() {
				$scope.message = 'Fetching tweets ...';
				tweetsRq.$promise.then(function success(response) {
					$scope.tweets = response.tweets;
					$scope.message = '';
				}, function error(error) {
					$scope.message = 'Couldn\'t able to fetch tweets :(';
					console.log(error);
				});
			}
			
			$scope.postTweet = function() {
				$scope.message = 'Posting data ...';
				var payload = '{"text" :"' + $scope.tweet + '"}';
				Twitter.postTweet(payload).$promise.then(function () {
                    $scope.message = 'Tweeted !';
                    $scope.tweet = '';
                }).catch(function () {
                    $scope.message = 'Couldn\'t able to post tweet.';
                });
			}
			
			var init = function() {
				console.log('TwitterTweetsController initialized');
				$scope.showTweets();
			}
			init();
		}).controller('TwitterFriendsController',
				function($scope, $state, Twitter) {
			$scope.message = 'Fetching friends ...';
			$scope.friends = [];
			
			var fetchFriends = function() {
				Twitter.findFriends().$promise.then(function success(response) {
					$scope.friends = response;
					$scope.message = '';
				}, function error(error) {
					$scope.message = 'Couldn\'t able to fetch friends list :(';
					console.log(error);
				});
			}
			
			var init = function() {
				console.log('TwitterFriendsController initialized');
				fetchFriends();
			}
			init();
		}).controller('TwitterFollowersController',
				function($scope, $state, Twitter) {
			$scope.message = 'Fetching followers ...';
			$scope.followers = [];
			
			var fetchFollowers = function() {
				Twitter.findFollowers().$promise.then(function success(response) {
					$scope.followers = response;
					$scope.message = '';
				}, function error(error) {
					$scope.message = 'Couldn\'t able to fetch followers list :(';
					console.log(error);
				});
			}
			
			var init = function() {
				console.log('TwitterFollowersController initialized');
				fetchFollowers();
			}
			init();
		}).controller('TwitterTrendsController',
				function($scope, $state, trendsRs) {
			$scope.message = 'Fetching trends ...';
			$scope.trends = [];
			
			var fetchFollowers = function() {
				trendsRs.$promise.then(function success(response) {
					$scope.trends = response.trends;
					$scope.message = '';
				}, function error(error) {
					$scope.message = 'Couldn\'t able to fetch trends :(';
					console.log(error);
				});
			}
			
			var init = function() {
				console.log('TwitterTrendsController initialized');
				fetchFollowers();
			}
			init();
		});
