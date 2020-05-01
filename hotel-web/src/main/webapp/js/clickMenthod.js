//标记，防止表单重复提交
var lock = true;

/**
 * 为添加按钮绑定单击事件，打开弹窗
 */
function addInfo(type) {
    switch (type) {
        case "product":
            layer.open({
                type: 2,
                title: '新增商品信息',
                shadeClose: true,
                shade: 0.3,
                maxmin: true, //开启最大化最小化按钮
                area: ['25%', '75%'],
                content: 'operation/addProductInfoWindow',
                skin: 'layui-layer-lan'
            });
            break;
        case "multiple":
            layer.open({
                type: 2,
                title: '新增连锁店信息',
                shadeClose: true,
                shade: 0.3,
                maxmin: true, //开启最大化最小化按钮
                area: ['42%', '32%'],
                content: 'operation/addMultipleInfoWindow',
                skin: 'layui-layer-lan'
            });
            break;
        case "productType":
            layer.open({
                type: 2,
                title: '新增商品类型',
                shadeClose: true,
                shade: 0.2,
                area: ['30%', '30%'],
                content: 'operation/addPetTypeInfoWindow',
                skin: 'layui-layer-lan'
            });
            break;
        case "purchase":
            layer.open({
                type: 2,
                title: '采购商品',
                shadeClose: true,
                shade: 0.2,
                area: ['25%', '38%'],
                content: 'operation/addPurchaseInfoWindow',
                skin: 'layui-layer-lan'
            });
            break;
        case "dishes":
            layer.open({
                type: 2,
                title: '添加菜品',
                shadeClose: true,
                shade: 0.2,
                area: ['25%', '55%'],
                content: 'operation/addDishesInfoWindow',
                skin: 'layui-layer-lan'
            });
            break;
        case "house":
            layer.open({
                type: 2,
                title: '新增客房信息',
                shadeClose: true,
                shade: 0.3,
                maxmin: true, //开启最大化最小化按钮
                area: ['42%', '40%'],
                content: 'openAddHouseInfoHtml',
                skin: 'layui-layer-lan'
            });
            break;
        case "houseType":
            layer.open({
                type: 2,
                title: '新增客房类型',
                shadeClose: true,
                shade: 0.2,
                area: ['25%', '70%'],
                content: 'openHouseTypeHtml',
                skin: 'layui-layer-lan'
            });
            break;
        case "employee":
            layer.open({
                type: 2,
                title: '新增员工信息',
                shadeClose: true,
                shade: 0.2,
                area: ['20%', '38%'],
                content: 'operation/addEmployeeInfoWindow',
                skin: 'layui-layer-lan'
            });
            break;
    }
};

/**
 * 为修改按钮绑定单击事件，打开弹窗
 */
function updateInfo(count, dataId, type) {
    if (count == 0) {
        layer.open({
            title: '错误消息'
            , content: '请先选择一条信息再进行操纵！'
            , skin: 'layui-layer-lan'
            , shade: 0
            , icon: 5
            , anim: 6
            , closeBtn: 0
        });
    } else if (count > 1) {
        layer.open({
            title: '错误消息'
            , content: '您只可选中一条信息操纵！'
            , skin: 'layui-layer-lan'
            , shade: 0
            , icon: 5
            , anim: 6
            , closeBtn: 0

        });
    } else {
        switch (type) {
            case "client":
                layer.open({
                    type: 2,
                    title: '修改客户信息',
                    shadeClose: true,
                    shade: 0.3,
                    maxmin: true, //开启最大化最小化按钮
                    area: ['42%', '31%'],
                    content: 'operation/updateClientWindow?selectId=' + dataId,    //跳转至控制器处理修改
                    skin: 'layui-layer-lan'
                });
                break;
            case "product":
                layer.open({
                    type: 2,
                    title: '修改商品信息',
                    shadeClose: true,
                    shade: 0.3,
                    maxmin: true, //开启最大化最小化按钮
                    area: ['25%', '77%'],
                    content: 'operation/updateProductInfoWindow/' + dataId,    //跳转至控制器处理updateInfoWindow请求通知将result返回的ID传入进行宠物信息的查询
                    skin: 'layui-layer-lan'
                });
                break;
            case "purchase":
                layer.open({
                    type: 2,
                    title: '修改采购信息',
                    shadeClose: true,
                    shade: 0.3,
                    maxmin: true, //开启最大化最小化按钮
                    area: ['25%', '38%'],
                    content: 'operation/updatePurchaseInfoWindow/' + dataId,    //跳转至控制器处理updateInfoWindow请求通知将result返回的ID传入进行宠物信息的查询
                    skin: 'layui-layer-lan'
                });
                break;
            case "dishes":
                layer.open({
                    type: 2,
                    title: '修改菜品信息',
                    shadeClose: true,
                    shade: 0.3,
                    maxmin: true, //开启最大化最小化按钮
                    area: ['25%', '60%'],
                    content: 'operation/updateDishesInfoWindow/' + dataId,    //跳转至控制器处理updateInfoWindow请求通知将result返回的ID传入进行宠物信息的查询
                    skin: 'layui-layer-lan'
                });
                break;
            case "houseType":
                layer.open({
                    type: 2,
                    title: '修改客房类型信息',
                    shadeClose: true,
                    shade: 0.3,
                    maxmin: true, //开启最大化最小化按钮
                    area: ['25%', '70%'],
                    content: 'openUpdateHouseTypeHtml?id=' + dataId,    //跳转至控制器处理updateInfoWindow请求通知将result返回的ID传入进行宠物信息的查询
                    skin: 'layui-layer-lan'
                });
                break;
            case "employee":
                layer.open({
                    type: 2,
                    title: '修改客房类型信息',
                    shadeClose: true,
                    shade: 0.3,
                    maxmin: true, //开启最大化最小化按钮
                    area: ['20%', '30%'],
                    content: 'operation/openUpdateEmployeeTypeHtml?id=' + dataId,    //跳转至控制器处理updateInfoWindow请求通知将result返回的ID传入进行宠物信息的查询
                    skin: 'layui-layer-lan'
                });
                break;
        }

    }
};

/**
 * 为查询按钮绑定单击事件，打开弹窗
 */
function searchInfo(count, dataId) {
    if (count == 0) {
        layer.open({
            title: '错误消息'
            , content: '请先选择一条信息再进行操纵！'
            , skin: 'layui-layer-lan'
            , shade: 0
            , icon: 5
            , anim: 6
            , closeBtn: 0
        })
    } else if (count > 1) {
        layer.open({
            title: '错误消息'
            , content: '您只可选中一条信息操纵！'
            , skin: 'layui-layer-lan'
            , shade: 0
            , icon: 5
            , anim: 6
            , closeBtn: 0
        })
    } else {
        layer.open({
            type: 2,
            title: '宠物详细信息',
            shadeClose: true,
            shade: 0.3,
            maxmin: true, //开启最大化最小化按钮
            area: ['42%', '79%'],
            content: 'operation/searchInfoWindow/' + dataId,        //跳转至控制器处理searchInfoWindow请求通知将result返回的ID传入进行宠物信息的查询
            skin: 'layui-layer-lan'

        });
    }
};

/**
 * 获取选中ID
 * 处理传入的所有数据的数组，获取数据中的ID
 * @param dataArry
 * @returns {*}
 */
function getIds(dataArry) {
    var resultIds = -1;
    if (dataArry.length == 1) {           //如果获取的选中行只有一行，直接获取数组第一个的ID
        resultIds = dataArry[0].id;
        return resultIds;
    } else if (dataArry.length == 0) {     //如果选中行为0，返回-1
        return resultIds;
    } else if (dataArry.length > 1) {      //如果选中行大于1行，可能是删除，循环处理选中ID
        $.each(dataArry, function () {
            var data = $(this).get(0);
            resultIds += data.id + ",";
        });
        //如果flag已经不再是-1，就将前面的-1截取掉，同时截掉最后一个逗号
        if (resultIds != -1) {
            resultIds = resultIds.substring(2, resultIds.length - 1);
        }
        return resultIds;
    }
}

/**
 * 获取客房号
 * 处理传入的所有数据的数组，获取数据中的ID
 * @param dataArry
 * @returns {*}
 */
function getHouseCodes(dataArry) {
    var resultIds = -1;
    if (dataArry.length == 1) {           //如果获取的选中行只有一行，直接获取数组第一个的ID
        resultIds = dataArry[0].houseCode;
        return resultIds;
    } else if (dataArry.length == 0) {     //如果选中行为0，返回-1
        return resultIds;
    } else if (dataArry.length > 1) {      //如果选中行大于1行，可能是删除，循环处理选中ID
        $.each(dataArry, function () {
            var data = $(this).get(0);
            resultIds += data.houseCode + ",";
        });
        //如果flag已经不再是-1，就将前面的-1截取掉，同时截掉最后一个逗号
        if (resultIds != -1) {
            resultIds = resultIds.substring(2, resultIds.length - 1);
        }
        return resultIds;
    }
};

/**
 * 获取客房类型id
 * 处理传入的所有数据的数组，获取数据中的ID
 * @param dataArry
 * @returns {*}
 */
function getHouseTypeId(dataArry) {
    var resultIds = -1;
    if (dataArry.length == 1) {           //如果获取的选中行只有一行，直接获取数组第一个的ID
        resultIds = dataArry[0].typeId;
        return resultIds;
    } else if (dataArry.length == 0) {     //如果选中行为0，返回-1
        return resultIds;
    } else if (dataArry.length > 1) {      //如果选中行大于1行，可能是删除，循环处理选中ID
        $.each(dataArry, function () {
            var data = $(this).get(0);
            resultIds += data.typeId + ",";
        });
        //如果flag已经不再是-1，就将前面的-1截取掉，同时截掉最后一个逗号
        if (resultIds != -1) {
            resultIds = resultIds.substring(2, resultIds.length - 1);
        }
        return resultIds;
    }
};
/**
 * 删除方法
 * @param count
 * @param dataId
 * @param url
 */
function deleteInfo(count, dataId, url) {
    if (count == 0) {
        layer.open({
            title: '错误消息'
            , content: '请先选择一条信息再进行操纵！'
            , skin: 'layui-layer-lan'
            , shade: 0
            , icon: 5
            , anim: 6
            , closeBtn: 0
        })
    } else {
        if(lock) {
            lock = false;
            layer.confirm('确定要删除编号为：<span style="color:red">' + dataId + '</span> 的信息？', {
                btn: ['确定', '取消'] //按钮
                , shade: 0
                , icon: 5
                , anim: 6
                , closeBtn: 0
                , icon: 5
            }, function () { //确定
                //调用异步删除方法
                $.getJSON(
                    url,
                    function (rollData) {
                        if (rollData.result == "no"){
                            layer.confirm(rollData.msg, {
                                btn: ['确定'] //按钮
                                , icon: 5
                            }, function () {
                                window.location.reload();
                                lock = true;
                            })
                        }
                        if (rollData.result == "success") {
                            layer.confirm(rollData.msg, {
                                btn: ['确定'] //按钮
                                , icon: 6
                            }, function () {
                                window.location.reload();
                                lock = true;
                            })
                        } else {
                            layer.confirm(rollData.msg, {
                                btn: ['确定'] //按钮
                                , icon: 5
                            }, function () {
                                window.location.reload();
                                lock = true;
                            })
                        }
                    }
                )
            }, function () { //取消
                lock = true;
                layer.msg('取消删除！', {
                    icon: 6
                    , time: 2000
                });
            })
        }
    }

    /**
     * 绑定回车事件
     */
    $(document).keypress(function (e) {
        //如果当前有类似layer.alert的窗体，点击最上层的确定按钮，并且取消所有焦点
        if($('.layui-layer-btn0').length > 0){
            $('.layui-layer-btn0').eq($('.layui-layer-btn0').length - 1)[0].click();
            $("*").blur();
        }
    });
};