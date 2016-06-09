'use strict';

describe('Controller Tests', function() {

    describe('Products Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProducts, MockTrip, MockPictrues;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProducts = jasmine.createSpy('MockProducts');
            MockTrip = jasmine.createSpy('MockTrip');
            MockPictrues = jasmine.createSpy('MockPictrues');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Products': MockProducts,
                'Trip': MockTrip,
                'Pictrues': MockPictrues
            };
            createController = function() {
                $injector.get('$controller')("ProductsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istartApp:productsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
