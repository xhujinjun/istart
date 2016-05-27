(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('TravelAgencyController', TravelAgencyController);

    TravelAgencyController.$inject = ['$scope'];

    function TravelAgencyController ($scope) {
        var vm = this;
        vm.bsTableControl = {
        		options: {
        			url: '/api/travel-agencies',
                    cache: false,
                    striped: true,//隔行变色效果
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
                    pageList: [10, 25, 50, 100],
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
                        field: 'agencyCode',
                        title: '旅行社编号',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        width: '10%'
                    }, {
                        field: 'agencyName',
                        title: '旅行社名称',
                        align: 'left',
                        valign: 'top',
                        sortable: true,
                        width: '10%'
                    }, {
                        field: 'agencyIntroduce',
                        title: '旅行社介绍',
                        align: 'left',
                        valign: 'top',
                        formatter: agencyIntroduceFormatter,
                        width: '20%'
                    }, {
                        field: 'addr',
                        title: '旅行社地址',
                        align: 'left',
                        valign: 'top',
                        width: '20%'
                    }, {
                        field: 'buildDate',
                        title: '成立时间',
                        align: 'left',
                        valign: 'top',
                        sortable: true,
                        width: '10%'
                    }, {
                        field: 'contactPhone',
                        title: '联系电话',
                        align: 'left',
                        valign: 'top',
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
        	        "<a href='#/travel-agency/" + row.id + "' class='btn btn-info btn-sm'>",
        	        	"<span class='glyphicon glyphicon-eye-open'></span>",
        	        	"<span class='hidden-xs hidden-sm'>明细</span>",
        	        "</a>",
        	        "<a href='#/travel-agency/" + row.id + "/edit' class='btn btn-primary btn-sm'>",
        	        	"<span class='glyphicon glyphicon-pencil'></span>",
        	        	"<span class='hidden-xs hidden-sm'>编辑</span>",
        	        "</a>",
        	        "<a href='#/travel-agency/" + row.id + "/delete' class='btn btn-danger btn-sm'>",
        	        	"<span class='glyphicon glyphicon-remove-circle'></span>",
        	        	"<span class='hidden-xs hidden-sm'>删除</span>",
        	        "</a>"
            ].join('');
        }
        function agencyIntroduceFormatter(value, row, index) {
        	if(value != null){
        		if(value.length>30){
        			return value.substring(0,30) + "...";
        		}
        	}
        	return value;
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
        	console.log('params1:',params);
        	$('#form').find('input[name]').each(function () {
        		params[$(this).attr('name')] = $(this).val();
        	});
        	console.log('params2:',params);
        	return params;
        }
    }
})();
