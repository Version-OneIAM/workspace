'use strict';

angular.module('idmApp')
    .controller('HealthModalController', function($scope, $uibModalInstance, currentHealth, baseName, subSystemName) {

        $scope.currentHealth = currentHealth;
        $scope.baseName = baseName, $scope.subSystemName = subSystemName;

        $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
        };
    });
