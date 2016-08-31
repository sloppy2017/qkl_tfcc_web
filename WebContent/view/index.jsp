<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="./_g/_headbase.jsp" />
<title>tfcc_原油发行及奖励系统</title>
<jsp:include page="./_g/head.jsp" />
<%
	String basePath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	
%>
<link rel="stylesheet" href="${basePath}/resources/yc_udrs/css/style.css">
<link rel="stylesheet" href="${basePath}/resources/yc_udrs/css/index.css">
<script>
    var $$data = {
        "url": "search/query.do",
        "sourceUrl":"search/tableConfig.do",
        "exportUrl":"search/export.do",
        "input":[
            {"field":"source"},
            {"field":"data"},
            {"field":"beginDate"},
            {"field":"endDate"}
        ]
    };
</script>
</head>
<body class="easyui-layout">

<!--html start  style="width:100%;height:30px;background:#eee;" -->
 <div id="page_header" class="easyui-page_header">
    <div class="top">
        <div class="l">
            <span>tfcc_原油发行及奖励系统</span>
        </div>
        <div class="r exit">
            欢迎您！<a href="#" title="">柯志益</a><!-- <a class="modify-pwd-btn" href="javascript:void(0);">修改密码</a> --><a href="#">安全退出</a>
        </div>
    </div>
 </div> 
<div data-options="region:'north',border:false" style="padding:10px;margin-top:30px;">
    <form id="j_form" class="easyui-panel m-search" data-options="fit:true">
        <input  type="hidden" name="page" id="page" value="1" />
        <input  type="hidden" name="size" id="size" value="10" />
        <div class="row">
            <div class="fieldset">
                <label>选择来源：</label>
                <div class="val">
                    <select id="j_source" style="width:180px"></select>
                </div>
            </div>
            <div class="fieldset">
                <label>选择数据：</label>
                <div class="val">
                    <select id="j_data" style="width:200px"></select>
                </div>
            </div>
            <div class="fieldset">
                <label>选择时间：</label>
                <div class="val">
                    <input id="j_beginDate" class="easyui-datebox" data-options="editable:false
                    " /> 至 <input id="j_endDate" class="easyui-datebox" data-options="editable:false,validType:'md[\'#j_beginDate\']'" />
                </div>
            </div>
            <div class="fieldset">
                <input type="button" class="easyui-linkbutton" value="查询" style="height:24px;padding:0 8px;margin-left:3px;" id="j_submit" />
                <input type="button" class="easyui-linkbutton" value="导出数据" style="height:24px;padding:0 8px;margin-left:3px;" id="j_export" />
            </div>
        </div>
    </form>
</div>

<div data-options="region:'center',border:false" style="padding:0 10px 10px">
    <table id="j_list"></table>
</div>
<!--html end-->

<jsp:include page="./_g/script.jsp"/>
<script type="text/javascript" src="${basePath}/resources/yc_udrs/js/index.js"></script>
<jsp:include page="./_g/foot.jsp" />