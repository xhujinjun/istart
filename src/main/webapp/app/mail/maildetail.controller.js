(function() {
	'use strict';

	angular.module('istartApp').controller('MailDetailCtrl', MailDetailCtrl);

	MailDetailCtrl.$inject = [ '$scope','Mails', '$stateParams' ];

	function MailDetailCtrl($scope,Mails, $stateParams) {
		var vm = this;
		Mails.get($stateParams.mailId).then(function(mail){
			console.log('mail',mail);
		    vm.mail = mail;
		});
	}
})();
