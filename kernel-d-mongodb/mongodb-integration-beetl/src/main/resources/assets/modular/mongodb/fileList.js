layui.use(['table', 'form', 'func', 'HttpRequest', 'util', 'upload'], function () {

    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var func = layui.func;
    var HttpRequest = layui.HttpRequest;
    var util = layui.util;
    var upload = layui.upload;

    // 模型设计管理
    var MongoFile = {
        tableId: "fileList"
    };

    // 初始化表格的列
    MongoFile.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', title: '文件编号', width: 200},
            {field: 'name', title: '文件名称', width: 200},
            {field: 'uploadUserId', title: '创建人编号'},
            {field: 'uploadDate', title: '创建日期'},
            {align: 'center', toolbar: '#tableBar', title: '操作', width: 250}
        ]];
    };

    // 点击查询按钮
    MongoFile.search = function () {
        var queryData = {};
        queryData['name'] = $("#fileName").val();
        table.reload(MongoFile.tableId, {
            where: queryData,
            page: {curr: 1}
        });
    };

    // 点击删除
    MongoFile.delete = function (data) {
        var operation = function () {
            var httpRequest = new HttpRequest(Feng.ctxPath + "/view/mongodb/file/del?id="+data.id, 'post', function (data) {
                Feng.success("删除成功!");
                table.reload(MongoFile.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            httpRequest.set(data);
            httpRequest.start(true);
        };
        Feng.confirm("是否删除文件" + data.name + "?", operation);
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MongoFile.tableId,
        url: Feng.ctxPath + '/view/mongodb/file/list',
        page: true,
        request: {pageName: 'pageNo', limitName: 'pageSize'},
        height: "full-158",
        cellMinWidth: 100,
        cols: MongoFile.initColumn(),
        parseData: Feng.parseData
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MongoFile.search();
    });


    // 上传文件的点击事件
    upload.render({
        elem: '#modelUpload',
        url: Feng.ctxPath + '/view/mongodb/file/add',
        accept: 'file',
        size: 10000, // 单位kb
        done: function (res) {
            Feng.success("上传文件成功!");
            MongoFile.search();
        },
        error: function (err) {
            Feng.error("上传文件失败！" + err.message);
        }
    });

    // 工具条点击事件
    table.on('tool(' + MongoFile.tableId + ')', function (obj) {
        var data = obj.data;
        var event = obj.event;

        if (event === 'down') {
            window.open(Feng.ctxPath + '/view/mongodb/file/down?id=' + data.id);
        } else if (event === 'delete') {
            MongoFile.delete(data);
        }
    });

});
