/*TMODJS:{"version":"1.0.0"}*/
!function(){function a(a,b){return(/string|function/.test(typeof b)?h:g)(a,b)}function b(a,c){return"string"!=typeof a&&(c=typeof a,"number"===c?a+="":a="function"===c?b(a.call(a)):""),a}function c(a){return l[a]}function d(a){return b(a).replace(/&(?![\w#]+;)|[<>"']/g,c)}function e(a,b){if(m(a))for(var c=0,d=a.length;d>c;c++)b.call(a,a[c],c,a);else for(c in a)b.call(a,a[c],c)}function f(a,b){var c=/(\/)[^\/]+\1\.\.\1/,d=("./"+a).replace(/[^\/]+$/,""),e=d+b;for(e=e.replace(/\/\.\//g,"/");e.match(c);)e=e.replace(c,"/");return e}function g(b,c){var d=a.get(b)||i({filename:b,name:"Render Error",message:"Template not found"});return c?d(c):d}function h(a,b){if("string"==typeof b){var c=b;b=function(){return new k(c)}}var d=j[a]=function(c){try{return new b(c,a)+""}catch(d){return i(d)()}};return d.prototype=b.prototype=n,d.toString=function(){return b+""},d}function i(a){var b="{Template Error}",c=a.stack||"";if(c)c=c.split("\n").slice(0,2).join("\n");else for(var d in a)c+="<"+d+">\n"+a[d]+"\n\n";return function(){return"object"==typeof console&&console.error(b+"\n\n"+c),b}}var j=a.cache={},k=this.String,l={"<":"&#60;",">":"&#62;",'"':"&#34;","'":"&#39;","&":"&#38;"},m=Array.isArray||function(a){return"[object Array]"==={}.toString.call(a)},n=a.utils={$helpers:{},$include:function(a,b,c){return a=f(c,a),g(a,b)},$string:b,$escape:d,$each:e},o=a.helpers=n.$helpers;a.get=function(a){return j[a.replace(/^\.\//,"")]},a.helper=function(a,b){o[a]=b},"function"==typeof define?define(function(){return a}):"undefined"!=typeof exports?module.exports=a:this.template=a,/*v:1*/
a("footer",function(a,b){"use strict";var c=this,d=(c.$helpers,a.time),e=c.$escape,f=function(d,e){e=e||a;var f=c.$include(d,e,b);return g+=f},g="";return g+='<div id="footer"> ',d&&(g+=" <p class='time'>",g+=e(d),g+="</p> "),g+=" ",f("../copyright"),g+=" </div>",new k(g)}),/*v:1*/
a("header",function(a,b){"use strict";var c=this,d=(c.$helpers,function(d,f){f=f||a;var g=c.$include(d,f,b);return e+=g}),e="";return e+=' <div id="header"> ',d("./logo"),e+=' <ul id="nav"> <li><a href="http://www.qq.com">\u9996\u9875</a></li> <li><a href="http://news.qq.com/">\u65b0\u95fb</a></li> <li><a href="http://pp.qq.com/">\u56fe\u7247</a></li> <li><a href="http://mil.qq.com/">\u519b\u4e8b</a></li> </ul> </div>  ',new k(e)}),/*v:1*/
a("logo",' <h1 id="logo"> <a href="http://www.qq.com"> <img width=\'134\' height=\'44\' src="http://mat1.gtimg.com/www/images/qq2012/qqlogo_1x.png" alt="\u817e\u8baf\u7f51" /> </a> </h1> ')}();