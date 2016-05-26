(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('HotelDialogController', HotelDialogController);

    HotelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Hotel'];

    function HotelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Hotel) {
        var vm = this;
        vm.hotel = entity;
        $scope.hotel = {
                name: "Dani",
                currentActivity: "level"
            };
         
            $scope.levels =
            [
                "Five Star",
                "Four Star",
                "Three Star",
                "Two Star"
            ];
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('istartApp:hotelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.hotel.id !== null) {
                Hotel.update(vm.hotel, onSaveSuccess, onSaveError);
            } else {
            	Hotel.save(vm.hotel, onSaveSuccess, onSaveError);
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
