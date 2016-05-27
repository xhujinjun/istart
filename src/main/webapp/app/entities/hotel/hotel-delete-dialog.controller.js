(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('HotelDeleteController',HotelDeleteController);

    HotelDeleteController.$inject = ['$uibModalInstance', 'entity', 'Hotel'];

    function HotelDeleteController($uibModalInstance, entity, Hotel) {
        var vm = this;
        vm.dic = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Hotel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
