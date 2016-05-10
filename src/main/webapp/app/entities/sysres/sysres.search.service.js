(function() {
    'use strict';

    angular
        .module('istartApp')
        .factory('SysresSearch', SysresSearch);

    SysresSearch.$inject = ['$resource'];

    function SysresSearch($resource) {
        var resourceUrl =  'api/_search/sysres/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
