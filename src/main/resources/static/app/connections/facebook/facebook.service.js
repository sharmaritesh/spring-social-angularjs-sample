'use strict';

angular.module('ssa')
    .factory('Facebook', function ($resource) {
        return $resource('/api/connect/facebook', {}, {
        	'fetchProfile': { url: 'api/connect/facebook/profile', method: 'GET'},
        	'getFriends': { url: 'api/connect/facebook/friends', method: 'GET', isArray: true},
        	'getAlbums': { url: 'api/connect/facebook/albums', method: 'GET', isArray: true},
        	'fetchPhotosInAlbum': { url: 'api/connect/facebook/album/:id', method: 'GET', isArray: true},
        });
    });


