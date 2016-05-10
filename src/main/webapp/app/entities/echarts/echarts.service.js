(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Echarts', Echarts);

    Echarts.$inject = ['$resource', 'DateUtils'];

    function Echarts ($resource, DateUtils) {
        var resourceUrl =  'api/echarts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                isArray: true
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.publicationDate = DateUtils.convertLocalDateToServer(data.publicationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.publicationDate = DateUtils.convertLocalDateToServer(data.publicationDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
