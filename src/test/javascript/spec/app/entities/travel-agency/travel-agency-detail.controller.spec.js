'use strict';

describe('Controller Tests', function() {

    describe('TravelAgency Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTravelAgency;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTravelAgency = jasmine.createSpy('MockTravelAgency');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TravelAgency': MockTravelAgency
            };
            createController = function() {
                $injector.get('$controller')("TravelAgencyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istartApp:travelAgencyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
