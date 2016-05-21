'use strict';

describe('Controller Tests', function() {

    describe('Dic Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDic, MockDicType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDic = jasmine.createSpy('MockDic');
            MockDicType = jasmine.createSpy('MockDicType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Dic': MockDic,
                'DicType': MockDicType
            };
            createController = function() {
                $injector.get('$controller')("DicDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istartApp:dicUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
