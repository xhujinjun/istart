(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('ScenicSpotSearch', ScenicSpotSearch);

    ScenicSpotSearch.$inject = ['$resource'];

    function ScenicSpotSearch($resource) {
        var resourceUrl =  'api/_search/scenic-spots/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
