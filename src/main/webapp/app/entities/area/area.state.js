(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('area', {
            parent: 'entity',
            url: '/area?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.area.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/area/areas.html',
                    controller: 'AreaController',
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
                    $translatePartialLoader.addPart('area');
                    $translatePartialLoader.addPart('dataStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('area-detail', {
            parent: 'entity',
            url: '/area/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.area.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/area/area-detail.html',
                    controller: 'AreaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('area');
                    $translatePartialLoader.addPart('dataStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Area', function($stateParams, Area) {
                    return Area.get({id : $stateParams.id});
                }]
            }
        })
        .state('area.new', {
            parent: 'area',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/area/area-dialog.html',
                    controller: 'AreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                areaid: null,
                                name: null,
                                parentid: null,
                                parentname: null,
                                addr: null,
                                level: null,
                                isleaf: null,
                                createDatetime: null,
                                modifiyDatetime: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('area', null, { reload: true });
                }, function() {
                    $state.go('area');
                });
            }]
        })
        .state('area.edit', {
            parent: 'area',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/area/area-dialog.html',
                    controller: 'AreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Area', function(Area) {
                            return Area.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('area', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('area.delete', {
            parent: 'area',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/area/area-delete-dialog.html',
                    controller: 'AreaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Area', function(Area) {
                            return Area.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('area', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
