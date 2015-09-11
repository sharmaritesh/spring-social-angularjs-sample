'use strict';

angular.module('ssa')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                },
                views: {
                    'content@': {
                        templateUrl: 'app/main/main.html',
                        controller: 'MainController'
                    }
                }
            });
    });
