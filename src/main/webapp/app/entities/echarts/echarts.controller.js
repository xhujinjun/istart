(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('EchartsController', EchartsController);

    EchartsController.$inject = ['$scope', '$state','$http'];

    function EchartsController ($scope, $state,$http) {
        var vm = this;
        vm.loadEcharts = loadEcharts;
        vm.loadEcharts();
        var myChart = echarts.init(document.getElementById('main'));
        myChart.showLoading();
        function loadEcharts () {
        	$http.get('api/echarts').
        	  success(function(data, status, headers, config) {
        		  // 指定图表的配置项和数据
        		  console.log(data);
                  var option = eval(data);
                  console.log(option);
                  // 使用刚指定的配置项和数据显示图表。
                  myChart.setOption(option);
                  myChart.hideLoading();
        	  }).
        	  error(function(data, status, headers, config) {
        		  myChart.hideLoading();
        	  });
        }
    }
})();
