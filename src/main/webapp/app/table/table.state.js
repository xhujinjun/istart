(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('table', {
        	abstract: true,
        	parent: 'app',
            url: '/table',
            data: {
                authorities: ['ROLE_ADMIN','ROLE_USER'],
                pageTitle: 'table'
            },
            views: {
                'content@': {
                	template: '<div ui-view></div>'
                }
            }
        }).state('table.static', {
        	parent: 'table',
            url: '/static',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'static'
            },
            views: {
                'content@': {
                	templateUrl: 'app/table/table_static.html'
                }
            }
        }).state('table.bootstraptable', {
        	parent: 'table',
        	url: '/bootstraptable',
        	data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bootstraptable'
            },
            views: {
                'content@': {
                	templateUrl: 'app/table/bootstrap_table.html',
                    controller: 'TableController'
                }
            }
        }).state('table.treegrid', {
        	parent: 'table',
        	url: '/treeGrid',
        	data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'treeGrid'
            },
            views: {
                'content@': {
                	templateUrl: 'app/table/table_grid.html',
                    controller: 'GridDemoCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        ;
    }
})();
