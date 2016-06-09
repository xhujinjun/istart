(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ScenicSpotDeleteController',ScenicSpotDeleteController);

    ScenicSpotDeleteController.$inject = ['$uibModalInstance', 'entity', 'ScenicSpot'];

    function ScenicSpotDeleteController($uibModalInstance, entity, ScenicSpot) {
        var vm = this;
        vm.scenicSpot = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ScenicSpot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
