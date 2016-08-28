(function() {
    'use strict';

    angular
        .module('cleandataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subcategory-entry', {
            parent: 'entity',
            url: '/subcategory-entry?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SubcategoryEntries'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subcategory-entry/subcategory-entries.html',
                    controller: 'SubcategoryEntryController',
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
        .state('subcategory-entry-detail', {
            parent: 'entity',
            url: '/subcategory-entry/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SubcategoryEntry'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subcategory-entry/subcategory-entry-detail.html',
                    controller: 'SubcategoryEntryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SubcategoryEntry', function($stateParams, SubcategoryEntry) {
                    return SubcategoryEntry.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('subcategory-entry.new', {
            parent: 'subcategory-entry',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subcategory-entry/subcategory-entry-dialog.html',
                    controller: 'SubcategoryEntryDialogController',
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
                    $state.go('subcategory-entry', null, { reload: true });
                }, function() {
                    $state.go('subcategory-entry');
                });
            }]
        })
        .state('subcategory-entry.edit', {
            parent: 'subcategory-entry',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subcategory-entry/subcategory-entry-dialog.html',
                    controller: 'SubcategoryEntryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SubcategoryEntry', function(SubcategoryEntry) {
                            return SubcategoryEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subcategory-entry', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subcategory-entry.delete', {
            parent: 'subcategory-entry',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subcategory-entry/subcategory-entry-delete-dialog.html',
                    controller: 'SubcategoryEntryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SubcategoryEntry', function(SubcategoryEntry) {
                            return SubcategoryEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subcategory-entry', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
