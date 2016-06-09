(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('ScenicArea', ScenicArea);

    ScenicArea.$inject = ['$resource', 'DateUtils'];

    function ScenicArea ($resource, DateUtils) {
        var resourceUrl =  'api/scenic-areas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.openStartTime = DateUtils.convertDateTimeFromServer(data.openStartTime);
                    data.openEndTime = DateUtils.convertDateTimeFromServer(data.openEndTime);
                    data.dataCreateDatetime = DateUtils.convertDateTimeFromServer(data.dataCreateDatetime);
                    data.dataUpdateDatetime = DateUtils.convertDateTimeFromServer(data.dataUpdateDatetime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
