(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('echarts', {
            parent: 'entity',
            url: '/echarts',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'echarts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/echarts/echarts.html',
                    controller: 'EchartsController',
                    controllerAs: 'vm'
                }
            }
        })
    }

})();
