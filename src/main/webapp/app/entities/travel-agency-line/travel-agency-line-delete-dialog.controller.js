(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TravelAgencyLineDeleteController',TravelAgencyLineDeleteController);

    TravelAgencyLineDeleteController.$inject = ['$uibModalInstance', 'entity', 'TravelAgencyLine'];

    function TravelAgencyLineDeleteController($uibModalInstance, entity, TravelAgencyLine) {
        var vm = this;
        vm.travelAgencyLine = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            TravelAgencyLine.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
