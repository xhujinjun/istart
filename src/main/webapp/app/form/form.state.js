(function() {
    'use strict';

    angular
        .module('istartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('form', {
        	abstract: true,
        	parent: 'app',
            url: '/form',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'form'
            }
        }).state('form.elements', {
        	parent: 'form',
            url: '/elements',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '个人中心'
            },
            views: {
                'content@': {
                	templateUrl: 'app/form/form_elements.html'
                }
            }
        }).state('form.validation', {
        	parent: 'form',
            url: '/validation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '博客'
            },
            views: {
                'content@': {
                	templateUrl: 'app/form/form_validation.html'
                }
            }
        }).state('form.wizard', {
        	parent: 'form',
            url: '/wizard',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/form/form_wizard.html'
                }
            }
        }).state('form.fileupload', {
        	parent: 'form',
            url: '/fileupload',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/form/form_fileupload.html'
                }
            }
        }).state('form.imagecrop', {
        	parent: 'form',
            url: '/imagecrop',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/form/form_imagecrop.html'
                }
            }
        }).state('form.select', {
        	parent: 'form',
            url: '/select',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/form/form_select.html'
                }
            }
        }).state('form.slider', {
        	parent: 'form',
            url: '/slider',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/form/form_slider.html'
                }
            }
        }).state('form.editor', {
        	parent: 'form',
            url: '/editor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '检索'
            },
            views: {
                'content@': {
                	templateUrl: 'app/form/form_editor.html'
                }
            }
        }).state('form.mark', {
        	parent: 'form',
            url: '/mark',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mark'
            },
            views: {
                'content@': {
                	templateUrl: 'app/form/form_mark.html'
                }
            }
        }).state('form.ueditor', {
        	parent: 'form',
            url: '/ueditor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mark'
            },
            views: {
                'content@': {
                	templateUrl: 'app/form/form_ueditor.html'
                }
            }
        })
        ;
    }
})();
