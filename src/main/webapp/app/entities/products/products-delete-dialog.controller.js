(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ProductsDeleteController',ProductsDeleteController);

    ProductsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Products'];

    function ProductsDeleteController($uibModalInstance, entity, Products) {
        var vm = this;
        vm.products = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Products.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
