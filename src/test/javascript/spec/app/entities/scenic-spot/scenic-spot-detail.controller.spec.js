'use strict';

describe('Controller Tests', function() {

    describe('ScenicSpot Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockScenicSpot;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockScenicSpot = jasmine.createSpy('MockScenicSpot');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ScenicSpot': MockScenicSpot
            };
            createController = function() {
                $injector.get('$controller')("ScenicSpotDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istartApp:scenicSpotUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
