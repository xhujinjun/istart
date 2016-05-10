(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Role', Role);

    Role.$inject = ['$resource'];

    function Role ($resource) {
        var resourceUrl =  'api/roles/:id';

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
