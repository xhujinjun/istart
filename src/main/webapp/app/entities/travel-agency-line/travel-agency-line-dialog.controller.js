(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TravelAgencyLineDialogController', TravelAgencyLineDialogController);

    TravelAgencyLineDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TravelAgencyLine'];

    function TravelAgencyLineDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TravelAgencyLine) {
        var vm = this;
        vm.travelAgencyLine = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:travelAgencyLineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.travelAgencyLine.id !== null) {
                TravelAgencyLine.update(vm.travelAgencyLine, onSaveSuccess, onSaveError);
            } else {
                TravelAgencyLine.save(vm.travelAgencyLine, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.lineDatetime = false;
        vm.datePickerOpenStatus.dataCreateDatetime = false;
        vm.datePickerOpenStatus.dataUpdateDatetime = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
