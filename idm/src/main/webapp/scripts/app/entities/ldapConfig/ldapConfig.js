'use strict';

angular.module('idmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ldapConfig', {
                parent: 'entity',
                url: '/ldapConfigs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'idmApp.ldapConfig.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ldapConfig/ldapConfigs.html',
                        controller: 'LdapConfigController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ldapConfig');
                        $translatePartialLoader.addPart('portTypesEnum');
                        $translatePartialLoader.addPart('fetchStatesEnum');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ldapConfig.detail', {
                parent: 'entity',
                url: '/ldapConfig/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'idmApp.ldapConfig.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ldapConfig/ldapConfig-detail.html',
                        controller: 'LdapConfigDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ldapConfig');
                        $translatePartialLoader.addPart('portTypesEnum');
                        $translatePartialLoader.addPart('fetchStatesEnum');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LdapConfig', function($stateParams, LdapConfig) {
                        return LdapConfig.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ldapConfig.new', {
                parent: 'ldapConfig',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ldapConfig/ldapConfig-dialog.html',
                        controller: 'LdapConfigDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ldapConfig', null, { reload: true });
                    }, function() {
                        $state.go('ldapConfig');
                    })
                }]
            })
            .state('ldapConfig.edit', {
                parent: 'ldapConfig',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ldapConfig/ldapConfig-dialog.html',
                        controller: 'LdapConfigDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LdapConfig', function(LdapConfig) {
                                return LdapConfig.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ldapConfig', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('ldapConfig.delete', {
                parent: 'ldapConfig',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ldapConfig/ldapConfig-delete-dialog.html',
                        controller: 'LdapConfigDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LdapConfig', function(LdapConfig) {
                                return LdapConfig.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ldapConfig', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
