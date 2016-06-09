(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('TripSearch', TripSearch);

    TripSearch.$inject = ['$resource'];

    function TripSearch($resource) {
        var resourceUrl =  'api/_search/trips/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
