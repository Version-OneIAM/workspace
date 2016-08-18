'use strict';

angular.module('idmApp')
    .factory('LdapConfig', function ($resource, DateUtils) {
        return $resource('api/ldapConfigs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.whenmanaged = DateUtils.convertLocaleDateFromServer(data.whenmanaged);
                    data.lastsynctime = DateUtils.convertLocaleDateFromServer(data.lastsynctime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.whenmanaged = DateUtils.convertLocaleDateToServer(data.whenmanaged);
                    data.lastsynctime = DateUtils.convertLocaleDateToServer(data.lastsynctime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.whenmanaged = DateUtils.convertLocaleDateToServer(data.whenmanaged);
                    data.lastsynctime = DateUtils.convertLocaleDateToServer(data.lastsynctime);
                    return angular.toJson(data);
                }
            }
        });
    });
