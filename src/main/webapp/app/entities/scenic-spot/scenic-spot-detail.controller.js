(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ScenicSpotDetailController', ScenicSpotDetailController);

    ScenicSpotDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'ScenicSpot'];

    function ScenicSpotDetailController($scope, $rootScope, $stateParams, DataUtils, entity, ScenicSpot) {
        var vm = this;
        vm.scenicSpot = entity;
        
        var unsubscribe = $rootScope.$on('istartApp:scenicSpotUpdate', function(event, result) {
            vm.scenicSpot = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
    }
})();
