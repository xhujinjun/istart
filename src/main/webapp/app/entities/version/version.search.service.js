(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('VersionSearch', VersionSearch);

    VersionSearch.$inject = ['$resource'];

    function VersionSearch($resource) {
        var resourceUrl =  'api/_search/versions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
