(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('DicTypeDeleteController',DicTypeDeleteController);

    DicTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'DicType'];

    function DicTypeDeleteController($uibModalInstance, entity, DicType) {
        var vm = this;
        vm.dicType = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            DicType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
