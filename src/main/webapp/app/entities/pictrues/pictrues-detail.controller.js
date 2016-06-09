(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('PictruesDetailController', PictruesDetailController);

    PictruesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Pictrues'];

    function PictruesDetailController($scope, $rootScope, $stateParams, entity, Pictrues) {
        var vm = this;
        vm.pictrues = entity;
        
        var unsubscribe = $rootScope.$on('istartApp:pictruesUpdate', function(event, result) {
            vm.pictrues = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
