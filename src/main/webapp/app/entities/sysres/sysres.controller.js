(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('SysresController', SysresController);

    SysresController.$inject = ['$scope','$timeout', '$state', 'Sysres', 'SysresSearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];
    /**
     * 
     */
    function SysresController ($scope,$timeout, $state, Sysres, SysresSearch, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.clear = clear;
        vm.search = search;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
        vm.loadAll();
        
        vm.my_tree_handler = function(branch){
        	var _ref;
        	vm.output = "You selected: " + branch.label;
        	if ((_ref = branch.data) != null ? _ref.description : void 0) {
                return vm.output += '(' + branch.data.description + ')';
              }
        };
        vm.apple_selected = function(branch) {
            return vm.output = "APPLE! : " + branch.label;
        };
        vm.my_data = [
                      {
                          label: 'Animal',
                          children: [
                            {
                              label: 'Dog',
                              data: {
                                description: "man's best friend"
                              }
                            }, {
                              label: 'Cat',
                              data: {
                                description: "Felis catus"
                              }
                            }, {
                              label: 'Hippopotamus',
                              data: {
                                description: "hungry, hungry"
                              }
                            }, {
                              label: 'Chicken',
                              children: ['White Leghorn', 'Rhode Island Red', 'Jersey Giant']
                            }
                          ]
                        }, {
                          label: 'Vegetable',
                          data: {
                            definition: "A plant or part of a plant used as food, typically as accompaniment to meat or fish, such as a cabbage, potato, carrot, or bean.",
                            data_can_contain_anything: true
                          },
                          onSelect: function(branch) {
                            return vm.output = "Vegetable: " + branch.data.definition;
                          },
                          children: [
                            {
                              label: 'Oranges'
                            }, {
                              label: 'Apples',
                              children: [
                                {
                                  label: 'Granny Smith',
                                  onSelect: vm.apple_selected
                                }, {
                                  label: 'Red Delicous',
                                  onSelect: vm.apple_selected
                                }, {
                                  label: 'Fuji',
                                  onSelect: vm.apple_selected
                                }
                              ]
                            }
                          ]
                        }, {
                          label: 'Mineral',
                          children: [
                            {
                              label: 'Rock',
                              children: ['Igneous', 'Sedimentary', 'Metamorphic']
                            }, {
                              label: 'Metal',
                              children: ['Aluminum', 'Steel', 'Copper']
                            }, {
                              label: 'Plastic',
                              children: [
                                {
                                  label: 'Thermoplastic',
                                  children: ['polyethylene', 'polypropylene', 'polystyrene', ' polyvinyl chloride']
                                }, {
                                  label: 'Thermosetting Polymer',
                                  children: ['polyester', 'polyurethane', 'vulcanized rubber', 'bakelite', 'urea-formaldehyde']
                                }
                              ]
                            }
                          ]
                        }
                      ];
        vm.my_tree = {};
        vm.my_tree_handler = function(branch) {
            var _ref;
            vm.output = "You selected: " + branch.label;
            if ((_ref = branch.data) != null ? _ref.description : void 0) {
              return vm.output += '(' + branch.data.description + ')';
            }
          };
          
          
          
        function loadAll () {
            if (pagingParams.search) {
                SysresSearch.query({
                    query: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                Sysres.query({
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.sysres = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function search (searchQuery) {
            if (!searchQuery){
                return vm.clear();
            }
            vm.links = null;
            vm.page = 1;
            vm.predicate = '_score';
            vm.reverse = false;
            vm.currentSearch = searchQuery;
            vm.transition();
        }

        function clear () {
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.currentSearch = null;
            vm.transition();
        }

    }
})();
