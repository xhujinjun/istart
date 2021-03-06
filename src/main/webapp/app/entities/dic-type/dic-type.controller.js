(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('DicTypeController', DicTypeController);

    DicTypeController.$inject = ['$scope'];

    function DicTypeController ($scope) {
        var vm = this;
        vm.bsTableControl = {
        		options: {
        			url: '/api/dic-types',
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
                    queryParams: queryParams,
                    pagination: true,
                    idField: 'id',
                    pageSize: 10,
                    pageList: [10, 25, 50, 100, 200],
                    paginationVAlign: 'bottom',
                    sidePagination: 'server',
                    paginationFirstText: 'First', 
                    paginationPreText: 'Previous',
                    paginationNextText: 'Next',
                    paginationLastText: 'Last',
                    columns: [{
                        field: 'state',
                        checkbox: true
                    }, {
                        field: 'dicTypeCode',
                        title: '字典类型代码',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'dicTypeName',
                        title: '字典类型名称',
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
        	        "<a href='#/dic-type/" + row.id + "' class='btn btn-info btn-sm'>",
        	        	"<span class='glyphicon glyphicon-eye-open'></span>",
        	        	"<span class='hidden-xs hidden-sm'>明细</span>",
        	        "</a>",
        	        "<a href='#/dic-type/" + row.id + "/edit' class='btn btn-primary btn-sm'>",
        	        	"<span class='glyphicon glyphicon-pencil'></span>",
        	        	"<span class='hidden-xs hidden-sm'>编辑</span>",
        	        "</a>",
        	        "<a href='#/dic-type/" + row.id + "/delete' class='btn btn-danger btn-sm'>",
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
        
        var $table = $('#table'),
    	$ok = $('#ok');
        $(function () {
        	$ok.click(function () {
        		$table.bootstrapTable('refresh');
        	});
        });
        function queryParams(params) {
        	$('#form').find('input[name]').each(function () {
        		params[$(this).attr('name')] = $(this).val();
        	});
        	return params;
        }
    }
})();
