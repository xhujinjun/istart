/*TMODJS:{"version":1,"md5":"919b808edbafed48dea0f54d3e38dab8"}*/
template('footer',function($data,$filename
/**/) {
'use strict';var $utils=this,$helpers=$utils.$helpers,time=$data.time,$escape=$utils.$escape,include=function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);$out+=text;return $out;},$out='';$out+='<div id="footer"> ';
if(time){
$out+=' <p class=\'time\'>';
$out+=$escape(time);
$out+='</p> ';
}
$out+=' ';
include('../copyright');
$out+=' </div>';
return new String($out);
});