(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Hotel', Hotel);

    Hotel.$inject = ['$resource', 'DateUtils'];

    function Hotel ($resource, DateUtils) {
        var resourceUrl =  'api/hotels/:id';
        alert(resourceUrl);
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
//                    data.dataCreateDatetime = DateUtils.convertDateTimeFromServer(data.dataCreateDatetime);
//                    data.dataUpdateDatetime = DateUtils.convertDateTimeFromServer(data.dataUpdateDatetime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
