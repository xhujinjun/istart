(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('DicController', DicController);

    DicController.$inject = ['$scope'];

    function DicController ($scope) {
        var vm = this;
        vm.bsTableControl = {
        		options: {
        			url: '/api/dics',
                    cache: false,
                    striped: true,
                    toolbar: '#toolbar',
                    search: false,
                    showRefresh: true,
                    showToggle: true,
                    showColumns: true,
                    showExport: true,
                    detailView: true,
                    detailFormatter: detailFormatter,
                    minimumCountColumns: 2,
                    clickToSelect: true,
                    maintainSelected: true,
                    striped: true,//隔行变色效果
                    queryParamsType: 'limit',
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
                        title: '字典类型编码',
                        align: 'left',
                        valign: 'middle',
                        sortable: true,
                        width: '10%'
                    }, {
                        field: 'dicTypeName',
                        title: '字典类型',
                        align: 'left',
                        valign: 'middle',
                        sortable: true,
                        width: '10%'
                    }, {
                        field: 'dicCode',
                        title: '字典编码',
                        align: 'left',
                        valign: 'top',
                        sortable: true,
                        width: '10%'
                    }, {
                        field: 'dicName',
                        title: '字典名称',
                        align: 'left',
                        valign: 'top',
                        sortable: true,
                        width: '10%'
                    }, {
                        field: 'dicNameDefinition',
                        title: '字典定义',
                        align: 'left',
                        valign: 'top',
                        sortable: true,
                        width: '10%'
                    }, {
                        field: 'dataCreator',
                        title: '数据创建者',
                        align: 'left',
                        valign: 'top',
                        sortable: true,
                        width: '5%'
                    }, {
                        field: 'dataUpdater',
                        title: '数据更新者',
                        align: 'left',
                        valign: 'top',
                        sortable: true,
                        width: '5%'
                    }, {
                        field: 'dataCreateDatetime',
                        title: '数据创建时间',
                        align: 'left',
                        valign: 'top',
                        sortable: true,
                        width: '10%'
                    }, {
                        field: 'dataUpdateDatetime',
                        title: '数据更新时间',
                        align: 'left',
                        valign: 'top',
                        sortable: true,
                        width: '10%'
                    },{
                        field: '操作',
                        title: '操作',
                        align: 'center',
                        valign: 'middle',
                        width: '20%',
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
