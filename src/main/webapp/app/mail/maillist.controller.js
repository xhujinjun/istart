(function() {
	'use strict';

	angular.module('istartApp').controller('MailLIstCtrl', MailLIstCtrl);

	MailLIstCtrl.$inject = [ '$scope','Mails', '$stateParams' ];

	function MailLIstCtrl($scope,Mails, $stateParams) {
		var vm = this;
		vm.mails = '';
		vm.fold = $stateParams.fold;
		console.log('fold',vm.fold);
		Mails.all().then(function(Mails){
		    vm.mails = Mails;
		  });
	}
})();
