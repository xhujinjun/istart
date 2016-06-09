(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Trip', Trip);

    Trip.$inject = ['$resource'];

    function Trip ($resource) {
        var resourceUrl =  'api/trips/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
