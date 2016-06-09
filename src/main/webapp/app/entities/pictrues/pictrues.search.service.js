(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('PictruesSearch', PictruesSearch);

    PictruesSearch.$inject = ['$resource'];

    function PictruesSearch($resource) {
        var resourceUrl =  'api/_search/pictrues/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
