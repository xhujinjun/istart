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
//                    height: 400,
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
                    pageList: [10, 25, 50, 100],
                    paginationVAlign: 'bottom',
                    /*sidePagination: 'server',*/
                    	
                    rowStyle: rowStyle,
                    columns: [{
                        field: 'state',
                        checkbox: true
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
                        formatter: agencyIntroduceFormatter
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
             '<a type="submit" class="btn btn-info btn-sm" href="#/travel-agency/1">',
             '<span class="glyphicon glyphicon-eye-open"></span>',
             '<span class="hidden-xs hidden-sm">明细</span>',
             '</button>',
             '<a type="submit" class="btn btn-primary btn-sm" href="#/travel-agency/1/edit">',
             '<span class="glyphicon glyphicon-pencil"></span>',
             '<span class="hidden-xs hidden-sm">编辑</span>',
             '</button>',
             '<a type="submit" class="btn btn-danger btn-sm" href="#/travel-agency/1/delete">',
             '<span class="glyphicon glyphicon-remove-circle"></span>',
             '<span class="hidden-xs hidden-sm">删除</span>',
             '</button>'
            ].join('');
        }
        function agencyIntroduceFormatter(value, row, index) {
        	if(value != null){
        		if(value.length>15){
        			return value.substring(0,15) + "...";
        		}
        	}
        	return value;
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
