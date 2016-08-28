(function() {
    'use strict';
    angular
        .module('cleandataApp')
        .factory('SubcategoryEntry', SubcategoryEntry);

    SubcategoryEntry.$inject = ['$resource'];

    function SubcategoryEntry ($resource) {
        var resourceUrl =  'api/subcategory-entries/:id';

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
            'save': { method: 'POST', isArray: false}
        });
    }
})();
