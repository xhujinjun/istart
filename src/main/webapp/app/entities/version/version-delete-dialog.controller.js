(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('VersionDeleteController',VersionDeleteController);

    VersionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Version'];

    function VersionDeleteController($uibModalInstance, entity, Version) {
        var vm = this;
        vm.version = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Version.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
