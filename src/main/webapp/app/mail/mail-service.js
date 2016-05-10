(function() {
    'use strict';
    angular
        .module('istartApp')
        .factory('Mails', Mails);

    Mails.$inject = ['$http'];

    function Mails ($http) {
    	var path = 'app/mail/mails.json';
    	console.log('app/mail/mails.json');
    	  var mails = $http.get(path).then(function (resp) {
    	    return resp.data.mails;
    	  });

    	  var factory = {};
    	  factory.all = function () {
    	    return mails;
    	  };
    	  factory.get = function (id) {
    	    return mails.then(function(mails){
    	      for (var i = 0; i < mails.length; i++) {
    	        if (mails[i].id == id) return mails[i];
    	      }
    	      return null;
    	    })
    	  };
    	  return factory;
    }
})();
