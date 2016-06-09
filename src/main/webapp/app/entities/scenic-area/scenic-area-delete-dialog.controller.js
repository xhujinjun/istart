(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ScenicAreaDeleteController',ScenicAreaDeleteController);

    ScenicAreaDeleteController.$inject = ['$uibModalInstance', 'entity', 'ScenicArea'];

    function ScenicAreaDeleteController($uibModalInstance, entity, ScenicArea) {
        var vm = this;
        vm.scenicArea = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ScenicArea.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
