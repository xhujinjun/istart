(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pictrues', {
            parent: 'entity',
            url: '/pictrues',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.pictrues.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pictrues/pictrues.html',
                    controller: 'PictruesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pictrues');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pictrues-detail', {
            parent: 'entity',
            url: '/pictrues/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.pictrues.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pictrues/pictrues-detail.html',
                    controller: 'PictruesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pictrues');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Pictrues', function($stateParams, Pictrues) {
                    return Pictrues.get({id : $stateParams.id});
                }]
            }
        })
        .state('pictrues.new', {
            parent: 'pictrues',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pictrues/pictrues-dialog.html',
                    controller: 'PictruesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                pno: null,
                                picPath: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pictrues', null, { reload: true });
                }, function() {
                    $state.go('pictrues');
                });
            }]
        })
        .state('pictrues.edit', {
            parent: 'pictrues',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pictrues/pictrues-dialog.html',
                    controller: 'PictruesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pictrues', function(Pictrues) {
                            return Pictrues.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('pictrues', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pictrues.delete', {
            parent: 'pictrues',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pictrues/pictrues-delete-dialog.html',
                    controller: 'PictruesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pictrues', function(Pictrues) {
                            return Pictrues.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('pictrues', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
