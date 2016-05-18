(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TravelAgencyLineDetailController', TravelAgencyLineDetailController);

    TravelAgencyLineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'TravelAgencyLine'];

    function TravelAgencyLineDetailController($scope, $rootScope, $stateParams, entity, TravelAgencyLine) {
        var vm = this;
        vm.travelAgencyLine = entity;
        
        var unsubscribe = $rootScope.$on('istartApp:travelAgencyLineUpdate', function(event, result) {
            vm.travelAgencyLine = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
