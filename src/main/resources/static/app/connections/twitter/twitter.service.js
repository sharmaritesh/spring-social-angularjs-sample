'use strict';

angular.module('ssa')
    .factory('Twitter', function ($resource) {
        return $resource('/api/connect/twitter', {}, {
        	'fetchProfile': { url: 'api/connect/twitter/profile', method: 'GET'},
        	'fetchTweets': { url: 'api/connect/twitter/tweets', method: 'GET'},
        	'postTweet': { url: 'api/connect/twitter/tweet', method: 'POST'},
        	'findFriends': { url: 'api/connect/twitter/friends', method: 'GET', isArray: true},
        	'findFollowers': { url: 'api/connect/twitter/followers', method: 'GET', isArray: true},
        	'trends': { url: 'api/connect/twitter/trends', method: 'GET'}
        });
    });


