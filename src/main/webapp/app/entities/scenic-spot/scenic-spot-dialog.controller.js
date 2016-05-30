(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ScenicSpotDialogController', ScenicSpotDialogController);

    ScenicSpotDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ScenicSpot'];

    function ScenicSpotDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ScenicSpot) {
        var vm = this;
        vm.scenicSpot = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:scenicSpotUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.scenicSpot.id !== null) {
                ScenicSpot.update(vm.scenicSpot, onSaveSuccess, onSaveError);
            } else {
                ScenicSpot.save(vm.scenicSpot, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
    }
})();
