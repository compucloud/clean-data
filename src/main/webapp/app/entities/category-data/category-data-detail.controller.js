(function() {
    'use strict';

    angular
        .module('cleandataApp')
        .controller('CategoryDataDetailController', CategoryDataDetailController);

    CategoryDataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CategoryData'];

    function CategoryDataDetailController($scope, $rootScope, $stateParams, entity, CategoryData) {
        var vm = this;

        vm.categoryData = entity;

        var unsubscribe = $rootScope.$on('cleandataApp:categoryDataUpdate', function(event, result) {
            vm.categoryData = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
