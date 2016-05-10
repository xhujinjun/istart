(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Book', 'Author'];

    function BookDetailController($scope, $rootScope, $stateParams, entity, Book, Author) {
        var vm = this;
        vm.book = entity;
        vm.load = function (id) {
            Book.get({id: id}, function(result) {
                vm.book = result;
            });
        };
        var unsubscribe = $rootScope.$on('istartApp:bookUpdate', function(event, result) {
            vm.book = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
