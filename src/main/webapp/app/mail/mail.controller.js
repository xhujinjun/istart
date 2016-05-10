(function() {
	'use strict';

	angular.module('istartApp').controller('MailCtrl', MailCtrl);

	MailCtrl.$inject = [ '$scope' ];

	function MailCtrl($scope) {
		var vm = this;
		vm.folds = [ {
			name : 'Inbox',
			filter : ''
		}, {
			name : 'Starred',
			filter : 'starred'
		}, {
			name : 'Sent',
			filter : 'sent'
		}, {
			name : 'Important',
			filter : 'important'
		}, {
			name : 'Draft',
			filter : 'draft'
		}, {
			name : 'Trash',
			filter : 'trash'
		} ];

		vm.labels = [ {
			name : 'Angular',
			filter : 'angular',
			color : '#23b7e5'
		}, {
			name : 'Bootstrap',
			filter : 'bootstrap',
			color : '#7266ba'
		}, {
			name : 'Client',
			filter : 'client',
			color : '#fad733'
		}, {
			name : 'Work',
			filter : 'work',
			color : '#27c24c'
		} ];

		vm.addLabel = function() {
			vm.labels.push({
				name : $scope.newLabel.name,
				filter : angular.lowercase($scope.newLabel.name),
				color : '#ccc'
			});
			$scope.newLabel.name = '';
		}

		vm.labelClass = function(label) {
			return {
				'b-l-info' : angular.lowercase(label) === 'angular',
				'b-l-primary' : angular.lowercase(label) === 'bootstrap',
				'b-l-warning' : angular.lowercase(label) === 'client',
				'b-l-success' : angular.lowercase(label) === 'work'
			};
		};
	}
})();
