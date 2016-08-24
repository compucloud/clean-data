(function() {
    'use strict';

    angular
        .module('cleandataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('category-data', {
            parent: 'entity',
            url: '/category-data?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CategoryData'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/category-data/category-data.html',
                    controller: 'CategoryDataController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('category-data-detail', {
            parent: 'entity',
            url: '/category-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CategoryData'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/category-data/category-data-detail.html',
                    controller: 'CategoryDataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CategoryData', function($stateParams, CategoryData) {
                    return CategoryData.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('category-data.new', {
            parent: 'category-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category-data/category-data-dialog.html',
                    controller: 'CategoryDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                category: null,
                                subcategory: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('category-data', null, { reload: true });
                }, function() {
                    $state.go('category-data');
                });
            }]
        })
        .state('category-data.edit', {
            parent: 'category-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category-data/category-data-dialog.html',
                    controller: 'CategoryDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CategoryData', function(CategoryData) {
                            return CategoryData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('category-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('category-data.delete', {
            parent: 'category-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category-data/category-data-delete-dialog.html',
                    controller: 'CategoryDataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CategoryData', function(CategoryData) {
                            return CategoryData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('category-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
