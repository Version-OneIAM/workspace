'use strict';

describe('Controller Tests', function() {

    describe('LdapConfig Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLdapConfig;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLdapConfig = jasmine.createSpy('MockLdapConfig');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LdapConfig': MockLdapConfig
            };
            createController = function() {
                $injector.get('$controller')("LdapConfigDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'idmApp:ldapConfigUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
