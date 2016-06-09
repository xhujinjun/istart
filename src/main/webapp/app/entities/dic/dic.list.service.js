(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('DicList', DicList);

    DicList.$inject = ['$resource', 'DateUtils'];

    function DicList ($resource, DateUtils) {
        var resourceUrl =  'api/getDiclist';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
