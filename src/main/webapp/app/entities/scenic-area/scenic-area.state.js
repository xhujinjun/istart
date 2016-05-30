(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('scenic-area', {
            parent: 'entity',
            url: '/scenic-area?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.scenicArea.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scenic-area/scenic-areas.html',
                    controller: 'ScenicAreaController',
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
                    $translatePartialLoader.addPart('scenicArea');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('scenic-area-detail', {
            parent: 'entity',
            url: '/scenic-area/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.scenicArea.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scenic-area/scenic-area-detail.html',
                    controller: 'ScenicAreaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scenicArea');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ScenicArea', function($stateParams, ScenicArea) {
                    return ScenicArea.get({id : $stateParams.id});
                }]
            }
        })
        .state('scenic-area.new', {
            parent: 'scenic-area',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scenic-area/scenic-area-dialog.html',
                    controller: 'ScenicAreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                scenicStar: null,
                                score: null,
                                interestNum: null,
                                ticket: null,
                                ticketDesc: null,
                                openStartTime: null,
                                openEndTime: null,
                                openDesc: null,
                                playTimeNum: null,
                                contact: null,
                                website: null,
                                overview: null,
                                imagePath: null,
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
                    $state.go('scenic-area', null, { reload: true });
                }, function() {
                    $state.go('scenic-area');
                });
            }]
        })
        .state('scenic-area.edit', {
            parent: 'scenic-area',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scenic-area/scenic-area-dialog.html',
                    controller: 'ScenicAreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ScenicArea', function(ScenicArea) {
                            return ScenicArea.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('scenic-area', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scenic-area.delete', {
            parent: 'scenic-area',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scenic-area/scenic-area-delete-dialog.html',
                    controller: 'ScenicAreaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ScenicArea', function(ScenicArea) {
                            return ScenicArea.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('scenic-area', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
