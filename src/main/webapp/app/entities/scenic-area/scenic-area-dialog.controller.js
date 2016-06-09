(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ScenicAreaDialogController', ScenicAreaDialogController);

    ScenicAreaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ScenicArea', 'ScenicSpot'];

    function ScenicAreaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ScenicArea, ScenicSpot) {
        var vm = this;
        vm.scenicArea = entity;
        vm.scenicspots = ScenicSpot.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:scenicAreaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.scenicArea.id !== null) {
                ScenicArea.update(vm.scenicArea, onSaveSuccess, onSaveError);
            } else {
                ScenicArea.save(vm.scenicArea, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.openStartTime = false;
        vm.datePickerOpenStatus.openEndTime = false;
        vm.datePickerOpenStatus.dataCreateDatetime = false;
        vm.datePickerOpenStatus.dataUpdateDatetime = false;

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
