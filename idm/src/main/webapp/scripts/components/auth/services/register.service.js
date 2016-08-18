'use strict';

angular.module('idmApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


