(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Version', Version);

    Version.$inject = ['$resource', 'DateUtils'];

    function Version ($resource, DateUtils) {
        var resourceUrl =  'api/versions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createTime = DateUtils.convertDateTimeFromServer(data.createTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
