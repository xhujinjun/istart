(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('ScenicAreaSearch', ScenicAreaSearch);

    ScenicAreaSearch.$inject = ['$resource'];

    function ScenicAreaSearch($resource) {
        var resourceUrl =  'api/_search/scenic-areas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
