(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('VersionDetailController', VersionDetailController);

    VersionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Version'];

    function VersionDetailController($scope, $rootScope, $stateParams, entity, Version) {
        var vm = this;
        vm.version = entity;
        vm.load = function (id) {
            Version.get({id: id}, function(result) {
                vm.version = result;
            });
        };
        var unsubscribe = $rootScope.$on('istartApp:versionUpdate', function(event, result) {
            vm.version = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
