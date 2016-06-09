(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('DicDList', DicDList);

    DicDList.$inject = ['$resource', 'DateUtils'];

    function DicDList ($resource, DateUtils) {
        var resourceUrl =  'api/getDicDtolist';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
