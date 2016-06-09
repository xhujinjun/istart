(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Pictrues', Pictrues);

    Pictrues.$inject = ['$resource'];

    function Pictrues ($resource) {
        var resourceUrl =  'api/pictrues/:id';

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
