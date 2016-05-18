(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('travel-agency-line', {
            parent: 'entity',
            url: '/travel-agency-line?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.travelAgencyLine.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/travel-agency-line/travel-agency-lines.html',
                    controller: 'TravelAgencyLineController',
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
                    $translatePartialLoader.addPart('travelAgencyLine');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('travel-agency-line-detail', {
            parent: 'entity',
            url: '/travel-agency-line/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.travelAgencyLine.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/travel-agency-line/travel-agency-line-detail.html',
                    controller: 'TravelAgencyLineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('travelAgencyLine');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TravelAgencyLine', function($stateParams, TravelAgencyLine) {
                    return TravelAgencyLine.get({id : $stateParams.id});
                }]
            }
        })
        .state('travel-agency-line.new', {
            parent: 'travel-agency-line',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-agency-line/travel-agency-line-dialog.html',
                    controller: 'TravelAgencyLineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                travelAgencyId: null,
                                line_number: null,
                                lineName: null,
                                spotIntroduce: null,
                                lineDatetime: null,
                                lineCity: null,
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
                    $state.go('travel-agency-line', null, { reload: true });
                }, function() {
                    $state.go('travel-agency-line');
                });
            }]
        })
        .state('travel-agency-line.edit', {
            parent: 'travel-agency-line',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-agency-line/travel-agency-line-dialog.html',
                    controller: 'TravelAgencyLineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TravelAgencyLine', function(TravelAgencyLine) {
                            return TravelAgencyLine.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('travel-agency-line', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('travel-agency-line.delete', {
            parent: 'travel-agency-line',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-agency-line/travel-agency-line-delete-dialog.html',
                    controller: 'TravelAgencyLineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TravelAgencyLine', function(TravelAgencyLine) {
                            return TravelAgencyLine.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('travel-agency-line', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
