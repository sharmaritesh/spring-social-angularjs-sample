'use strict';

angular.module('ssa')
    .factory('Connections', function ($resource) {
        return $resource('api/connect', {}, {
        });
    });


