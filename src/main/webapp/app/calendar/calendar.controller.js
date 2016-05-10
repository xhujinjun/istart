(function() {
    'use strict';

    angular
        .module('istartApp')
        .controller('FullcalendarController', FullcalendarController);

    FullcalendarController.$inject = ['$scope'];

    function FullcalendarController ($scope) {
    	var vm = this;
    	var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        /* event source that pulls from google.com */
        vm.eventSource = {
                url: "http://www.google.com/calendar/feeds/usa__en%40holiday.calendar.google.com/public/basic",
                className: 'gcal-event',           // an option!
                currentTimezone: 'America/Chicago' // an option!
        };

        /* event source that contains custom events on the scope */
        vm.events = [
          {title:'All Day Event', start: new Date(y, m, 1), className: ['b-l b-2x b-info'], location:'New York', info:'This a all day event that will start from 9:00 am to 9:00 pm, have fun!'},
          {title:'Dance class', start: new Date(y, m, 3), end: new Date(y, m, 4, 9, 30), allDay: false, className: ['b-l b-2x b-danger'], location:'London', info:'Two days dance training class.'},
          {title:'Game racing', start: new Date(y, m, 6, 16, 0), className: ['b-l b-2x b-info'], location:'Hongkong', info:'The most big racing of this year.'},
          {title:'Soccer', start: new Date(y, m, 8, 15, 0), className: ['b-l b-2x b-info'], location:'Rio', info:'Do not forget to watch.'},
          {title:'Family', start: new Date(y, m, 9, 19, 30), end: new Date(y, m, 9, 20, 30), className: ['b-l b-2x b-success'], info:'Family party'},
          {title:'Long Event', start: new Date(y, m, d - 5), end: new Date(y, m, d - 2), className: ['bg-success bg'], location:'HD City', info:'It is a long long event'},
          {title:'Play game', start: new Date(y, m, d - 1, 16, 0), className: ['b-l b-2x b-info'], location:'Tokyo', info:'Tokyo Game Racing'},
          {title:'Birthday Party', start: new Date(y, m, d + 1, 19, 0), end: new Date(y, m, d + 1, 22, 30), allDay: false, className: ['b-l b-2x b-primary'], location:'New York', info:'Party all day'},
          {title:'Repeating Event', start: new Date(y, m, d + 4, 16, 0), alDay: false, className: ['b-l b-2x b-warning'], location:'Home Town', info:'Repeat every day'},      
          {title:'Click for Google', start: new Date(y, m, 28), end: new Date(y, m, 29), url: 'http://google.com/', className: ['b-l b-2x b-primary']},
          {title:'Feed cat', start: new Date(y, m+1, 6, 18, 0), className: ['b-l b-2x b-info']}
        ];

        /* alert on dayClick */
        vm.precision = 400;
        vm.lastClickTime = 0;
        vm.alertOnEventClick = function( date, jsEvent, view ){
          var time = new Date().getTime();
          if(time - vm.lastClickTime <= vm.precision){
              vm.events.push({
                title: 'New Event',
                start: date,
                className: ['b-l b-2x b-info']
              });
          }
          vm.lastClickTime = time;
        };
        /* alert on Drop */
        vm.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
           vm.alertMessage = ('Event Droped to make dayDelta ' + delta);
        };
        /* alert on Resize */
        vm.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view){
           vm.alertMessage = ('Event Resized to make dayDelta ' + delta);
        };

        vm.overlay = $('.fc-overlay');
        vm.alertOnMouseOver = function( event, jsEvent, view ){
          vm.event = event;
          vm.overlay.removeClass('left right').find('.arrow').removeClass('left right top pull-up');
          var wrap = $(jsEvent.target).closest('.fc-event');
          var cal = wrap.closest('.calendar');
          var left = wrap.offset().left - cal.offset().left;
          var right = cal.width() - (wrap.offset().left - cal.offset().left + wrap.width());
          if( right > vm.overlay.width() ) { 
            vm.overlay.addClass('left').find('.arrow').addClass('left pull-up')
          }else if ( left > vm.overlay.width() ) {
            vm.overlay.addClass('right').find('.arrow').addClass('right pull-up');
          }else{
            vm.overlay.find('.arrow').addClass('top');
          }
          (wrap.find('.fc-overlay').length == 0) && wrap.append( vm.overlay );
        }

        /* config object */
        vm.uiConfig = {
          calendar:{
            height: 450,
            editable: true,
            header:{
              left: 'prev',
              center: 'title',
              right: 'next'
            },
            dayClick: vm.alertOnEventClick,
            eventDrop: vm.alertOnDrop,
            eventResize: vm.alertOnResize,
            eventMouseover: vm.alertOnMouseOver
          }
        };
        
        /* add custom event*/
        vm.addEvent = function() {
          vm.events.push({
            title: 'New Event',
            start: new Date(y, m, d),
            className: ['b-l b-2x b-info']
          });
        };

        /* remove event */
        vm.remove = function(index) {
          vm.events.splice(index,1);
        };

        /* Change View */
        vm.changeView = function(view, calendar) {
          $('.calendar').fullCalendar('changeView', view);
        };

        vm.today = function(calendar) {
          $('.calendar').fullCalendar('today');
        };

        /* event sources array*/
        vm.eventSources = [vm.events];
        
        /*汉化*/
        vm.uiConfig.calendar.buttonText = {
            today: '今日',
            day: '日视图',
            week: '周视图',
            month: '月视图'
        };
        vm.uiConfig.calendar.allDayText = '全天';
        vm.uiConfig.calendar.timeFormat = 'h:mm';
        vm.uiConfig.calendar.weekMode = "variable";
        vm.uiConfig.calendar.columnFormat = {
            month: 'dddd',
            week: 'dddd M/D',
            day: 'dddd M/D'
        };
         vm.uiConfig.calendar.titleFormat = {
            month: 'YYYY年 MMMM月',
            week: "YYYY年  MMMM月D日",
            day: 'YYYY年 MMMM月D日 dddd'
        };
        vm.uiConfig.calendar.monthNames = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"];
    //  vm.uiConfig.calendar.dayNames = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        vm.uiConfig.calendar.dayNames = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
        vm.uiConfig.calendar.header = {
            left: 'prev,next today',
            center: 'title',
            right: 'agendaDay,agendaWeek,month'
        };
        vm.uiConfig.calendar.firstDay = 1;
    }
})();
