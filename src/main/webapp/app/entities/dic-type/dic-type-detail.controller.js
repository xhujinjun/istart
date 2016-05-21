(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('DicTypeDetailController', DicTypeDetailController);

    DicTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'DicType'];

    function DicTypeDetailController($scope, $rootScope, $stateParams, entity, DicType) {
        var vm = this;
        vm.dicType = entity;
        
        var unsubscribe = $rootScope.$on('istartApp:dicTypeUpdate', function(event, result) {
            vm.dicType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
