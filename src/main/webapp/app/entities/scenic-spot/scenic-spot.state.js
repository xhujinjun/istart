(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('scenic-spot', {
            parent: 'entity',
            url: '/scenic-spot?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.scenicSpot.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scenic-spot/scenic-spots.html',
                    controller: 'ScenicSpotController',
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
                    $translatePartialLoader.addPart('scenicSpot');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('scenic-spot-detail', {
            parent: 'entity',
            url: '/scenic-spot/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.scenicSpot.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scenic-spot/scenic-spot-detail.html',
                    controller: 'ScenicSpotDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scenicSpot');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ScenicSpot', function($stateParams, ScenicSpot) {
                    return ScenicSpot.get({id : $stateParams.id});
                }]
            }
        })
        .state('scenic-spot.new', {
            parent: 'scenic-spot',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scenic-spot/scenic-spot-dialog.html',
                    controller: 'ScenicSpotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                scenicAreasId: null,
                                name: null,
                                introduce: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scenic-spot', null, { reload: true });
                }, function() {
                    $state.go('scenic-spot');
                });
            }]
        })
        .state('scenic-spot.edit', {
            parent: 'scenic-spot',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scenic-spot/scenic-spot-dialog.html',
                    controller: 'ScenicSpotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ScenicSpot', function(ScenicSpot) {
                            return ScenicSpot.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('scenic-spot', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scenic-spot.delete', {
            parent: 'scenic-spot',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scenic-spot/scenic-spot-delete-dialog.html',
                    controller: 'ScenicSpotDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ScenicSpot', function(ScenicSpot) {
                            return ScenicSpot.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('scenic-spot', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
