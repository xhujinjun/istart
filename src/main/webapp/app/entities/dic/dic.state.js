(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dic', {
            parent: 'entity',
            url: '/dic?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.dic.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dic/dics.html',
                    controller: 'DicController',
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
                    $translatePartialLoader.addPart('dic');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dic-detail', {
            parent: 'entity',
            url: '/dic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.dic.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dic/dic-detail.html',
                    controller: 'DicDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dic');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Dic', function($stateParams, Dic) {
                    return Dic.get({id : $stateParams.id});
                }]
            }
        })
        .state('dic.new', {
            parent: 'dic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dic/dic-dialog.html',
                    controller: 'DicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dicTypeId: null,
                                dicCode: null,
                                dicName: null,
                                dicNameDefinition: null,
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
                    $state.go('dic', null, { reload: true });
                }, function() {
                    $state.go('dic');
                });
            }]
        })
        .state('dic.edit', {
            parent: 'dic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dic/dic-dialog.html',
                    controller: 'DicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dic', function(Dic) {
                            return Dic.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('dic', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dic.delete', {
            parent: 'dic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dic/dic-delete-dialog.html',
                    controller: 'DicDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Dic', function(Dic) {
                            return Dic.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('dic', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
