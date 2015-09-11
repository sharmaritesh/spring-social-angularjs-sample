'use strict';

angular.module('ssa')
    .config(function ($stateProvider) {
        $stateProvider
            .state('connections.facebook.manage', {
                parent: 'connections.facebook',
                url: '/manage',
                data: {
                },
                views: {
                    'content@': {
                        templateUrl: 'app/connections/facebook/facebookManager.html',
                        controller: 'FacebookConnectionController as fcc',
                    }
                }
            })
            .state('connections.facebook.manage.profile', {
            	parent: 'connections.facebook.manage',
                url: '/profile', 
                templateUrl: 'app/connections/facebook/profile.html',
                controller: 'FacebookProfileController',
                resolve: {
                	profileRs: function(Facebook) {
                		return Facebook.fetchProfile();
                	}
                }
            })
            .state('connections.facebook.manage.friends', {
            	parent: 'connections.facebook.manage',
                url: '/friends', 
                templateUrl: 'app/connections/facebook/friends.html',
                controller: 'FacebookFriendsController',
                resolve: {
                	friendRs: function(Facebook) {
                		return Facebook.getFriends();
                	}
                }
            })
            .state('connections.facebook.manage.albums', {
            	parent: 'connections.facebook.manage',
                url: '/albums', 
                templateUrl: 'app/connections/facebook/albums.html',
                controller: 'FacebookAlbumsController',
                resolve: {
                	albumsRs: function(Facebook) {
                		return Facebook.getAlbums();
                	}
                }
            });
    });
