(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('DicDetailController', DicDetailController);

    DicDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Dic', 'DicType'];

    function DicDetailController($scope, $rootScope, $stateParams, entity, Dic, DicType) {
        var vm = this;
        vm.dic = entity;
        
        var unsubscribe = $rootScope.$on('istartApp:dicUpdate', function(event, result) {
            vm.dic = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
