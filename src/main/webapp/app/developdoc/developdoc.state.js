(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('developdoc', {
            parent: 'app',
            url: '/developdoc',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'developdoc'
            },
            views: {
                'content@': {
                    templateUrl: 'app/developdoc/developdoc.html'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
