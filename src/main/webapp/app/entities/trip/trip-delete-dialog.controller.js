(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TripDeleteController',TripDeleteController);

    TripDeleteController.$inject = ['$uibModalInstance', 'entity', 'Trip'];

    function TripDeleteController($uibModalInstance, entity, Trip) {
        var vm = this;
        vm.trip = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Trip.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
