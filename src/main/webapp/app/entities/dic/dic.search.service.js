(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('DicSearch', DicSearch);

    DicSearch.$inject = ['$resource'];

    function DicSearch($resource) {
        var resourceUrl =  'api/_search/dics/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
