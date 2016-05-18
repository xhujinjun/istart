(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('travel-agency', {
            parent: 'entity',
            url: '/travel-agency?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.travelAgency.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/travel-agency/travel-agencies.html',
                    controller: 'TravelAgencyController',
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
                    $translatePartialLoader.addPart('travelAgency');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('travel-agency-detail', {
            parent: 'entity',
            url: '/travel-agency/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.travelAgency.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/travel-agency/travel-agency-detail.html',
                    controller: 'TravelAgencyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('travelAgency');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TravelAgency', function($stateParams, TravelAgency) {
                    return TravelAgency.get({id : $stateParams.id});
                }]
            }
        })
        .state('travel-agency.new', {
            parent: 'travel-agency',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-agency/travel-agency-dialog.html',
                    controller: 'TravelAgencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                agencyCode: null,
                                agencyName: null,
                                agencyIntroduce: null,
                                addr: null,
                                buildDate: null,
                                contactPhone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('travel-agency', null, { reload: true });
                }, function() {
                    $state.go('travel-agency');
                });
            }]
        })
        .state('travel-agency.edit', {
            parent: 'travel-agency',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-agency/travel-agency-dialog.html',
                    controller: 'TravelAgencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TravelAgency', function(TravelAgency) {
                            return TravelAgency.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('travel-agency', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('travel-agency.delete', {
            parent: 'travel-agency',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/travel-agency/travel-agency-delete-dialog.html',
                    controller: 'TravelAgencyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TravelAgency', function(TravelAgency) {
                            return TravelAgency.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('travel-agency', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
