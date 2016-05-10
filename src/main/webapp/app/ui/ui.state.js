(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('ui', {
        	abstract: true,
        	parent: 'app',
            url: '/ui',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ui'
            }
        }).state('ui.buttons', {
        	parent: 'ui',
            url: '/buttons',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '个人中心'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_buttons.html'
                }
            }
        }).state('ui.icons', {
        	parent: 'ui',
            url: '/icons',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '博客'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_icons.html'
                }
            }
        }).state('ui.grid', {
        	parent: 'ui',
            url: '/grid',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_grid.html'
                }
            }
        }).state('ui.widgets', {
        	parent: 'ui',
            url: '/widgets',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_widgets.html'
                }
            }
        }).state('ui.bootstrap', {
        	parent: 'ui',
            url: '/imagecrop',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_bootstrap.html'
                }
            }
        }).state('ui.sortable', {
        	parent: 'ui',
            url: '/sortable',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_sortable.html'
                }
            }
        }).state('ui.portlet', {
        	parent: 'ui',
            url: '/portlet',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_portlet.html'
                }
            }
        }).state('ui.timeline', {
        	parent: 'ui',
            url: '/timeline',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_timeline.html'
                }
            }
        }).state('ui.tree', {
        	parent: 'ui',
            url: '/tree',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_tree.html'
                }
            }
        }).state('ui.toaster', {
        	parent: 'ui',
            url: '/toaster',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_toaster.html'
                }
            }
        }).state('ui.jvectormap', {
        	parent: 'ui',
            url: '/jvectormap',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/ui/ui_jvectormap.html'
                }
            }
        })
        ;
    }
})();
