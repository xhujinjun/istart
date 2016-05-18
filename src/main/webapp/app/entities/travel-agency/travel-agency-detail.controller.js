(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TravelAgencyDetailController', TravelAgencyDetailController);

    TravelAgencyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'TravelAgency'];

    function TravelAgencyDetailController($scope, $rootScope, $stateParams, entity, TravelAgency) {
        var vm = this;
        vm.travelAgency = entity;
        
        var unsubscribe = $rootScope.$on('istartApp:travelAgencyUpdate', function(event, result) {
            vm.travelAgency = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
