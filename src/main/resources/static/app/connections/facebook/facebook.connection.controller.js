'use strict';

angular.module('ssa').controller('FacebookConnectionController',
		function($scope, $state) {
			$scope.message = 'Loading ...';
			var init = function() {
				console.log('FacebookConnectionController initialized');
			}
			init();
		}).controller('FacebookProfileController',
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
				console.log('FacebookProfileController initialized');
				$scope.loadProfile();
			}
			init();
		})
		.controller('FacebookFriendsController',
				function($scope, $state, friendRs) {
			$scope.message = 'Fetching your friends listing...';
			$scope.friends = [];
			var getFriends = function() {
				if (friendRs != null) {
					friendRs.$promise.then(function success(response) {
						angular.forEach(response, function(friend, key) {
							friend.dpUrl = 'http://graph.facebook.com/' + friend.id + '/picture';
						});
						$scope.friends = response;
						$scope.message = '';
					}, function error(error) {
						$scope.message = 'Couldn\'t able to fetch friends :(';
						console.log(error);
					});
				}
			}
			var init = function() {
				console.log('FacebookFriendsController initialized');
				getFriends();
			}
			init();
		})
		.controller('FacebookAlbumsController',
				function($scope, $state, albumsRs, Facebook) {
			$scope.message = 'Fetching your albums ...';
			$scope.albums = [];
			
			$scope.loadAlbum = function(albumId) {
				console.log('Fetching album content with Id : ' + albumId);
				
				Facebook.fetchPhotosInAlbum({id: albumId}).$promise.then(function success(response) {
					$scope.photos = response;
					$scope.message = '';
				}, function error(error) {
					$scope.message = 'Couldn\'t able to fetch friends :(';
					console.log(error);
				});
			}
			var getAlbums = function() {
				if (albumsRs != null) {
					albumsRs.$promise.then(function success(response) {
						$scope.albums = response;
						$scope.message = '';
					}, function error(error) {
						$scope.message = 'Couldn\'t able to fetch albums :(';
						console.log(error);
					});
				}
			}
			var init = function() {
				console.log('FacebookAlbumsController initialized');
				getAlbums();
			}
			init();
		});
