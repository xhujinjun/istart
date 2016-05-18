(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('TravelAgencyLineSearch', TravelAgencyLineSearch);

    TravelAgencyLineSearch.$inject = ['$resource'];

    function TravelAgencyLineSearch($resource) {
        var resourceUrl =  'api/_search/travel-agency-lines/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
