<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div>
    <ul id="contentCategory" class="easyui-tree"></ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
    //文档加载后处理以下逻辑
    $(function () {
        //创建树，当展开一个封闭的节点，如果没有加载子节点，会把节点id的值作为http请求参数并命名为id
        $("#contentCategory").tree({
            url: '/content/category/list',
            animate: true,
            method: "GET",
            //鼠标右键
            onContextMenu: function (e, node) {
                //关闭原来的鼠标默认事件
                e.preventDefault();
                //选中鼠标右键的节点
                $(this).tree('select', node.target);
                //显示菜单栏
                $('#contentCategoryMenu').menu('show', {
                    left: e.pageX,//在鼠标的位置显示
                    top: e.pageY
                });
            },
            //在选中的节点被编辑之后触发
            onAfterEdit: function (node) {
                //获取树本身
                var _tree = $(this);
                //如果是新增的节点
                if (node.id == 0) {
                    // 新增节点
                    $.post("/content/category/create", {parentId: node.parentId, name: node.text}, function (data) {
                        if (data.status == 200) {
                            //更新节点
                            _tree.tree("update", {
                                //更新新增节点的id
                                target: node.target,
                                id: data.data.id
                            });
                        } else {
                            $.messager.alert('提示', '创建' + node.text + ' 分类失败!');
                        }
                    });
                } else {//如果是更新节点
                    $.post("/content/category/update", {id: node.id, name: node.text});
                }
            }
        });
    });

    //处理点击菜单的事件
    function menuHandler(item) {
        //获取树
        var tree = $("#contentCategory");
        //获取被选中（鼠标右键）的节点
        var node = tree.tree("getSelected");

        //点击添加
        if (item.name === "add") {
            //追加子节点
            tree.tree('append', {
                //被添加的子节点的父节点
                parent: (node ? node.target : null),
                data: [{
                    text: '新建分类',
                    id: 0,
                    parentId: node.id
                }]
            });
            //找到id为0的节点
            var _node = tree.tree('find', 0);//根节点
            //选中添加的节点，开启编辑
            tree.tree("select", _node.target).tree('beginEdit', _node.target);
        } else if (item.name === "rename") {
            tree.tree('beginEdit', node.target);
        } else if (item.name === "delete") {
            $.messager.confirm('确认', '确定删除名为 ' + node.text + ' 的分类吗？', function (r) {
                if (r) {
                    $.post("/content/category/delete/", {id: node.id}, function (data) {
                        if (data.status == 200) {
                            tree.tree("remove", node.target);
                        } else {
                            $.messager.alert('提示', '删除' + node.text + ' 分类失败!');
                        }
                    });
                }
            });
        }
    }
</script>