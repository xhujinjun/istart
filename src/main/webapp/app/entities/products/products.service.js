(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Products', Products);

    Products.$inject = ['$resource', 'DateUtils'];

    function Products ($resource, DateUtils) {
        var resourceUrl =  'api/products/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startdate = DateUtils.convertLocalDateFromServer(data.startdate);
                        data.dataCreateDatetime = DateUtils.convertLocalDateFromServer(data.dataCreateDatetime);
                        data.dataUpdateDatetime = DateUtils.convertLocalDateFromServer(data.dataUpdateDatetime);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startdate = DateUtils.convertLocalDateToServer(data.startdate);
                    data.dataCreateDatetime = DateUtils.convertLocalDateToServer(data.dataCreateDatetime);
                    data.dataUpdateDatetime = DateUtils.convertLocalDateToServer(data.dataUpdateDatetime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startdate = DateUtils.convertLocalDateToServer(data.startdate);
                    data.dataCreateDatetime = DateUtils.convertLocalDateToServer(data.dataCreateDatetime);
                    data.dataUpdateDatetime = DateUtils.convertLocalDateToServer(data.dataUpdateDatetime);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
