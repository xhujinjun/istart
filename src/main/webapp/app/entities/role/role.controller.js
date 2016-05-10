(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('RoleController', RoleController);

    RoleController.$inject = ['$scope', '$state', 'Role', 'RoleSearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function RoleController ($scope, $state, Role, RoleSearch, ParseLinks, AlertService, pagingParams, paginationConstants) {
    	console.log('');
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.clear = clear;
        vm.search = search;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
        vm.loadAll();// 执行loadAll()函数

        function loadAll () {
        	console.log('loadAll ()');
        	console.log('pagingParams',pagingParams);
        	console.log('pagingParams.search',pagingParams.search);
            if (pagingParams.search) {
                RoleSearch.query({
                    query: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                Role.query({
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.roles = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
        	console.log('loadPage (page)');
            vm.page = page;
            vm.transition();
        }

        function transition () {
        	console.log('transition ()');
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function search (searchQuery) {
        	console.log('search (searchQuery)');
            if (!searchQuery){
                return vm.clear();
            }
            vm.links = null;
            vm.page = 1;
            vm.predicate = '_score';
            vm.reverse = false;
            vm.currentSearch = searchQuery;
            vm.transition();
        }

        function clear () {
        	console.log('clear ()');
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.currentSearch = null;
            vm.transition();
        }

    }
})();
