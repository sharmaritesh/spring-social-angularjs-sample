'use strict';

angular.module('ssa')
    .config(function ($stateProvider) {
        $stateProvider
            .state('connections.twitter.manage', {
                parent: 'connections.twitter',
                url: '/manage',
                data: {
                },
                views: {
                    'content@': {
                        templateUrl: 'app/connections/twitter/twitterManager.html',
                        controller: 'TwitterConnectionController as tcc',
                    }
                }
            })
            .state('connections.twitter.manage.profile', {
            	parent: 'connections.twitter.manage',
                url: '/profile', 
                templateUrl: 'app/connections/twitter/profile.html',
                controller: 'TwitterProfileController',
                resolve: {
                	profileRs: function(Twitter) {
                		return Twitter.fetchProfile();
                	}
                }
            })
            .state('connections.twitter.manage.tweets', {
            	parent: 'connections.twitter.manage',
                url: '/tweets', 
                templateUrl: 'app/connections/twitter/tweets.html',
                controller: 'TwitterTweetsController',
                resolve: {
                	tweetsRq: function(Twitter) {
                		return Twitter.fetchTweets();
                	}
                }
            })
            .state('connections.twitter.manage.friends', {
            	parent: 'connections.twitter.manage',
                url: '/friends', 
                templateUrl: 'app/connections/twitter/friends.html',
                controller: 'TwitterFriendsController'
            })
            .state('connections.twitter.manage.followers', {
            	parent: 'connections.twitter.manage',
                url: '/followers', 
                templateUrl: 'app/connections/twitter/followers.html',
                controller: 'TwitterFollowersController'
            })
            .state('connections.twitter.manage.trends', {
            	parent: 'connections.twitter.manage',
                url: '/trends', 
                templateUrl: 'app/connections/twitter/trends.html',
                controller: 'TwitterTrendsController',
                resolve: {
                	trendsRs: function(Twitter) {
                		return Twitter.trends();
                	}
                }
            });
    });
