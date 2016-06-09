'use strict';

describe('Controller Tests', function() {

    describe('Pictrues Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPictrues;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPictrues = jasmine.createSpy('MockPictrues');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Pictrues': MockPictrues
            };
            createController = function() {
                $injector.get('$controller')("PictruesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istartApp:pictruesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
