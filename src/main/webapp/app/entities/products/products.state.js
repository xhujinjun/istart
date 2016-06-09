(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('products', {
            parent: 'entity',
            url: '/products',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.products.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/products/products.html',
                    controller: 'ProductsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('products');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('products-detail', {
            parent: 'entity',
            url: '/products/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.products.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/products/products-detail.html',
                    controller: 'ProductsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('products');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Products', function($stateParams, Products) {
                    return Products.get({id : $stateParams.id});
                }]
            }
        })
        .state('products.new', {
            parent: 'products',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/products/products-dialog.html',
                    controller: 'ProductsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                pno: null,
                                travelAgentId: null,
                                phone: null,
                                title: null,
                                price: null,
                                pricedesc: null,
                                preferential: null,
                                startdate: null,
                                startadderss: null,
                                endadderss: null,
                                days: null,
                                costdesc: null,
                                route: null,
                                detaildesc: null,
                                bookNotice: null,
                                rate: null,
                                detailTrip:null,
                                tourismTypesId: null,
                                detailTypeId: null,
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
                    $state.go('products', null, { reload: true });
                }, function() {
                    $state.go('products');
                });
            }]
        })
        .state('products.edit', {
            parent: 'products',
            url: '/{id}/{tourismTypesId}/{detailTypeId}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/products/products-dialog.html',
                    controller: 'ProductsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Products', function(Products) {
                            return Products.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('products', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('products.delete', {
            parent: 'products',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/products/products-delete-dialog.html',
                    controller: 'ProductsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Products', function(Products) {
                            return Products.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('products', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
