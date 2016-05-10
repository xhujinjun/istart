(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Sysres', Sysres);

    Sysres.$inject = ['$resource'];

    function Sysres ($resource) {
        var resourceUrl =  'api/sysres/:id';

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
