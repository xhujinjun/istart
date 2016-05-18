(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TravelAgencyDeleteController',TravelAgencyDeleteController);

    TravelAgencyDeleteController.$inject = ['$uibModalInstance', 'entity', 'TravelAgency'];

    function TravelAgencyDeleteController($uibModalInstance, entity, TravelAgency) {
        var vm = this;
        vm.travelAgency = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            TravelAgency.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
