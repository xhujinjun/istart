(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('TravelAgencyLine', TravelAgencyLine);

    TravelAgencyLine.$inject = ['$resource', 'DateUtils'];

    function TravelAgencyLine ($resource, DateUtils) {
        var resourceUrl =  'api/travel-agency-lines/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.lineDatetime = DateUtils.convertDateTimeFromServer(data.lineDatetime);
                    data.dataCreateDatetime = DateUtils.convertDateTimeFromServer(data.dataCreateDatetime);
                    data.dataUpdateDatetime = DateUtils.convertDateTimeFromServer(data.dataUpdateDatetime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
