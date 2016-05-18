(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('TravelAgency', TravelAgency);

    TravelAgency.$inject = ['$resource', 'DateUtils'];

    function TravelAgency ($resource, DateUtils) {
        var resourceUrl =  'api/travel-agencies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.buildDate = DateUtils.convertLocalDateFromServer(data.buildDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.buildDate = DateUtils.convertLocalDateToServer(data.buildDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.buildDate = DateUtils.convertLocalDateToServer(data.buildDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
