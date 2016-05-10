(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('GridDemoCtrl', GridDemoCtrl);

    GridDemoCtrl.$inject = ['$scope'];

    function GridDemoCtrl ($scope) {
        var vm = this;
        vm.tree_data = [
                            {Name:"USA",Area:9826675,Population:318212000,TimeZone:"UTC -5 to -10",
                             children:[
                               {Name:"California", Area:423970,Population:38340000,TimeZone:"Pacific Time",
                                   children:[
                                       {Name:"San Francisco", Area:231,Population:837442,TimeZone:"PST"},
                                       {Name:"Los Angeles", Area:503,Population:3904657,TimeZone:"PST"}
                                   ]
                               },
                               {Name:"Illinois", Area:57914,Population:12882135,TimeZone:"Central Time Zone",
                                   children:[
                                       {Name:"Chicago", Area:234,Population:2695598,TimeZone:"CST"}
                                   ]
                               }
                           ]
                         },    
                         {Name:"Texas",Area:268581,Population:26448193,TimeZone:"Mountain"}
                         ];
    }
})();
