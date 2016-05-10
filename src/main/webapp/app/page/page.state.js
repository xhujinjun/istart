(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('page', {
        	abstract: true,
        	parent: 'app',
            url: '/page',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '个人中心'
            }
        }).state('page.profile', {
        	parent: 'page',
            url: '/profile',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '个人中心'
            },
            views: {
                'content@': {
                	templateUrl: 'app/page/page_profile.html'
                }
            }
        }).state('page.post', {
        	parent: 'page',
            url: '/post',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '博客'
            },
            views: {
                'content@': {
                	templateUrl: 'app/page/page_post.html'
                }
            }
        }).state('page.search', {
        	parent: 'page',
            url: '/search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/page/page_search.html'
                }
            }
        }).state('page.invoice', {
        	parent: 'page',
            url: '/invoice',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/page/page_invoice.html'
                }
            }
        }).state('page.price', {
        	parent: 'page',
            url: '/price',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/page/page_price.html'
                }
            }
        })
        .state('lockme', {
        	parent: 'app',
            url: '/lockme',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '个人中心'
            },
            views: {
                'content@': {
                    templateUrl: 'app/page/page_lockme.html'
                }
            }
        }).state('access', {
        	abstract: true,
        	parent: 'app',
            url: '/access',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '个人中心'
            }
        }).state('access.signin', {
        	parent: 'access',
            url: '/signin',
            views: {
                'content@': {
                    templateUrl: 'app/page/page_signin.html'
                }
            }
        }).state('access.signup', {
        	parent: 'access',
            url: '/signup',
            views: {
                'content@': {
                    templateUrl: 'app/page/page_signup.html'
                }
            }
        }).state('access.forgotpwd', {
        	parent: 'access',
            url: '/forgotpwd',
            views: {
                'content@': {
                    templateUrl: 'app/page/page_forgotpwd.html'
                }
            }
        }).state('access.404', {
        	parent: 'access',
            url: '/404',
            views: {
                'content@': {
                    templateUrl: 'app/page/page_404.html'
                }
            }
        })
        ;
    }
})();
