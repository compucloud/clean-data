(function() {
    'use strict';

    angular
        .module('cleandataApp')
        .controller('SubcategoryEntryDetailController', SubcategoryEntryDetailController);

    SubcategoryEntryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SubcategoryEntry'];

    function SubcategoryEntryDetailController($scope, $rootScope, $stateParams, entity, SubcategoryEntry) {
        var vm = this;

        vm.subcategoryEntry = entity;

        var unsubscribe = $rootScope.$on('cleandataApp:subcategoryEntryUpdate', function(event, result) {
            vm.subcategoryEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
