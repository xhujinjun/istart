(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('AreaDeleteController',AreaDeleteController);

    AreaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Area'];

    function AreaDeleteController($uibModalInstance, entity, Area) {
        var vm = this;
        vm.area = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Area.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
