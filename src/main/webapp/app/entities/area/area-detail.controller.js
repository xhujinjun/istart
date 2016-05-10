(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('AreaDetailController', AreaDetailController);

    AreaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Area'];

    function AreaDetailController($scope, $rootScope, $stateParams, entity, Area) {
        var vm = this;
        vm.area = entity;
        vm.load = function (id) {
            Area.get({id: id}, function(result) {
                vm.area = result;
            });
        };
        var unsubscribe = $rootScope.$on('istartApp:areaUpdate', function(event, result) {
            vm.area = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
