(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TripDialogController', TripDialogController);

    TripDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trip'];

    function TripDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Trip) {
        var vm = this;
        vm.trip = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:tripUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.trip.id !== null) {
                Trip.update(vm.trip, onSaveSuccess, onSaveError);
            } else {
                Trip.save(vm.trip, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
