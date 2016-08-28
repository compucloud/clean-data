(function() {
    'use strict';

    angular
        .module('cleandataApp')
        .controller('SubcategoryEntryDialogController', SubcategoryEntryDialogController);

    SubcategoryEntryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubcategoryEntry'];

    function SubcategoryEntryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SubcategoryEntry) {
        var vm = this;

        vm.subcategoryEntry = entity;
        vm.clear = clear;
        vm.save = save;
        vm.closePopup = closePopup;

        vm.add = add;
        vm.saveList = saveList;
        vm.subcategoryEntryList = [];
        vm.editMode = true;
        vm.subcategoryDTO = {};



        function add () {
            vm.subcategoryEntryList.push(vm.subcategoryEntry);
            vm.subcategoryEntry = null;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function closePopup () {
          $uibModalInstance.close();
        }

        function save () {
            vm.isSaving = true;
            if (vm.subcategoryEntry.id !== null) {
                SubcategoryEntry.update(vm.subcategoryEntry, onSaveSuccess, onSaveError);
            } else {
                SubcategoryEntry.save(vm.subcategoryEntry, onSaveSuccess, onSaveError);
            }
        }

        function saveList () {
            vm.isSaving = true;
            if (vm.subcategoryEntry.id !== null) {
                SubcategoryEntry.update(vm.subcategoryEntry, onSaveSuccess, onSaveError);
            } else {
                SubcategoryEntry.save(vm.subcategoryEntryList, onSaveSuccess, onSaveError);
            }
        }

        function saveList () {
            vm.isSaving = true;
            SubcategoryEntry.save(vm.subcategoryEntryList, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('cleandataApp:subcategoryEntryUpdate', result);
            //$uibModalInstance.close(result);
            vm.subcategoryDTO = result;
            vm.isSaving = false;
            vm.editMode = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
