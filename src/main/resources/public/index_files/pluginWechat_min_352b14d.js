define("pluginWechat",function(e,t,n){(function(){var e,t;t=window,e=function(){function e(e){var n,r,o,i;n=null!=(r=e.type)?r:"test",-1===["test","beta"].indexOf(n)&&(n=""),this.auth_host="http://"+n+"weixin.killerwhale.cn",this.appid=e.appid,this.scene=e.scene,this.scene_type=e.scene_type,this.scope=e.scope,this.redirect_url=e.redirect_url||t.location.href,this.onReady=null!=(o=e.onReady)?o:this.EMPTY_FN,this.onError=null!=(i=e.onError)?i:this.EMPTY_FN}return e.prototype.EMPTY_FN=function(){},e.prototype.AUTH_URI="/service/wechat/checkpluginauth",e.prototype.isMobile=function(){var e,n,r,o;return e=(null!=(o=t.navigator)?o.userAgent:void 0)||"",n=/mobile|nokia|.*iPhone.*|android|samsung|htc|blackberry|windows phone|LG|SonyEricsson|MOT|opera mini|j2me|mqqbrowser|lenovo|uc( )?web|symbian/i.test(e),n===!0?n:(r=t.location.search||"",-1!==r.indexOf("mobile="))},e.prototype.isWechat=function(){var e,n,r;return n=this.isMobile(),n!==!0?n:(e=(null!=(r=t.navigator)?r.userAgent:void 0)||"",n=-1!==e.toLowerCase().indexOf("micromessenger"))},e.prototype.jsonp=function(e,n,r){var o,i,s=this;return o=document.createElement("script"),o.type="text/javascript",i="cb_"+(new Date).getTime()+(1e3*Math.random()).toFixed(0),t[i]=function(e){return null!=n&&n.call(s,e),t[i]=null,document.head.removeChild(o)},o.onload=function(){},o.onerror=function(e){return null!=r&&r.call(s,e),t[i]=null,document.head.removeChild(o)},e=""+e+"&callback="+i,o.src=e,document.head.appendChild(o)},e.prototype.checkAuth=function(e){var t,n=this;return t=this.getAuthSrc(),this.jsonp(t,function(t){return 0!==t.errcode?e.call(n,new Error(""+t.message)):e.call(n,null,t.payload)},function(t){return e(t)})},e.prototype.getAuthSrc=function(){var e,t,n,r;n={appid:this.appid,scene:this.scene,scene_type:this.scene_type,scope:this.scope,redirect_url:encodeURIComponent(this.redirect_url)},e=[];for(t in n)r=n[t],r&&e.push(""+t+"="+r);return""+this.auth_host+this.AUTH_URI+"?"+e.join("&")},e.prototype.auth=function(){var e,t,n,r,o=this;return n=this.redirect_url,e=this.appid,r=this.scene,this.isWechat()!==!0?(t=new Error("must be wechat environment."),this.onError(t)):n?e||r?this.checkAuth(function(e,t){return null!=e||null==t?o.onError(e):null!=t.auth_url?window.top.location.href=t.auth_url:o.onReady(t)}):(t=new Error("appid or scene is not exist."),this.onError(t)):(t=new Error("redirect_url is not exist."),this.onError(t))},e}(),t.PluginWechat=e,n.exports=e}).call(this)});