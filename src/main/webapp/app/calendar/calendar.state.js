(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('calendar', {
            parent: 'app',
            url: '/calendar',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'calendar'
            },
            views: {
                'content@': {
                    templateUrl: 'app/calendar/calendar.html',
                    controller: 'FullcalendarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
