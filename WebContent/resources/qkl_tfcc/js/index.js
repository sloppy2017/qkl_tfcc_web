$.extend($.fn.validatebox.defaults.rules, {
    select: {
        validator: function (value, param) {
            var type=true;
            if(value.indexOf('请选择')>0){type=false;}
            return type;
        },
        message: '请选择值！'
    },
    md: {
        validator: function(value, param){
            beginDate = $(param[0]).datetimebox('getValue');
            var d1 = $.fn.datebox.defaults.parser(beginDate);
            var d2 = $.fn.datebox.defaults.parser(value);
            varify=d2>=d1;
            return varify;
        },
        message: '结束时间要大于开始时间！'
    },
    date: {
        validator: function (value, param) {
            return /^(\d{4})-(\d{2})-(\d{2})$/.test(value);
        },
        message: '请输入正确的日期格式！'
    }
});

var $index = {
    _formId: $('#j_form'),
    _listId: $('#j_list'),
    _config: '',
    _cmenu: '',
    _init: function(){
        this._initForm();
        this._initList();
        this._build();

    },
    _initForm:function(){
        var that = this;

        that.addInput();

        $('#j_source').combobox({
            url: $$data.sourceUrl,
            type:"POST",
            editable:false,
            valueField:'source_id',
            textField:'source_name',
            validType:"select['j_source']",
            prompt:'请选择来源',
            onSelect:function(node){
                $('#j_data').combobox({
                    data: node.dataList
                });
            }
        });

        $('#j_data').combobox({
            editable:false,
            valueField:'table_name',
            textField:'data_name',
            prompt:'请选择数据',
            validType:"select['j_data']",
            onSelect:function(node){
                that._config=node.config;
            }
        });

        // 当天时间

    },
    _build:function(){
        var that = this;
        //绑定事件
        $('#j_submit').click(function(){
            that.query();
        });
        $('#j_export').click(function(){
            that.exportData();
        });
    },
    addInput:function(){
        var input = $$data.input;
        var arrForm = [];
        $.each(input,function(index,val){
            arrForm.push('<input type="hidden" id="'+val.field+'" name="'+val.field+'" />');
        });
        var html = arrForm.join('');
        this._formId.append(html);
    },
    query:function(){
        var that = this;
        var input = $$data.input;
        var arrForm = [];
        $.each(input,function(index,val){
            var value = $('#j_'+val.field).combobox('getValue');
            // input hidden id
            $('#'+val.field).val(value);
            var text = $('#j_'+val.field).combobox('getText');
            arrForm[val.field]=text;
        });
        //console.log(that._formId.serialize());
        var cid = $('#source').val();
        var id = $('#data').val();
        var time=$('#endDate').val()>=$('#beginDate').val();
        if(!cid){
            $.messager.alert('查询：','请选择查询来源','info');
            return;
        }else if(!id){
            $.messager.alert('查询：','请选择查询数据','info');
            return;
        }/*else if(!time&&$('#beginDate').val()!=""&&$('#endDate').val()!=""){
        	$.messager.alert('查询：','结束日期不能小于或等于开始时间','info');
        	return;
        }*/
        		
        //form query
        $.ajax({
            url: $$data.url,
            data: that._formId.serialize(),
            type:'post',
            beforeSend:function(XHR){
                var array = [{field:"table_name",title:"ID",hidden:true}];
                var percent = 0.97/that._config.length;
                $.each(that._config, function(index, val) {
                     array.push({field:val.field,title:val.title,align:"left",width:that.fixWidth(percent)});
                });
                that._listId.datagrid('loading');
                that._listId.datagrid({columns:[array]});
            },
            success:function(data){
                data.sourceText = arrForm['source'];
                data.dataText = arrForm['data'];
                that._listId.datagrid('loaded');
                that.setList(data);
                that._listId.datagrid('loadData',data);
            }
        });
        //that.showList(data);
    },
    _initList:function(){
        var that = this;
        var array = [{field:"table_name",title:"ID",hidden:true}];
        that._listId.datagrid({
            method: 'post',
            title: '来源 > 数据',
            iconCls: 'icon-search',
            loadMsg: '数据加载中请稍后……',
            rownumbers: true,
            fit:true,
            pagination: true,
            pageSize: 10,
            pageList : [ 10, 20, 30 ],
            striped: true,
            idField: "table_name",
            singleSelect: true,
            columns:[array],
            data:[],
            onLoadSuccess:function(data) {
                if(!data){return;}
                if (data.total == 0) {
                    $(this).datagrid('getPanel').find('.datagrid-view2 .datagrid-body').html('<div class="orange" style="font-size:12px;text-align:center;padding:100px">暂无相关信息！</div>');
                }
                that.setPage(data);
            },
            onHeaderContextMenu: function(e, field){
                e.preventDefault();
                if (!$index._cmenu){
                    $index.createColumnMenu();
                }
                $index._cmenu.menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            }
        });
    },
    createColumnMenu:function(){
        $index._cmenu = $('<div/>').appendTo('body');
        $index._cmenu.menu({
            onClick: function(item){
                if (item.iconCls == 'icon-ok'){
                    $index._listId.datagrid('hideColumn', item.name);
                    $index._cmenu.menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-empty'
                    });
                } else {
                    $index._listId.datagrid('showColumn', item.name);
                    $index._cmenu.menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-ok'
                    });
                }
            }
        });
        var fields = $index._listId.datagrid('getColumnFields');
        for(var i=0; i<fields.length; i++){
            var field = fields[i];
            var col = $index._listId.datagrid('getColumnOption', field);
            $index._cmenu.menu('appendItem', {
                text: col.title,
                name: field,
                iconCls: 'icon-ok'
            });
        }
    },
    setList:function(data){
        var that = this;
        var opts = that._listId.datagrid('options');
        var pageNum = data.page,pageSize = data.size;
        opts.pageNumber = pageNum;
        opts.pageSize = pageSize;
        var title = data.sourceText + ' > ' + data.dataText;
        that._listId.datagrid('getPanel').panel('setTitle',title);
    },
    setPage:function(data){
        var that = this;
        var pager = that._listId.datagrid('getPager');
        pager.pagination({
            onSelectPage: function (pageNum, pageSize) {
                $('#page').val(pageNum);
                $('#size').val(pageSize);
                that.query();
            }
        });
        var pageNum = data.page,pageSize = data.size;
        pager.pagination('refresh', {
            pageNumber: pageNum,
            pageSize: pageSize
        });
        if(pageNum==undefined||pageNum==''){pageNum=1;pageSize=10;}
        $('#page').val(pageNum);
        $('#size').val(pageSize);
    },
    fixWidth: function(percent){
        return document.body.clientWidth * percent;
    },
    exportData:function(){
    	var that= this;
    	var tempPage = $('#page').val();
        var tempSize = $('#size').val();
       	$('#page').val(-1);
        $('#size').val(-1);
        var input = $$data.input;
        var arrForm = [];
        $.each(input,function(index,val){
            var value = $('#j_'+val.field).combobox('getValue');
            // input hidden id
            $('#'+val.field).val(value);
            var text = $('#j_'+val.field).combobox('getText');
            arrForm[val.field]=text;
        });
        var cid = $('#source').val();
        var id = $('#data').val();
        if(!cid){
            $.messager.alert('导出数据：','请选择查询来源','info');
            return;
        }else if(!id){
            $.messager.alert('导出数据：','请选择查询数据','info');
            return;
        }
    	window.open($$data.exportUrl+"?"+that._formId.serialize());
    	$('#page').val(tempPage);
        $('#size').val(tempSize);
    }
};

$index._init();
