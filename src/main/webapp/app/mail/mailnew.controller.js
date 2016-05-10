(function() {
	'use strict';

	angular.module('istartApp').controller('MailNewCtrl', MailNewCtrl);

	MailNewCtrl.$inject = [ '$scope' ];

	function MailNewCtrl($scope) {
		var vm = this;
		vm.mail = {
			    to: '',
			    subject: '',
			    content: ''
			  };
		vm.tolist = [
			    {name: 'James', email:'james@gmail.com'},
			    {name: 'Luoris Kiso', email:'luoris.kiso@hotmail.com'},
			    {name: 'Lucy Yokes', email:'lucy.yokes@gmail.com'}
			  ];
	}
})();
