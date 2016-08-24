(function() {
    'use strict';

    angular
        .module('cleandataApp')
        .controller('CategoryDetailController', CategoryDetailController);

    CategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Category'];

    function CategoryDetailController($scope, $rootScope, $stateParams, entity, Category) {
        var vm = this;

        vm.category = entity;

        var unsubscribe = $rootScope.$on('cleandataApp:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
