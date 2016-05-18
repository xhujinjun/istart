(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('TravelAgencySearch', TravelAgencySearch);

    TravelAgencySearch.$inject = ['$resource'];

    function TravelAgencySearch($resource) {
        var resourceUrl =  'api/_search/travel-agencies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
