(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('SysresDeleteController',SysresDeleteController);

    SysresDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sysres'];

    function SysresDeleteController($uibModalInstance, entity, Sysres) {
        var vm = this;
        vm.sysres = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Sysres.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
