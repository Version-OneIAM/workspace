'use strict';

angular.module('idmApp')
	.controller('LdapConfigDeleteController', function($scope, $uibModalInstance, entity, LdapConfig) {

        $scope.ldapConfig = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LdapConfig.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
