(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('RoleDialogController', RoleDialogController);

    RoleDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Role'];

    function RoleDialogController ($scope, $stateParams, $uibModalInstance, entity, Role) {
        var vm = this;
        vm.role = entity;
        vm.load = function(id) {
            Role.get({id : id}, function(result) {
                vm.role = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:roleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.role.id !== null) {
                Role.update(vm.role, onSaveSuccess, onSaveError);
            } else {
                Role.save(vm.role, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
