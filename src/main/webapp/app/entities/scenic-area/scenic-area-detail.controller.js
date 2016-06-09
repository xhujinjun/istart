(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ScenicAreaDetailController', ScenicAreaDetailController);

    ScenicAreaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'ScenicArea', 'ScenicSpot'];

    function ScenicAreaDetailController($scope, $rootScope, $stateParams, DataUtils, entity, ScenicArea, ScenicSpot) {
        var vm = this;
        vm.scenicArea = entity;
        
        var unsubscribe = $rootScope.$on('istartApp:scenicAreaUpdate', function(event, result) {
            vm.scenicArea = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
    }
})();
