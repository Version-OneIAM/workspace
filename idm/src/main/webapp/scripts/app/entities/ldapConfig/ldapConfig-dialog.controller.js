'use strict';

angular.module('idmApp').controller('LdapConfigDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LdapConfig',
        function($scope, $stateParams, $uibModalInstance, entity, LdapConfig) {

        $scope.ldapConfig = entity;
        $scope.load = function(id) {
            LdapConfig.get({id : id}, function(result) {
                $scope.ldapConfig = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('idmApp:ldapConfigUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.ldapConfig.id != null) {
                LdapConfig.update($scope.ldapConfig, onSaveSuccess, onSaveError);
            } else {
                LdapConfig.save($scope.ldapConfig, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForWhenmanaged = {};

        $scope.datePickerForWhenmanaged.status = {
            opened: false
        };

        $scope.datePickerForWhenmanagedOpen = function($event) {
            $scope.datePickerForWhenmanaged.status.opened = true;
        };
        $scope.datePickerForLastsynctime = {};

        $scope.datePickerForLastsynctime.status = {
            opened: false
        };

        $scope.datePickerForLastsynctimeOpen = function($event) {
            $scope.datePickerForLastsynctime.status.opened = true;
        };
}]);
