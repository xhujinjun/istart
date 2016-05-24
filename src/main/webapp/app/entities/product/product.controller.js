(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ProductController', ProductController);

    ProductController.$inject = ['$scope', '$state', 'Product', 'ProductSearch', 'ParseLinks','PageSearch', 'AlertService', 'pagingParams', 'paginationConstants'];

    function ProductController ($scope, $state, Product, ProductSearch, ParseLinks,PageSearch, AlertService, pagingParams, paginationConstants) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.clear = clear;
        vm.search = search;
        vm.Pagesearch=Pagesearch;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
        vm.loadAll();
        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.startdate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
       
        function loadAll () {
            if (pagingParams.search) {
                ProductSearch.query({
                    query: pagingParams.search,
                    name: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                Product.query({
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
                vm.products = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function search (searchQuery) {
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
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.currentSearch = null;
            vm.transition();
        }
        function Pagesearch(){
        	var price=$("#price").val();
        	//var days=$("#days").val();
        	alert(sort())
        	var startdate=$("#startdate").val();
        	var start=$("#start").val()
        	var page={"pageNumber":pagingParams.page - 1,"pageSize":paginationConstants.itemsPerPage,"sort": sort()}
        	PageSearch.query({
        		price:price,
        		startdate: startdate,
        		start:start,
        		page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                property:vm.predicate,
                direction:vm.reverse ? 'asc' : 'desc'
             }, onSuccess, onError);
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
                vm.products = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

    }
})();
