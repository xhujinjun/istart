(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ScenicAreaController', ScenicAreaController);

    ScenicAreaController.$inject = ['$scope'];

    function ScenicAreaController ($scope) {
        var vm = this;
        vm.bsTableControl = {
        		options: {
        			url: '/api/scenic-areas',
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
                        field: 'name',
                        title: '景区名称',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'scenicStar',
                        title: '星级',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'ticket',
                        title: '门票',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'contact',
                        title: '联系电话',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'website',
                        title: '网址',
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
        	        "<a href='#/scenic-area/" + row.id + "' class='btn btn-info btn-sm'>",
        	        	"<span class='glyphicon glyphicon-eye-open'></span>",
        	        	"<span class='hidden-xs hidden-sm'>明细</span>",
        	        "</a>",
        	        "<a href='#/scenic-area/" + row.id + "/edit' class='btn btn-primary btn-sm'>",
        	        	"<span class='glyphicon glyphicon-pencil'></span>",
        	        	"<span class='hidden-xs hidden-sm'>编辑</span>",
        	        "</a>",
        	        "<a href='#/scenic-area/" + row.id + "/delete' class='btn btn-danger btn-sm'>",
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
