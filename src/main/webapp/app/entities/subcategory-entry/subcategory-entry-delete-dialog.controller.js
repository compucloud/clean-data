(function() {
    'use strict';

    angular
        .module('cleandataApp')
        .controller('SubcategoryEntryDeleteController',SubcategoryEntryDeleteController);

    SubcategoryEntryDeleteController.$inject = ['$uibModalInstance', 'entity', 'SubcategoryEntry'];

    function SubcategoryEntryDeleteController($uibModalInstance, entity, SubcategoryEntry) {
        var vm = this;

        vm.subcategoryEntry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SubcategoryEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
