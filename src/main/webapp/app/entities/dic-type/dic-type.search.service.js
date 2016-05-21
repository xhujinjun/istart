(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('DicTypeSearch', DicTypeSearch);

    DicTypeSearch.$inject = ['$resource'];

    function DicTypeSearch($resource) {
        var resourceUrl =  'api/_search/dic-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
