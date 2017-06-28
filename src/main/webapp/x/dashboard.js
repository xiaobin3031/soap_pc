$(function(){
    loadLeftMenu();
    loadMainContent();
});
//加载首页
function loadMainContent(){
    $('#mainContent').load('page/home/mainBody.html');
}
//加载左侧菜单
function loadLeftMenu(){
    X.ajax({},function(data){
        if(data){
            var newList = [];
            for(var i = 0;i<data.length;i++){
                if(!data[i].parent_id){
                    data[i].nodes = [];
                    newList.push(data[i]);
                }
            }
            for(var i=0;i<newList.length;i++){
                for(var j=0;j<data.length;j++){
                    if(newList[i].prod_id == data[j].parent_id){
                        newList[i].nodes.push(data[j]);
                    }
                }
            }
            var t = new Txt();
            for(var i=0;i<newList.length;i++){
                var node = newList[i];
                t._('<li><a href="#')
                    ._(node.prod_id)
                    ._('" class="editor">')
                    ._(node.prod_name)
                    ._('</a>');
                if(node.nodes && node.nodes.length){
                    t._('<span class="arrow"></span>')
                        ._('<ul id="')
                        ._(node.prod_id)
                        ._('">');
                    var nodes = node.nodes;
                    for(var j=0;j<nodes.length;j++){
                        var childNode = nodes[j];
                        t._('<li><a href="javascript:void(0)" onclick="X.tab(')
                            ._('\'')
                            ._(node.prod_id)
                            ._('\',\'')
                            ._(childNode.prod_id)
                            ._('\'')
                            ._(')">')
                            ._(childNode.prod_name)
                            ._('</a></li>');
                    }
                    t._('</ul>');
                }
                t._('</li>');
            }
            var mainNav = $('#mainNav');
            mainNav.find('> ul').empty().append(t.toString());
            //注册事件
            mainNav.find('> ul li a').each(function(){
                var url = jQuery(this).attr('href');
                jQuery(this).click(function(){
                    if(jQuery(url).length > 0) {
                        if(jQuery(url).is(':visible')) {
                            if(!jQuery(this).parents('div').hasClass('menucoll') &&
                                !jQuery(this).parents('div').hasClass('menucoll2'))
                                jQuery(url).slideUp();
                        } else {
                            jQuery('.vernav ul ul, .vernav2 ul ul').each(function(){
                                jQuery(this).slideUp();
                            });
                            if(!jQuery(this).parents('div').hasClass('menucoll') &&
                                !jQuery(this).parents('div').hasClass('menucoll2'))
                                jQuery(url).slideDown();
                        }
                        return false;
                    }
                });
            });
        }
        // mainContent
    },'product/list');
}