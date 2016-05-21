(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dic-type', {
            parent: 'entity',
            url: '/dic-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.dicType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dic-type/dic-types.html',
                    controller: 'DicTypeController',
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
                    $translatePartialLoader.addPart('dicType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dic-type-detail', {
            parent: 'entity',
            url: '/dic-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.dicType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dic-type/dic-type-detail.html',
                    controller: 'DicTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dicType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DicType', function($stateParams, DicType) {
                    return DicType.get({id : $stateParams.id});
                }]
            }
        })
        .state('dic-type.new', {
            parent: 'dic-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dic-type/dic-type-dialog.html',
                    controller: 'DicTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dicTypeCode: null,
                                dicTypeName: null,
                                dataCreator: null,
                                dataUpdater: null,
                                dataCreateDatetime: null,
                                dataUpdateDatetime: null,
                                dataStatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dic-type', null, { reload: true });
                }, function() {
                    $state.go('dic-type');
                });
            }]
        })
        .state('dic-type.edit', {
            parent: 'dic-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dic-type/dic-type-dialog.html',
                    controller: 'DicTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DicType', function(DicType) {
                            return DicType.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('dic-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dic-type.delete', {
            parent: 'dic-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dic-type/dic-type-delete-dialog.html',
                    controller: 'DicTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DicType', function(DicType) {
                            return DicType.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('dic-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
