'use strict';

describe('Controller Tests', function() {

    describe('SubcategoryEntry Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubcategoryEntry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubcategoryEntry = jasmine.createSpy('MockSubcategoryEntry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SubcategoryEntry': MockSubcategoryEntry
            };
            createController = function() {
                $injector.get('$controller')("SubcategoryEntryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cleandataApp:subcategoryEntryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
