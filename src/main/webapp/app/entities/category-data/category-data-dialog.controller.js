(function() {
    'use strict';

    angular
        .module('cleandataApp')
        .controller('CategoryDataDialogController', CategoryDataDialogController);

    CategoryDataDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CategoryData'];

    function CategoryDataDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CategoryData) {
        var vm = this;

        vm.categoryData = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.categoryData.id !== null) {
                CategoryData.update(vm.categoryData, onSaveSuccess, onSaveError);
            } else {
                CategoryData.save(vm.categoryData, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cleandataApp:categoryDataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
