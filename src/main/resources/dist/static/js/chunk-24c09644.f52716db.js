/*!
 *  build: vue-admin-better 
 *  vue-admin-beautiful.com 
 *  https://gitee.com/chu1204505056/vue-admin-better 
 *  time: 2022-9-7 11:40:54
 */
(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-24c09644"],{"05ce":function(s,e,t){"use strict";t("f55f")},"9ed6":function(s,e,t){"use strict";t.r(e);var r=function(){var s=this,e=s.$createElement,t=s._self._c||e;return t("div",{staticClass:"login-container"},[t("el-alert",{staticStyle:{position:"fixed"},attrs:{title:"beautiful boys and girls欢迎加入QQ群：972435319",type:"success",closable:!1}}),t("el-row",[t("el-col",{attrs:{xs:24,sm:24,md:12,lg:16,xl:16}},[t("div",{staticStyle:{color:"transparent"}},[s._v("占位符")])]),t("el-col",{attrs:{xs:24,sm:24,md:12,lg:8,xl:8}},[t("el-form",{ref:"form",staticClass:"login-form",attrs:{model:s.form,rules:s.rules,"label-position":"left"}},[t("div",{staticClass:"title"},[s._v("hello !")]),t("div",{staticClass:"title-tips"},[s._v("欢迎来到"+s._s(s.title)+"！")]),t("el-form-item",{staticStyle:{"margin-top":"40px"},attrs:{prop:"username"}},[t("span",{staticClass:"svg-container svg-container-admin"},[t("vab-icon",{attrs:{icon:["fas","user"]}})],1),t("el-input",{directives:[{name:"focus",rawName:"v-focus"}],attrs:{placeholder:"请输入用户名",tabindex:"1",type:"text"},model:{value:s.form.username,callback:function(e){s.$set(s.form,"username","string"===typeof e?e.trim():e)},expression:"form.username"}})],1),t("el-form-item",{attrs:{prop:"password"}},[t("span",{staticClass:"svg-container"},[t("vab-icon",{attrs:{icon:["fas","lock"]}})],1),t("el-input",{key:s.passwordType,ref:"password",attrs:{type:s.passwordType,tabindex:"2",placeholder:"请输入密码"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&s._k(e.keyCode,"enter",13,e.key,"Enter")?null:s.handleLogin.apply(null,arguments)}},model:{value:s.form.password,callback:function(e){s.$set(s.form,"password","string"===typeof e?e.trim():e)},expression:"form.password"}}),"password"===s.passwordType?t("span",{staticClass:"show-password",on:{click:s.handlePassword}},[t("vab-icon",{attrs:{icon:["fas","eye-slash"]}})],1):t("span",{staticClass:"show-password",on:{click:s.handlePassword}},[t("vab-icon",{attrs:{icon:["fas","eye"]}})],1)],1),t("el-button",{staticClass:"login-btn",attrs:{loading:s.loading,type:"primary"},on:{click:s.handleLogin}},[s._v(" 登录 ")]),t("router-link",{attrs:{to:"/register"}},[t("div",{staticStyle:{"margin-top":"20px"}},[s._v("注册")])])],1)],1)],1)],1)},a=[],o=(t("d9e2"),t("61f7")),i={name:"Login",directives:{focus:{inserted(s){s.querySelector("input").focus()}}},data(){const s=(s,e,t)=>{""==e?t(new Error("用户名不能为空")):t()},e=(s,e,t)=>{Object(o["isPassword"])(e)?t():t(new Error("密码不能少于6位"))};return{nodeEnv:"production",title:this.$baseTitle,form:{username:"",password:""},rules:{username:[{required:!0,trigger:"blur",validator:s}],password:[{required:!0,trigger:"blur",validator:e}]},loading:!1,passwordType:"password",redirect:void 0}},watch:{$route:{handler(s){this.redirect=s.query&&s.query.redirect||"/"},immediate:!0}},created(){document.body.style.overflow="hidden"},beforeDestroy(){document.body.style.overflow="auto"},mounted(){this.form.username="admin",this.form.password="123456",setTimeout(()=>{this.handleLogin()},3e3)},methods:{handlePassword(){"password"===this.passwordType?this.passwordType="":this.passwordType="password",this.$nextTick(()=>{this.$refs.password.focus()})},handleLogin(){this.$refs.form.validate(s=>{if(!s)return!1;this.loading=!0,this.$store.dispatch("user/login",this.form).then(()=>{const s="/404"===this.redirect||"/401"===this.redirect?"/":this.redirect;this.$router.push(s).catch(()=>{}),this.loading=!1}).catch(()=>{this.loading=!1})})}}},n=i,l=(t("05ce"),t("2877")),d=Object(l["a"])(n,r,a,!1,null,"80daa56c",null);e["default"]=d.exports},f55f:function(s,e,t){}}]);