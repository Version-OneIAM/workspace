'use strict';

angular.module('idmApp')
    .controller('LdapConfigController', function ($scope, $state, LdapConfig) {

        $scope.ldapConfigs = [];
        $scope.loadAll = function() {
            LdapConfig.query(function(result) {
               $scope.ldapConfigs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.ldapConfig = {
                name: null,
                domainname: null,
                netbiosdomainname: null,
                whenmanaged: null,
                lastsynctime: null,
                isdefault: null,
                isdeleted: null,
                domainsid: null,
                ipaddress: null,
                port: null,
                issecure: null,
                userbasedn: null,
                groupbasedn: null,
                portype: null,
                fetchstatus: null,
                id: null
            };
        };
    });
