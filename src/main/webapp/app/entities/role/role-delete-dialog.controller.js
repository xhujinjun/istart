(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('RoleDeleteController',RoleDeleteController);

    RoleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Role'];

    function RoleDeleteController($uibModalInstance, entity, Role) {
        var vm = this;
        vm.role = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Role.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
