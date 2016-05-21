(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('DicTypeDialogController', DicTypeDialogController);

    DicTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DicType'];

    function DicTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DicType) {
        var vm = this;
        vm.dicType = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:dicTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.dicType.id !== null) {
                DicType.update(vm.dicType, onSaveSuccess, onSaveError);
            } else {
                DicType.save(vm.dicType, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.dataCreateDatetime = false;
        vm.datePickerOpenStatus.dataUpdateDatetime = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
