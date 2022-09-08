/*!
 *  build: vue-admin-better 
 *  vue-admin-beautiful.com 
 *  https://gitee.com/chu1204505056/vue-admin-better 
 *  time: 2022-9-7 11:40:54
 */
(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1b451e6e"],{3915:function(e,t,o){"use strict";o.r(t);var r=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("el-dialog",{attrs:{title:e.title,visible:e.dialogFormVisible,width:"500px"},on:{"update:visible":function(t){e.dialogFormVisible=t},close:e.close}},[o("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"80px"}},[o("el-form-item",{attrs:{label:"客房id",prop:"spuId"}},[o("el-input",{attrs:{readonly:!0,autocomplete:"off"},model:{value:e.form.spuId,callback:function(t){e.$set(e.form,"spuId","string"===typeof t?t.trim():t)},expression:"form.spuId"}})],1),o("el-form-item",{attrs:{label:"店名",prop:"storeId"}},[o("el-select",{attrs:{placeholder:"请选择房间分类"},model:{value:e.form.storeId,callback:function(t){e.$set(e.form,"storeId",t)},expression:"form.storeId"}},e._l(e.storeOptions,(function(e){return o("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),o("el-form-item",{attrs:{label:"客房编码",prop:"roomNum"}},[o("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.roomNum,callback:function(t){e.$set(e.form,"roomNum","string"===typeof t?t.trim():t)},expression:"form.roomNum"}})],1),o("el-form-item",{attrs:{label:"客房名称",prop:"title"}},[o("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.title,callback:function(t){e.$set(e.form,"title","string"===typeof t?t.trim():t)},expression:"form.title"}})],1),o("el-form-item",{attrs:{label:"分类",prop:"type"}},[o("el-select",{attrs:{placeholder:"请选择房间分类"},model:{value:e.form.type,callback:function(t){e.$set(e.form,"type",t)},expression:"form.type"}},e._l(e.typeOptions,(function(e){return o("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),o("el-form-item",{attrs:{label:"价格",prop:"price"}},[o("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.price,callback:function(t){e.$set(e.form,"price",e._n(t))},expression:"form.price"}})],1),o("el-form-item",{attrs:{label:"划线价格",prop:"originPrice"}},[o("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.originPrice,callback:function(t){e.$set(e.form,"originPrice",e._n(t))},expression:"form.originPrice"}})],1),o("el-form-item",{attrs:{label:"图片",prop:"thumb"}},[o("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.thumb,callback:function(t){e.$set(e.form,"thumb","string"===typeof t?t.trim():t)},expression:"form.thumb"}})],1)],1),o("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{on:{click:e.close}},[e._v("取 消")]),o("el-button",{attrs:{type:"primary"},on:{click:e.save}},[e._v("确 定")])],1)],1)},l=[],s=o("ad8f"),a={name:"TableEdit",data(){return{form:{spuId:"",roomNum:"",roomName:"",roomPrice:"",roomOriginPrice:"",imageUrl:"",storeId:"",storeName:"",type:"",thumb:"",title:""},storeOptions:[{value:1,label:"红心农家乐"},{value:2,label:"红兴农家乐"}],typeOptions:[{value:1,label:"单间大床房"},{value:2,label:"家庭房1孩"},{value:3,label:"家庭房2孩"},{value:4,label:"双人标准间"},{value:5,label:"三人间"},{value:6,label:"4人间麻将房"},{value:7,label:"双大床房"}],rules:{title:[{required:!0,trigger:"blur",message:"请输入标题"}],roomNum:[{required:!0,trigger:"blur",message:"请输入客房编码"}],roomName:[{required:!0,trigger:"blur",message:"请输入客房名称"}],storeId:[{required:!0,trigger:"change",message:"请输入店名"}],type:[{required:!0,message:"请选择分类",trigger:"change"}],price:[{required:!0,trigger:"blur",message:"请输入价格",type:"number"}],originPrice:[{required:!0,trigger:"blur",message:"请输入客房划线价格",type:"number"}],thumb:[{required:!0,trigger:"blur",message:"请输入客房图片"}]},title:"",dialogFormVisible:!1,operationType:""}},created(){},methods:{showEdit(e){e?(this.title="编辑",this.operationType="update",this.form=Object.assign({},e)):(this.title="添加",this.operationType="add"),this.dialogFormVisible=!0},close(){this.$refs["form"].resetFields(),this.form=this.$options.data().form,this.dialogFormVisible=!1,this.$emit("fetch-data")},save(){this.$refs["form"].validate(async e=>{if(!e)return!1;{let e={};"add"==this.operationType?(e=await Object(s["doCreate"])(this.form),"Success"===e.code?this.$baseMessage("创建成功","success"):this.$baseMessage("创建失败","error")):"update"==this.operationType&&(e=await Object(s["doEdit"])(this.form),"Success"===e.code?this.$baseMessage("修改成功","success"):this.$baseMessage("修改失败","error")),this.$emit("fetch-data"),this.dialogFormVisible=!1}})}}},i=a,n=o("2877"),u=Object(n["a"])(i,r,l,!1,null,null,null);t["default"]=u.exports},ad8f:function(e,t,o){"use strict";o.r(t),o.d(t,"getList",(function(){return l})),o.d(t,"doEdit",(function(){return s})),o.d(t,"doCreate",(function(){return a})),o.d(t,"doShelves",(function(){return i}));var r=o("b775");function l(e){return Object(r["default"])({url:"https://springboot-krih-3055-4-1313299760.sh.run.tcloudbase.com/room/list",method:"post",data:e})}function s(e){return Object(r["default"])({url:"https://springboot-krih-3055-4-1313299760.sh.run.tcloudbase.com/room/update",method:"post",data:e})}function a(e){return Object(r["default"])({url:"https://springboot-krih-3055-4-1313299760.sh.run.tcloudbase.com/room/create",method:"post",data:e})}function i(e){return Object(r["default"])({url:"https://springboot-krih-3055-4-1313299760.sh.run.tcloudbase.com/room/roomShelves",method:"post",data:e})}},d0b0:function(e,t,o){"use strict";o.r(t);var r=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"table-container"},[o("vab-query-form",[o("vab-query-form-left-panel",[o("el-button",{attrs:{icon:"el-icon-plus",type:"primary"},on:{click:e.handleAdd}},[e._v(" 添加 ")])],1),o("vab-query-form-right-panel",[o("el-form",{ref:"form",attrs:{model:e.queryForm,inline:!0},nativeOn:{submit:function(e){e.preventDefault()}}},[o("el-form-item",[o("el-input",{attrs:{placeholder:"标题"},model:{value:e.queryForm.title,callback:function(t){e.$set(e.queryForm,"title",t)},expression:"queryForm.title"}})],1),o("el-form-item",[o("el-button",{attrs:{icon:"el-icon-search",type:"primary","native-type":"submit"},on:{click:e.handleQuery}},[e._v(" 查询 ")])],1)],1)],1)],1),o("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],ref:"tableSort",attrs:{"element-loading-text":e.elementLoadingText,height:e.height,data:e.list},on:{"selection-change":e.setSelectRows,"sort-change":e.tableSortChange}},[o("el-table-column",{attrs:{"show-overflow-tooltip":"",type:"selection",width:"55"}}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",label:"id",prop:"spuId",width:"55"}}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",prop:"storeName",label:"店名",width:"80"}}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",prop:"roomNum",label:"客房编码"}}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",prop:"title",label:"客房名称",width:"100"}}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",label:"分类",prop:"typeString"}}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",label:"上下架",prop:"roomShelvesString"}}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",label:"图片"},scopedSlots:e._u([{key:"default",fn:function(t){var r=t.row;return[e.imgShow?o("el-image",{attrs:{"preview-src-list":e.imageList,src:r.thumb}}):e._e()]}}])}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",label:"价格",prop:"price",sortable:""}}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",label:"划线价格",prop:"originPrice",sortable:""}}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",label:"状态"},scopedSlots:e._u([{key:"default",fn:function(t){var r=t.row;return[o("el-tooltip",{staticClass:"item",attrs:{content:r.roomStatusString,effect:"dark",placement:"top-start"}},[0===r.roomStatus?o("el-tag",{attrs:{type:"warning",size:"medium"}},[e._v(" "+e._s(r.roomStatusString)+" ")]):e._e(),1===r.roomStatus?o("el-tag",{attrs:{type:"success",size:"medium"}},[e._v(" "+e._s(r.roomStatusString)+" ")]):e._e()],1)]}}])}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",label:"创建时间",prop:"createTime",width:"200"}}),o("el-table-column",{attrs:{"show-overflow-tooltip":"",label:"操作",width:"180px"},scopedSlots:e._u([{key:"default",fn:function(t){var r=t.row;return[o("el-button",{attrs:{type:"text"},on:{click:function(t){return e.handleEdit(r)}}},[e._v("编辑")]),o("el-button",{attrs:{type:"text"},on:{click:function(t){return e.handleOn(r)}}},[e._v("上架")]),o("el-button",{attrs:{type:"text"},on:{click:function(t){return e.handleOff(r)}}},[e._v("下架")])]}}])})],1),o("el-pagination",{attrs:{background:e.background,"current-page":e.queryForm.pageNum,layout:e.layout,"page-size":e.queryForm.pageSize,total:e.total},on:{"current-change":e.handleCurrentChange,"size-change":e.handleSizeChange}}),o("table-edit",{ref:"edit",on:{"fetch-data":e.fetchData}})],1)},l=[],s=o("ad8f"),a=o("3915"),i={name:"ComprehensiveTable",components:{TableEdit:a["default"]},filters:{statusFilter(e){const t={1:"success",0:"info"};return t[e]}},data(){return{imgShow:!0,list:[],imageList:[],listLoading:!0,layout:"total, sizes, prev, pager, next, jumper",total:0,background:!0,selectRows:"",elementLoadingText:"正在加载...",queryForm:{pageNum:1,pageSize:20,title:"",fromApplet:2}}},computed:{height(){return this.$baseTableHeight()}},created(){this.fetchData()},beforeDestroy(){},mounted(){},methods:{tableSortChange(){const e=[];this.$refs.tableSort.tableData.forEach((t,o)=>{e.push(t.img)}),this.imageList=e},setSelectRows(e){this.selectRows=e},handleAdd(){this.$refs["edit"].showEdit()},handleEdit(e){this.$refs["edit"].showEdit(e)},async handleOn(e){const t={id:e.spuId,roomShelves:1},o=await Object(s["doShelves"])(t);"Success"===o.code?this.$baseMessage("上架成功","success"):this.$baseMessage("上架失败","error"),this.fetchData()},async handleOff(e){const t={id:e.spuId,roomShelves:2},o=await Object(s["doShelves"])(t);"Success"===o.code?this.$baseMessage("下架成功","success"):this.$baseMessage("下架失败","error"),this.fetchData()},handleSizeChange(e){this.queryForm.pageSize=e,this.fetchData()},handleCurrentChange(e){this.queryForm.pageNum=e,this.fetchData()},handleQuery(){this.queryForm.pageNum=1,this.fetchData()},async fetchData(){this.listLoading=!0;const{data:e}=await Object(s["getList"])(this.queryForm),{rooms:t,totalCount:o}=e;this.list=t;const r=[];t.forEach((e,t)=>{r.push(e.thumb)}),this.imageList=r,this.total=o,setTimeout(()=>{this.listLoading=!1},500)},testMessage(){this.$baseMessage("test1","success")},testALert(){this.$baseAlert("11"),this.$baseAlert("11","自定义标题",()=>{}),this.$baseAlert("11",null,()=>{})},testConfirm(){this.$baseConfirm("你确定要执行该操作?",null,()=>{},()=>{})},testNotify(){this.$baseNotify("测试消息提示","test","success","bottom-right")}}},n=i,u=o("2877"),c=Object(u["a"])(n,r,l,!1,null,null,null);t["default"]=c.exports}}]);