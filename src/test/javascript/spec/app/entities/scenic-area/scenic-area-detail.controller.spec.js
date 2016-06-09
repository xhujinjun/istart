'use strict';

describe('Controller Tests', function() {

    describe('ScenicArea Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockScenicArea, MockScenicSpot;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockScenicArea = jasmine.createSpy('MockScenicArea');
            MockScenicSpot = jasmine.createSpy('MockScenicSpot');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ScenicArea': MockScenicArea,
                'ScenicSpot': MockScenicSpot
            };
            createController = function() {
                $injector.get('$controller')("ScenicAreaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istartApp:scenicAreaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
