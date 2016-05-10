(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('SysresDialogController', SysresDialogController);

    SysresDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sysres'];

    function SysresDialogController ($scope, $stateParams, $uibModalInstance, entity, Sysres) {
        var vm = this;
        vm.sysres = entity;
        vm.load = function(id) {
            Sysres.get({id : id}, function(result) {
                vm.sysres = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:sysresUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.sysres.id !== null) {
                Sysres.update(vm.sysres, onSaveSuccess, onSaveError);
            } else {
                Sysres.save(vm.sysres, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
