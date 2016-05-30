(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('ScenicSpot', ScenicSpot);

    ScenicSpot.$inject = ['$resource'];

    function ScenicSpot ($resource) {
        var resourceUrl =  'api/scenic-spots/:id';

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
