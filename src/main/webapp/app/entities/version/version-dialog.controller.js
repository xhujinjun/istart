(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('VersionDialogController', VersionDialogController);

    VersionDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Version'];

    function VersionDialogController ($scope, $stateParams, $uibModalInstance, entity, Version) {
        var vm = this;
        vm.version = entity;
        vm.load = function(id) {
            Version.get({id : id}, function(result) {
                vm.version = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:versionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.version.id !== null) {
                Version.update(vm.version, onSaveSuccess, onSaveError);
            } else {
                Version.save(vm.version, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.createTime = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
