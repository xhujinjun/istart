(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('role', {
            parent: 'entity',
            url: '/role?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.role.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/role/roles.html',
                    controller: 'RoleController',
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
                    $translatePartialLoader.addPart('role');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('role-detail', {
            parent: 'entity',
            url: '/role/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.role.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/role/role-detail.html',
                    controller: 'RoleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('role');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Role', function($stateParams, Role) {
                    return Role.get({id : $stateParams.id});
                }]
            }
        })
        .state('role.new', {
            parent: 'role',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role/role-dialog.html',
                    controller: 'RoleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                seq: null,
                                dataStatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('role', null, { reload: true });
                }, function() {
                    $state.go('role');
                });
            }]
        })
        .state('role.edit', {
            parent: 'role',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role/role-dialog.html',
                    controller: 'RoleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Role', function(Role) {
                            return Role.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('role', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('role.delete', {
            parent: 'role',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role/role-delete-dialog.html',
                    controller: 'RoleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Role', function(Role) {
                            return Role.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('role', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
