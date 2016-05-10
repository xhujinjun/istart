(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('AuthorDetailController', AuthorDetailController);

    AuthorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Author', 'Book'];

    function AuthorDetailController($scope, $rootScope, $stateParams, entity, Author, Book) {
        var vm = this;
        vm.author = entity;
        vm.load = function (id) {
            Author.get({id: id}, function(result) {
                vm.author = result;
            });
        };
        var unsubscribe = $rootScope.$on('istartApp:authorUpdate', function(event, result) {
            vm.author = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
