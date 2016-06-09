(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ProductsDetailController', ProductsDetailController);

    ProductsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Products', 'Trip', 'Pictrues'];

    function ProductsDetailController($scope, $rootScope, $stateParams, entity, Products, Trip, Pictrues) {
        var vm = this;
        vm.products = entity;
        
        var unsubscribe = $rootScope.$on('istartApp:productsUpdate', function(event, result) {
            vm.products = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
