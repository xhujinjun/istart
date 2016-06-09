(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TripDetailController', TripDetailController);

    TripDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Trip'];

    function TripDetailController($scope, $rootScope, $stateParams, entity, Trip) {
        var vm = this;
        vm.trip = entity;
        
        var unsubscribe = $rootScope.$on('istartApp:tripUpdate', function(event, result) {
            vm.trip = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
