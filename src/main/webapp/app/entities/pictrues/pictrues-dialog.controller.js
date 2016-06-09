(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('PictruesDialogController', PictruesDialogController);

    PictruesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pictrues'];

    function PictruesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pictrues) {
        var vm = this;
        vm.pictrues = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:pictruesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.pictrues.id !== null) {
                Pictrues.update(vm.pictrues, onSaveSuccess, onSaveError);
            } else {
                Pictrues.save(vm.pictrues, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
