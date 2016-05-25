(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('HotelSearch', HotelSearch);

    HotelSearch.$inject = ['$resource'];

    function HotelSearch($resource) {
        var resourceUrl =  'api/_search/hotels/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
