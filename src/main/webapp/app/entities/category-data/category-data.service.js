(function() {
    'use strict';
    angular
        .module('cleandataApp')
        .factory('CategoryData', CategoryData);

    CategoryData.$inject = ['$resource'];

    function CategoryData ($resource) {
        var resourceUrl =  'api/category-data/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'save': { method: 'POST', isArray: true}
        });
    }
    
    function CategoryDataCount ($resource) {
        var resourceUrl =  'api/category-data-count';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}            
        });
    }
})();
