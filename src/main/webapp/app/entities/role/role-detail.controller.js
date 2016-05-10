(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('RoleDetailController', RoleDetailController);

    RoleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Role'];

    function RoleDetailController($scope, $rootScope, $stateParams, entity, Role) {
        var vm = this;
        vm.role = entity;
        vm.load = function (id) {
            Role.get({id: id}, function(result) {
                vm.role = result;
            });
        };
        var unsubscribe = $rootScope.$on('istartApp:roleUpdate', function(event, result) {
            vm.role = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
