(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('PictruesController', PictruesController);

    PictruesController.$inject = ['$scope', '$state', 'Pictrues', 'PictruesSearch', 'ParseLinks', 'AlertService'];

    function PictruesController ($scope, $state, Pictrues, PictruesSearch, ParseLinks, AlertService) {
        var vm = this;
        
        vm.ProController = {
        		options: {
        			url: '/api/products/get',
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
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'preferential',
                        title: '优惠信息',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'pricedesc',
                        title: '起价描述',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'costdesc',
                        title: '费用说明',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'trip',
                        title: '行程说明',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'start',
                        title: '出发地',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'route',
                        title: '路线',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    }, {
                        field: 'prodesc',
                        title: '产品说明',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    },
//                     {
//                        field: 'rate',
//                        title: '好评率',
//                        align: 'left',
//                        valign: 'top',
//                        sortable: true,
//                    },
//                    {
//                        field: 'state',
//                        title: '状态',
//                        align: 'left',
//                        dispaly:'none',
//                        valign: 'top',
//                        sortable: true,
//                    },
                    {
                        field: 'type',
                        title: '类型',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    },{
                        field: 'days',
                        title: '天数',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    },{
                        field: 'startdate',
                        title: '出发时间',
                        align: 'left',
                        valign: 'top',
                        sortable: true
                    },
//                    {
//                        field: 'pics',
//                        title: '图片',
//                        align: 'left',
//                        valign: 'top',
//                        sortable: true
//                    },
                    {
                        field: '操作',
                        title: '操作',
                        align: 'center',
                        valign: 'middle',
                        width: '20%',
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
        	        "<a href='#/product/" + row.id + "' class='btn btn-info btn-sm'>",
        	        	"<span class='glyphicon glyphicon-eye-open'></span>",
        	        	"<span class='hidden-xs hidden-sm'>明细</span>",
        	        "</a>",
        	        "<a href='#/product/" + row.id + "/edit' class='btn btn-primary btn-sm'>",
        	        	"<span class='glyphicon glyphicon-pencil'></span>",
        	        	"<span class='hidden-xs hidden-sm'>编辑</span>",
        	        "</a>",
        	        "<a href='#/product/" + row.id + "/delete' class='btn btn-danger btn-sm'>",
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
