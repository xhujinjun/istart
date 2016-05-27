(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('HotelDetailController', HotelDetailController);

    HotelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Hotel'];

    function HotelDetailController($scope, $rootScope, $stateParams, entity, Hotel) {
        var vm = this;
        vm.hotel = entity;
        
        var unsubscribe = $rootScope.$on('istartApp:hotelUpdate', function(event, result) {
            vm.dic = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
