'use strict';

angular.module('idmApp')
    .controller('LdapConfigDetailController', function ($scope, $rootScope, $stateParams, entity, LdapConfig) {
        $scope.ldapConfig = entity;
        $scope.load = function (id) {
            LdapConfig.get({id: id}, function(result) {
                $scope.ldapConfig = result;
            });
        };
        var unsubscribe = $rootScope.$on('idmApp:ldapConfigUpdate', function(event, result) {
            $scope.ldapConfig = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
