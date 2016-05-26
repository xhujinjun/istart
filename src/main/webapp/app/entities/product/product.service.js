(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Product', Product);

    Product.$inject = ['$resource', 'DateUtils'];

    function Product ($resource, DateUtils) {
        var resourceUrl =  'api/products/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startdate = DateUtils.convertLocalDateFromServer(data.startdate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startdate = DateUtils.convertLocalDateToServer(data.startdate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startdate = DateUtils.convertLocalDateToServer(data.startdate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
