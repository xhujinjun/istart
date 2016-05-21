(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('DicDialogController', DicDialogController);

    DicDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Dic', 'DicType'];

    function DicDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Dic, DicType) {
        var vm = this;
        vm.dic = entity;
        vm.dictypes = DicType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:dicUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.dic.id !== null) {
                Dic.update(vm.dic, onSaveSuccess, onSaveError);
            } else {
                Dic.save(vm.dic, onSaveSuccess, onSaveError);
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
