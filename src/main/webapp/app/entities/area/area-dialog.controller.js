(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('AreaDialogController', AreaDialogController);

    AreaDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Area'];

    function AreaDialogController ($scope, $stateParams, $uibModalInstance, entity, Area) {
        var vm = this;
        vm.area = entity;
        vm.load = function(id) {
            Area.get({id : id}, function(result) {
                vm.area = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:areaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.area.id !== null) {
                Area.update(vm.area, onSaveSuccess, onSaveError);
            } else {
                Area.save(vm.area, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.createDatetime = false;
        vm.datePickerOpenStatus.modifiyDatetime = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
