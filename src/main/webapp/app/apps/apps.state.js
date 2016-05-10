(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('apps', {
        	abstract: true,
        	parent: 'app',
            url: '/apps',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '个人中心'
            }
        }).state('apps.note', {
        	parent: 'apps',
            url: '/note',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '个人中心'
            },
            views: {
                'content@': {
                	templateUrl: 'app/apps/apps_note.html',
                	 controller: 'NoteController',
                     controllerAs: 'vm'
                }
            }
        }).state('apps.contact', {
        	parent: 'apps',
            url: '/contact',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '博客'
            },
            views: {
                'content@': {
                	templateUrl: 'app/apps/apps_contact.html'
                }
            }
        }).state('apps.weather', {
        	parent: 'apps',
            url: '/weather',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/apps/apps_weather.html'
                }
            }
        })
        ;
    }
})();
