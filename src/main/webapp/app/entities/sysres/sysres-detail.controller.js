(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('SysresDetailController', SysresDetailController);

    SysresDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Sysres'];

    function SysresDetailController($scope, $rootScope, $stateParams, entity, Sysres) {
        var vm = this;
        vm.sysres = entity;
        vm.load = function (id) {
            Sysres.get({id: id}, function(result) {
                vm.sysres = result;
            });
        };
        var unsubscribe = $rootScope.$on('istartApp:sysresUpdate', function(event, result) {
            vm.sysres = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
