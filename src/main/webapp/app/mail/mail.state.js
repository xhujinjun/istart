(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('mail', {
        	abstract: true,
        	parent: 'app',
            url: '/mail',
            views: {
                'content@': {
                    templateUrl: 'app/mail/mail.html',
                    controller: 'MailCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('list', {
        	parent: 'mail',
            url: '/inbox/{fold}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '电子邮件列表'
            },
            views: {
                '': {
                	templateUrl: 'app/mail/mail.list.html',
                    controller: 'MailLIstCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('detail', {
        	parent: 'mail',
        	url: '/{mailId:[0-9]{1,4}}',
        	data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.author.home.title'
            },
            views: {
                '': {
                	templateUrl: 'app/mail/mail.detail.html',
                    controller: 'MailDetailCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        .state('compose', {
        	parent: 'mail',
            url: '/compose',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'istartApp.author.home.title'
            },
            views: {
                '': {
                	templateUrl: 'app/mail/mail.new.html',
                    controller: 'MailNewCtrl',
                    controllerAs: 'vm'
                }
            }
        })
        ;
    }
})();
