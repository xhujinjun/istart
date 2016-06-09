(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('ProductsUpdate', ProductsUpdate);

    ProductsUpdate.$inject = ['$resource'];

    function ProductsUpdate($resource) {
        var resourceUrl =  'api/saveproducts';

        return $resource(resourceUrl, {}, {
            'update': { method: 'post'}
        });
    }
})();
