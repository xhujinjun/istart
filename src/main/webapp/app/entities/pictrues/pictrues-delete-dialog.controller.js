(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('PictruesDeleteController',PictruesDeleteController);

    PictruesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pictrues'];

    function PictruesDeleteController($uibModalInstance, entity, Pictrues) {
        var vm = this;
        vm.pictrues = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Pictrues.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
