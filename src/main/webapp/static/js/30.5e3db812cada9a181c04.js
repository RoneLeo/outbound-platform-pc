webpackJsonp([30],{kQc2:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});a("nznj"),a("qngx");var s={name:"case",data:()=>({pickerOptions2:{shortcuts:[{text:"最近一周",onClick(e){const t=new Date,a=new Date;a.setTime(a.getTime()-6048e5),e.$emit("pick",[a,t])}},{text:"最近一个月",onClick(e){const t=new Date,a=new Date;a.setTime(a.getTime()-2592e6),e.$emit("pick",[a,t])}},{text:"最近三个月",onClick(e){const t=new Date,a=new Date;a.setTime(a.getTime()-7776e6),e.$emit("pick",[a,t])}}]},selectedDates:[],tableData:[],pageSize:15,currentPage:1,total:0,searchForm:{}}),created(){this.getDataByCondition()},methods:{getDataByCondition(){this.currentPage=1;let e={page:this.currentPage,pagesize:this.pageSize,czr:this.searchForm.czr,czsj:this.searchForm.czsj,kssj:this.selectedDates[0],jssj:this.selectedDates[1]};this.$axios.post("log/findAll",e).then(e=>{this.tableData=e.data,this.total=e.counts})},clearCondition(){this.selectedDates=[],this.searchInput={}},handleSizeChange(e){this.pageSize=e,this.getDataByCondition()},handleCurrentChange(e){this.currentPage=e,this.getDataByCondition()}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"container"},[a("el-form",{staticClass:"demo-form-inline",attrs:{inline:!0,model:e.searchForm,size:"mini","label-width":"90px"}},[a("el-form-item",{attrs:{label:"操作时间段"}},[a("el-date-picker",{attrs:{type:"daterange",align:"right","unlink-panels":"","range-separator":"至","start-placeholder":"开始时间","end-placeholder":"结束时间","value-format":"yyyy-MM-dd","picker-options":e.pickerOptions2},model:{value:e.selectedDates,callback:function(t){e.selectedDates=t},expression:"selectedDates"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"操作用户"}},[a("el-input",{attrs:{placeholder:"输入用户名称",clearable:""},model:{value:e.searchForm.czr,callback:function(t){e.$set(e.searchForm,"czr",t)},expression:"searchForm.czr"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"操作内容"}},[a("el-input",{attrs:{placeholder:"输入操作内容",clearable:""},model:{value:e.searchForm.czsj,callback:function(t){e.$set(e.searchForm,"czsj",t)},expression:"searchForm.czsj"}})],1),e._v(" "),a("el-form-item",[a("el-button",{on:{click:e.clearCondition}},[e._v("清空条件")])],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.getDataByCondition}},[e._v("查询")])],1)],1)],1),e._v(" "),a("div",{staticClass:"container"},[a("el-table",{ref:"multipleTable",staticClass:"table",attrs:{data:e.tableData}},[a("el-table-column",{attrs:{prop:"czr",label:"操作用户"}}),e._v(" "),a("el-table-column",{attrs:{prop:"cznr",label:"操作内容"}}),e._v(" "),a("el-table-column",{attrs:{prop:"czsj",label:"操作时间"}})],1),e._v(" "),a("div",{staticClass:"pagination"},[a("el-pagination",{attrs:{"current-page":e.currentPage,background:"","page-sizes":[15,20,50],"page-size":e.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t},"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)],1)])},staticRenderFns:[]},n=a("C7Lr")(s,r,!1,null,null,null);t.default=n.exports}});