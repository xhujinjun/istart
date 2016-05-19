(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TravelAgencyController', TravelAgencyController);

    TravelAgencyController.$inject = ['$scope', '$state', 'TravelAgency', 'TravelAgencySearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function TravelAgencyController ($scope, $state, TravelAgency, TravelAgencySearch, ParseLinks, AlertService, pagingParams, paginationConstants) {
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
     /*   vm.loadAll();*/
        vm.rows = makeRandomRows();
        vm.bsTableControl = {
        		options: {
                    data: vm.rows,
                    cache: false,
                    height: 400,
                    striped: true,
                    pagination: true,
                    pageSize: 10,
                    pageList: [5, 10, 25, 50, 100, 200],
                    search: true,
                    showColumns: true,
                    showRefresh: true,
                    minimumCountColumns: 2,
                    clickToSelect: true,
                    showToggle: true,
                    maintainSelected: true,
                    paginationVAlign: 'bottom',
                    columns: [{
                        field: 'state',
                        checkbox: true
                    }, {
                        field: 'index',
                        title: '#',
                        align: 'right',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        field: 'id',
                        title: 'Item ID',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        field: 'name',
                        title: 'Item Name',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'workspace',
                        title: 'Workspace',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'flag',
                        title: 'Flag',
                        align: 'center',
                        valign: 'middle',
                        clickToSelect: false,
                        formatter: flagFormatter,
                        // events: flagEvents
                    }]
                }
            };
        function makeRandomRows () {
            var rows = [];
            var colData = {}; 
            for (var i = 0; i < 500; i++) {
                rows.push($.extend({
                	state: true,
                	index: i,
                    id: 'row ' + i,
                    name: 'GOOG' + i,
                    flagImage: Math.random() < 0.4
                        ? 'content/images/blueFlag16.png'
                        : Math.random() < 0.75
                            ? 'content/images/yellowFlag16.png'
                            : 'content/images/greenFlag16.png'
                }, colData));
            }
            return rows;
        }
        
        function flagFormatter(value, row, index) {
            return '<img src="' + row.flagImage + '"/>'
        }
        function loadAll () {
            if (pagingParams.search) {
                TravelAgencySearch.query({
                    query: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                TravelAgency.query({
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
                vm.travelAgencies = data;
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

    }
})();
