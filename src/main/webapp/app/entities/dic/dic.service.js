(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Dic', Dic);

    Dic.$inject = ['$resource', 'DateUtils'];

    function Dic ($resource, DateUtils) {
        var resourceUrl =  'api/dics/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
