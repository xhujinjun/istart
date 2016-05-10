(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('AreaSearch', AreaSearch);

    AreaSearch.$inject = ['$resource'];

    function AreaSearch($resource) {
        var resourceUrl =  'api/_search/areas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
