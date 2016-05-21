(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('DicType', DicType);

    DicType.$inject = ['$resource', 'DateUtils'];

    function DicType ($resource, DateUtils) {
        var resourceUrl =  'api/dic-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dataCreateDatetime = DateUtils.convertDateTimeFromServer(data.dataCreateDatetime);
                    data.dataUpdateDatetime = DateUtils.convertDateTimeFromServer(data.dataUpdateDatetime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
