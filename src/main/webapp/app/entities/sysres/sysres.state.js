(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sysres', {
            parent: 'entity',
            url: '/sysres?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.sysres.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sysres/sysres.html',
                    controller: 'SysresController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sysres');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sysres-detail', {
            parent: 'entity',
            url: '/sysres/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.sysres.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sysres/sysres-detail.html',
                    controller: 'SysresDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sysres');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Sysres', function($stateParams, Sysres) {
                    return Sysres.get({id : $stateParams.id});
                }]
            }
        })
        .state('sysres.new', {
            parent: 'sysres',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sysres/sysres-dialog.html',
                    controller: 'SysresDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                type: null,
                                url: null,
                                resdesc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sysres', null, { reload: true });
                }, function() {
                    $state.go('sysres');
                });
            }]
        })
        .state('sysres.edit', {
            parent: 'sysres',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sysres/sysres-dialog.html',
                    controller: 'SysresDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sysres', function(Sysres) {
                            return Sysres.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('sysres', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sysres.delete', {
            parent: 'sysres',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sysres/sysres-delete-dialog.html',
                    controller: 'SysresDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sysres', function(Sysres) {
                            return Sysres.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('sysres', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
