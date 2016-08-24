(function() {
    'use strict';

    angular
        .module('cleandataApp')
        .controller('CategoryDataDeleteController',CategoryDataDeleteController);

    CategoryDataDeleteController.$inject = ['$uibModalInstance', 'entity', 'CategoryData'];

    function CategoryDataDeleteController($uibModalInstance, entity, CategoryData) {
        var vm = this;

        vm.categoryData = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CategoryData.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
