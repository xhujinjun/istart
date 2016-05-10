(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Area', Area);

    Area.$inject = ['$resource', 'DateUtils'];

    function Area ($resource, DateUtils) {
        var resourceUrl =  'api/areas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDatetime = DateUtils.convertDateTimeFromServer(data.createDatetime);
                    data.modifiyDatetime = DateUtils.convertDateTimeFromServer(data.modifiyDatetime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
