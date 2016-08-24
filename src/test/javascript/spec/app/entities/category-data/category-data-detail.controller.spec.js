'use strict';

describe('Controller Tests', function() {

    describe('CategoryData Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCategoryData;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCategoryData = jasmine.createSpy('MockCategoryData');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CategoryData': MockCategoryData
            };
            createController = function() {
                $injector.get('$controller')("CategoryDataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cleandataApp:categoryDataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
