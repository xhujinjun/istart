(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TravelAgencyDialogController', TravelAgencyDialogController);

    TravelAgencyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TravelAgency'];

    function TravelAgencyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TravelAgency) {
        var vm = this;
        vm.travelAgency = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:travelAgencyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.travelAgency.id !== null) {
                TravelAgency.update(vm.travelAgency, onSaveSuccess, onSaveError);
            } else {
                TravelAgency.save(vm.travelAgency, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.buildDate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
