(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('dashboard-v1', {
            parent: 'app',
            url: '/dashboard-v1',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dashboard-v1'
            },
            templateUrl: 'app/calendar/app_dashboard_v1.html'
        }).state('dashboard-v2', {
            parent: 'app',
            url: '/dashboard-v2',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dashboard-v2'
            },
            templateUrl: 'app/calendar/app_dashboard_v2.html'
        });
    }
})();
