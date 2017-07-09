var X = {
    option:{
        debug:true
    },
    ajax:function(data,callback,url){
        if(!url) return;
        var s = {};
        s.type="POST";
        s.url = "/" + url;
        s.data = data;
        s.success = callback;
        s.error = function(){
            X.dialog(false,"请求发生异常");
        };
        $.ajax(s);
    },
    dialog:function(){
        var json = {};
        if(arguments.length == 1){
            json = arguments[0];
        }else if(arguments.length == 2){
            json.success = arguments[0];
            json.msg = arguments[1];
        }
        X.clearTimeOutAndInterval();
        switch(json.code){
            case 200:
                X.hideDialog();
                X.showLoginDialog();
                break;
            default:
                json.msg = '<span style="color:' + (json.success ? '' : 'red') + '">' + (json.msg || (json.success ? '操作成功' : '操作失败')) + '</span>';
                var dialog = $('#operationDialog'),div = $('#operationDiv');
                dialog.dialog({content:json.msg});
                var bottom = div.css('bottom');
                div.stop().animate({bottom:'0px'},800,function(){
                    X.option.timeOut = setTimeout(function(){
                        if(X.option.timeOut != undefined){
                            $('#operationDiv').animate({bottom:bottom},2000);
                            X.clearTimeOutAndInterval();
                        }
                    },json.success ? 2000 : 5000);
                    dialog.off("mouseover mouseout");
                    dialog.on({
                        mouseover:function(){
                            X.holdDialog();
                        },
                        mouseout:function(){
                            X.hideDialog();
                        }
                    });
                });
        }
    },
    clearTimeOutAndInterval:function(t, i){
        if(t == undefined){
            if(X.option.timeOut != undefined){
                window.clearTimeout(X.option.timeOut);
                X.option.timeOut = undefined;
            }
        }else{
            window.clearTimeout(t);
        }
        if(i == undefined){
            if(X.option.interval != undefined){
                window.clearInterval(X.option.interval);
                X.option.interval = undefined;
            }
        }else{
            window.clearInterval(i);
        }
    },
    hideDialog:function(){
        X.clearTimeOutAndInterval();
        X.option.timeOut = setTimeout(function(){
            $('#operationDiv').stop().animate({bottom:'-180px'},2000,function(){
                X.clearTimeOutAndInterval();
            });
        },1000);
    },
    holdDialog:function(){
        $('#operationDiv').stop().css({bottom:'0px'});
        X.clearTimeOutAndInterval();
    },
    showLoginDialog:function(callback){
        var dialog = $('#loginDialog');
        if(dialog.length > 0){
            dialog.dialog('destroy');
        }
        $('body').append('<div id="loginDialog" style="padding:10px 20px;"></div>');
        if(callback == undefined)
            callback = X.doLogin;
        dialog = $('#loginDialog');
        var t = '';
        t += '<div>'
            + '<input required id="username" title="username" name="username" class="easyui-textbox" data-options="prompt:\'用户名\',iconCls:\'icon-man\'" />'
            + '</div>'
            + '<div style="margin-top:20px;">'
            + '<input required id="password" title="password" name="password" class="easyui-passwordbox" data-options="prompt:\'密码\',iconCls:\'icon-lock\'" />'
            + '</div>'
            + '<div style="margin-top:10px;">'
            + '<span id="loginResult" style="color:red;"></span>'
            + '</div>';
        dialog.dialog({
            title:'登陆',
            draggable:false,
            closed:true,
            content:t,
            buttons:[{
                text:'登陆',
                handler:callback
            },{
                text:'关闭',
                handler:X.hideLoginDialog
            }]
        });
        dialog.dialog("open");
    },
    hideLoginDialog:function(){
        $('#loginDialog').dialog('close');
    },
    tab:function(purl,url){
        $('#mainContent').load('page/' + purl + '/' + url + '/' + url + '.html');
    },
    selectFromConst : function(oConst){
        var t = new Txt();
        for(var x in oConst){
            t._('<option value="')
                ._(x)
                ._('">')
                ._(oConst[x])
                ._('</option>');
        }
        return t.toString();
    },
    closeForm : function(form,modal,callback){
        var $form = $(form);
        $form[0].reset();
        var $input = $form.find('input:hidden');
        if($input.length > 0){
            $input.val('');
        }
        $(modal).modal('hide');
        if(callback){
            callback();
        }
    },
    loadTableData : function(table,data,callback){
        var $table = $(table);
        if($table.length <= 0){
            return;
        }
        $table.data(constants.staticRef.list,data);
        var $tbody = $table.find('tbody');
        $tbody.empty();
        if(data){
            var $thead = $table.find('thead');
            var $ths = $thead.find('> tr > th');
            var fields = {};
            var transFields = [];
            var opts = {};
            $ths.each(function(){
                var field = $(this).attr('data-field');
                fields[field] = $.extend(opts,JSON.parse('{' + $(this).attr('data-format') + '}'));
            });
            for(var i=0;i<data.length;i++){
                var $tr = $('tr');
                $tbody.append($tr);
                var obj = data[i];
                for(var x in fields){
                    var text = '';
                    if(fields[x]){
                        var opt = fields[x];
                        if(x == '$index'){
                            if(opt.type == 'checkbox'){
                                text += '<input title="checkbox" type="checkbox" />';
                            }
                        }

                    }
                }
                for(var j=0;j<transFields.length;j++){
                    var $td = $('td');
                    $tr.append($td);
                    var transField = transFields[j];
                    var value = obj[fields[j]];
                    var text = value;
                    if(transField == '_index'){
                        text = i + 1;
                    }else{
                        if(constants[transField] != undefined){
                            var trans = constants[transField];
                            var keys = trans.key;
                            for(var k=0;k<keys.length;k++){
                                if(keys[k] == value){
                                    text = trans.value[k];
                                    $td.attr('data-value',value);
                                    break;
                                }
                            }
                        }
                    }
                    $td.text(text);
                }
            }
        }
        if(callback != undefined && typeof callback == 'function'){
            callback(data);
        }
    },
    putDataIntoForm : function(form,data,modal){
        var $form = $(form);
        for(var x in data){
            var $input = $form.find('input[name$=".'+x+'"]');
            if($input.length > 0){
                $input.val(data[x]);
                continue;
            }
            $input = $form.find('input[name$=".' + x + '"][type="hidden"]');
            if($input.length > 0){
                $input.val(data[x]);
                continue;
            }
            var $select = $form.find('select[name$=".' + x + '"]');
            if($select.length > 0){
                $select.find('option[value="' + data[x] + '"]').prop('selected',true);
                continue;
            }
            var $textarea = $form.find('textarea[name$=".' + x + '"]');
            if($textarea.length > 0){
                $textarea.val(data[x]);
                continue;
            }
            var $checkbox = $form.find('input[name$=".' + x + '"][type="checkbox"]');
            if(1 == data[x] || '1' == data[x]){
                $checkbox.prop('checked',true);
            }
        }
        if(modal){
            var $modal = $(modal);
            $modal.modal('show');
        }
    },
    confirm : function(success,error){
        var $modal = $('#confirmModal');
        var $errorBtn = $modal.find('.modal-footer').find('.btn-success');
        var $successBtn = $modal.find('.modal-footer').find('.btn-danger');
        if(success == undefined || typeof success != 'function'){
            $successBtn.attr('data-dismiss','modal');
        }else{
            $successBtn.unbind('click');
            $successBtn.bind('click',success);
        }
        if(error == undefined || typeof error != 'function'){
            $errorBtn.attr('data-dismiss','modal');
        }else{
            $errorBtn.unbind('click');
            $errorBtn.bind('click',error);
        }
        $modal.modal('show');
    }
};

function testObj(obj){
    for(var x in obj){
        console.log(x + ": " + obj[x]);
    }

}

function Txt(){
    var t = '';
    this._ = function(v){
        if(v){
            t += v;
        }
        return this;
    };
    this.toString = function(){
        return t;
    }
}