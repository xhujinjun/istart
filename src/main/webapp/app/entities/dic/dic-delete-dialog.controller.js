(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('DicDeleteController',DicDeleteController);

    DicDeleteController.$inject = ['$uibModalInstance', 'entity', 'Dic'];

    function DicDeleteController($uibModalInstance, entity, Dic) {
        var vm = this;
        vm.dic = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Dic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
