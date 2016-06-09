(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('ProductsController', ProductsController);

    ProductsController.$inject = ['$scope', '$state', 'Products', 'ProductsSearch', 'ParseLinks', 'AlertService'];

    function ProductsController ($scope, $state, Products, ProductsSearch, ParseLinks, AlertService) {
    	var vm = this;
        vm.ProductsController = {
        		options: {
        			url: '/api/getproducts',
                    cache: false,
                    striped: true,//隔行变色效果
                    toolbar: '#toolbar',
                    search: false,
                    showRefresh: true,
                    showToggle: true,
                    showColumns: true,
                    showExport: false,
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
                        field: 'travelAgency.agencyName',
                        title: '旅行社',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'pno',
                        title: '产品编号',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'title',
                        title: '标题',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'price',
                        title: '价格',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'preferential',
                        title: '优惠信息',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }, {
                        field: 'costdesc',
                        title: '费用说明',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    } ,{
                        field: 'startadderss',
                        title: '出发地',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    },{
                        field: 'endadderss',
                        title: '目的地',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }
                    , {
                        field: 'route',
                        title: '路线',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    },{
                        field: 'detailTrip',
                        title: '行程描述',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    }, 
                    {
                        field: 'tourismType.dicName',
                        title: '类型',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    },{
                        field: 'days',
                        title: '天数',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    },{
                        field: 'startdate',
                        title: '出发时间',
                        align: 'left',
                        valign: 'middle',
                        sortable: true
                    },
                    {
                        field: '操作',
                        title: '操作',
                        align: 'left',
                        valign: 'middle',
                        width: '25%',
                        formatter: flagFormatter
                    }]
                }
            };
     	vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.startdate = false;
    	vm.openCalendar = function(date) {
        vm.datePickerOpenStatus[date] = true;
        };
        function flagFormatter(value, row, index) {
        	return [
        	        "<a href='#/products/" + row.pno + "' class='btn btn-info btn-sm'>",
        	            "<span class='glyphicon glyphicon-pencil'></span>",
        	        	"<span class='hidden-xs hidden-sm'>图片</span>",
        	        "</a>",
        	        "<a href='#/products/" + row.id + "' class='btn btn-info btn-sm'>",
    	        	    "<span class='glyphicon glyphicon-eye-open'></span>",
    	        	    "<span class='hidden-xs hidden-sm'>明细</span>",
    	            "</a>",
        	        "<a href='#/products/" + row.id + "/"+row.tourismTypesId+"/"+row.detailTypeId+"/edit' class='btn btn-primary btn-sm'>",
        	        	"<span class='glyphicon glyphicon-pencil'></span>",
        	        	"<span class='hidden-xs hidden-sm'>编辑</span>",
        	        "</a>",
        	        "<a href='#/products/" + row.id + "/delete' class='btn btn-danger btn-sm'>",
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
