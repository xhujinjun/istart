(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('DicController', DicController);

    DicController.$inject = ['$scope', '$state', 'Dic', 'DicSearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function DicController ($scope) {
        var vm = this;
        vm.bsTableControl = {
        		options: {
        			url: '/api/dics',
                   /* data: vm.rows,*/
                    cache: false,
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
                    sidePagination: 'server',
                    rowStyle: rowStyle,
                    columns: [{
                        field: 'state',
                        checkbox: true
                    }, {
                        field: 'dicTypeCode',
                        title: '字典类型编码',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'dicTypeName',
                        title: '字典类型',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'dicCode',
                        title: '字典编码',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'dicName',
                        title: '字典名称',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'dicNameDefinition',
                        title: '字典定义',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'dataCreator',
                        title: '数据创建者',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'dataUpdater',
                        title: '数据更新者',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'dataCreateDatetime',
                        title: '数据创建时间',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'dataUpdateDatetime',
                        title: '数据更新时间',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    },{
                        field: '操作',
                        title: '操作',
                        align: 'center',
                        valign: 'middle',
                        formatter: flagFormatter
                    }]
                }
            };
        function flagFormatter(value, row, index) {
        	return [
        	        "<a href='#/dic/" + row.id + "' class='btn btn-info btn-sm'>",
        	        	"<span class='glyphicon glyphicon-eye-open'></span>",
        	        	"<span class='hidden-xs hidden-sm'>明细</span>",
        	        "</a>",
        	        "<a href='#/dic/" + row.id + "/edit' class='btn btn-primary btn-sm'>",
        	        	"<span class='glyphicon glyphicon-pencil'></span>",
        	        	"<span class='hidden-xs hidden-sm'>编辑</span>",
        	        "</a>",
        	        "<a href='#/dic/" + row.id + "/delete' class='btn btn-danger btn-sm'>",
        	        	"<span class='glyphicon glyphicon-remove-circle'></span>",
        	        	"<span class='hidden-xs hidden-sm'>删除</span>",
        	        "</a>"
            ].join('');
        }
        
        function detailFormatter(index, row) {
            var html = [];
            $.each(row, function (key, value) {
                html.push('<p><b>' + key + ':</b> ' + value + '</p>');
            });
            return html.join('');
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
    }
})();
