(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('PageSearch', PageSearch);

    PageSearch.$inject = ['$resource'];

    function PageSearch($resource) {
        var resourceUrl =  'api/pagesearch/products/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'POST', isArray: true}
        });
    }
})();
