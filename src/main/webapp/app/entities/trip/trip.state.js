(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trip', {
            parent: 'entity',
            url: '/trip',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.trip.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trip/trips.html',
                    controller: 'TripController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trip');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('trip-detail', {
            parent: 'entity',
            url: '/trip/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.trip.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trip/trip-detail.html',
                    controller: 'TripDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trip');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Trip', function($stateParams, Trip) {
                    return Trip.get({id : $stateParams.id});
                }]
            }
        })
        .state('trip.new', {
            parent: 'trip',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip/trip-dialog.html',
                    controller: 'TripDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                pno: null,
                                day: null,
                                discripe: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trip', null, { reload: true });
                }, function() {
                    $state.go('trip');
                });
            }]
        })
        .state('trip.edit', {
            parent: 'trip',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip/trip-dialog.html',
                    controller: 'TripDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trip', function(Trip) {
                            return Trip.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('trip', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trip.delete', {
            parent: 'trip',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip/trip-delete-dialog.html',
                    controller: 'TripDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Trip', function(Trip) {
                            return Trip.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('trip', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
