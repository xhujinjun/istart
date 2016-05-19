(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TravelAgencyController', TravelAgencyController);

    TravelAgencyController.$inject = ['$scope', '$state', 'TravelAgency', 'TravelAgencySearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function TravelAgencyController ($scope, $state, TravelAgency, TravelAgencySearch, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var $table = $('#table'),
        $remove = $('#remove'),
        selections = [];
        
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
        window.operateEvents = {
                'click .like': function (e, value, row, index) {
                    alert('You click like action, row: ' + JSON.stringify(row));
                },
                'click .remove': function (e, value, row, index) {
                    $table.bootstrapTable('remove', {
                        field: 'id',
                        values: [row.id]
                    });
                }
        };
        vm.bsTableControl = {
        		options: {
        			url: '/api/travel-agencies',
                   /* data: vm.rows,*/
                    cache: false,
                    height: 400,
                    striped: true,
                    toolbar: '#toolbar',
                    search: true,
                    showRefresh: true,
                    showToggle: true,
                    showColumns: true,
                    showExport: true,
                    detailView: true,
                    detailFormatter: detailFormatter,
                    minimumCountColumns: 2,
                    clickToSelect: true,
                    maintainSelected: true,
                    
                    pagination: true,
                    idField: 'id',
                    pageSize: 10,
                    pageList: [10, 25, 50, 100, 200],
                    paginationVAlign: 'bottom',
                    rowStyle: rowStyle,
                    columns: [{
                        field: 'state',
                        checkbox: true
                    }, {
                        field: 'id',
                        title: '数据ID',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        field: 'agencyCode',
                        title: '旅行社编号',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'agencyName',
                        title: '旅行社名称',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'agencyIntroduce',
                        title: '旅行社介绍',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'addr',
                        title: '旅行社地址',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'buildDate',
                        title: '成立时间',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'contactPhone',
                        title: '联系电话',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    },{
                        field: '操作',
                        title: '操作',
                        align: 'center',
                        valign: 'middle',
                        formatter: flagFormatter,
                        events: operateEvents
                    }]
                }
            };
        
        function flagFormatter(value, row, index) {
            return [
                    '<a class="like" href="javascript:void(0)" title="Like">',
                    '<i class="glyphicon glyphicon-heart"></i>',
                    '</a>  ',
                    '<a class="remove" href="javascript:void(0)" title="Remove">',
                    '<i class="glyphicon glyphicon-remove"></i>',
                    '</a>'
                ].join('');
        }
        function rowStyle(row, index) {
            var classes = ['active', 'success', 'info', 'warning', 'danger'];
            if (index % 2 === 0 && index / 2 < classes.length) {
                return {
                    classes: classes[index / 2]
                };
            }
            return {};
        }
        function detailFormatter(index, row) {
            var html = [];
            $.each(row, function (key, value) {
                html.push('<p><b>' + key + ':</b> ' + value + '</p>');
            });
            return html.join('');
=======
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
>>>>>>> branch 'master' of https://github.com/xhujinjun/istart.git
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
