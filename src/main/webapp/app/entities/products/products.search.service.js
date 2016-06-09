(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('ProductsSearch', ProductsSearch);

    ProductsSearch.$inject = ['$resource'];

    function ProductsSearch($resource) {
        var resourceUrl =  'api/_search/products/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
