(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('hotel', {
            parent: 'app',
            url: '/hotel?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.hotel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hotel/hotels.html',
                    controller: 'HotelController',
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
                    $translatePartialLoader.addPart('hotel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
         .state('hotel-detail', {
            parent: 'entity',
            url: '/hotel/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.hotel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hotel/hotel-detail.html',
                    controller: 'HotelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hotel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Hotel', function($stateParams, Book) {
                    return Book.get({id : $stateParams.id});
                }]
            }
        })
        .state('hotel.new', {
            parent: 'hotel',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hotel/hotel-dialog.html',
                    controller: 'HotelDialogController',
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
                    $state.go('hotel', null, { reload: true });
                }, function() {
                    $state.go('hotel');
                });
            }]
        })
        .state('hotel.delete', {
            parent: 'hotel',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hotel/hotel-delete-dialog.html',
                    controller: 'DicDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Dic', function(Dic) {
                            return Dic.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('hotel', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
