(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('NoteController', NoteController);

    NoteController.$inject = ['$http'];

    function NoteController ($http) {
        var vm = this;

        $http.get('app/apps/notes.json').then(function (resp) {
            vm.notes = resp.data.notes;
            // set default note
            vm.note = vm.notes[0];
            vm.notes[0].selected = true;
          });

        vm.colors = ['primary', 'info', 'success', 'warning', 'danger', 'dark'];

        vm.createNote = function(){
            var note = {
              content: 'New note',
              color: vm.colors[Math.floor((Math.random()*3))],
              date: Date.now()
            };
            vm.notes.push(note);
            vm.selectNote(note);
          }

        vm.deleteNote = function(note){
        	vm.notes.splice(vm.notes.indexOf(note), 1);
            if(note.selected){
            	vm.note = vm.notes[0];
            	vm.notes.length && (vm.notes[0].selected = true);
            }
          }

        vm.selectNote = function(note){
            angular.forEach(vm.notes, function(note) {
              note.selected = false;
            });
            vm.note = note;
            vm.note.selected = true;
          }

    }
})();
