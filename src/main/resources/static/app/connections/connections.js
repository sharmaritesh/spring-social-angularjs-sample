'use strict';

angular.module('ssa')
    .config(function ($stateProvider) {
        $stateProvider
            .state('connections', {
                parent: 'site',
                url: '/connections',
                data: {
                },
                views: {
                    'content@': {
                        templateUrl: 'app/connections/connections.html',
                        controller: 'ConnectionsController'
                    }
                }
            })
            .state('connections.facebook', {
                parent: 'connections',
                url: '/facebook',
                data: {
                },
                views: {
                    'content@': {
                        templateUrl: 'app/connections/facebook/facebookConnect.html',
                        controller: 'ConnectionsController'
                    }
                }
            }).state('connections.twitter', {
                parent: 'connections',
                url: '/twitter',
                data: {
                },
                views: {
                    'content@': {
                        templateUrl: 'app/connections/twitter/twitterConnect.html',
                        controller: 'ConnectionsController'
                    }
                }
            });
    });
