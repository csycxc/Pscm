//****************************************
//Johnny.jiang
//****************************************
// 比较笨的一种实现datatable编辑js
//需要引用的js css
//**************************
//bootstrap.css
//jquery.dataTables.css
//bootstrap-datetimepicker.min.css

//jquery-2.2.3.min.js
//bootstrap.js
//jquery.dataTables.js
//dataTables.bootstrap.js
//bootstrap-datetimepicker.min.js
//DatatableEditer.js
//**************************


//参数列表
//***************************
//id table 的id名称
//title  显示modal的名字
//url post 地址
//fields 配置修改列  var fields ={ lable: "Remark", name: "Remark", dropdownlist: [{ value: "1", text: "1" }, { value: "test", text: "test" }, { value: "3", text: "3" }] }];
//注意 fields 中 lable的名字必须要与table的列名一致，不然无法匹配到栏位,dropdownlist 为json格式数据
//button 操作标志，指定 update 和 delete
//***************************


(function ($) {
    //参数
    var defaults = {
        title: null,
        url: null,
        fields: null
    };

    var dataTableEditor = function(id, title, url, fields, button) {
        try {
            var trData = $(id).parent().parent();
            var thlist = $(id).parent().parent().parent().parent().find('th');
            //var trlist = $('#' + id + ' tr:gt(0)');
            //在th中添加一个id为datatableedit 的div
            if ($("#datatableedit").length > 0) {
            } else {
                    $(id).parent().parent().parent().parent().append("<div id='datatableedit'></div>");
            }
            var tdlist = trData.find('td');
            //获取行id
            var trid = trData.attr("id");
            //产生bootstrap modal 
            var html = "<div class='modal fade' id='datatableedit_Modal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'><button type='button' class='close' data-dismiss='modal' aria-hidden='true'></button><h4 class='modal-title' id='myModalLabel'>";
            html += title + "</h4></div><div class='modal-body'>";
            var context = " <div class='row'><div class='col-sm-12'> <form id='datatableedit_form' name='datatableedit_form'  role='form'  method='POST'> ";
            //新增方法页面
            if (button == 'create') {
                for (var n = 0; n < fields.length; n++) {
                    if (fields[n].lable) {
                        context += "<div class='row '><div ><label class='col-sm-4 control-label'>" + fields[n].lable + "</label> <div class='col-sm-8'>";
                    }
                    //产生dropdownlist
                    if (fields[n].dropdownlist) {
                        context += "<select name ='" + fields[n].name + "' id='" + fields[n].lable + "'  class='form-control'>";
                        $.each(fields[n].dropdownlist, function(index, item) {
                            context += " <option value ='" + item.value + "'>" + item.text + "</option>";
                        });
                        context += "</select>";
                    }
                    //date
                    else if (fields[n].date) {
                        context += "<div class=\"input-group date form_date \" data-date=\"\" data-date-format=\"yyyy-mm-dd\" data-link-field='" + fields[n].name + "' data-link-format=\"yyyy-mm-dd\">";
                        context += "<input class=\"form-control\"  type=\"text\" name ='" + fields[n].name + "' id='" + fields[n].lable + "' value='' readonly>";
                        context += " <span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-remove\"></span></span>";
                        context += "<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-calendar\"></span></span>";
                        context += "  </div><input type=\"hidden\"  value='' />";
                    }
                    //time
                    else if (fields[n].time) {
                        context += "<div class=\"input-group date form_time\" data-date=\"\" data-date-format=\"hh:ii\" data-link-field='" + fields[n].name + "'  data-link-format=\"hh:ii\">";
                        context += "<input class=\"form-control \" type=\"text\" name ='" + fields[n].name + "' id='" + fields[n].lable + "' value='' readonly>";
                        context += "<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-remove\"></span></span>";
                        context += "<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-time\"></span></span></div>";
                        context += "<input type=\"hidden\"  value=''  />";
                    }
                    //input
                    else {
                        context += "<input type='text' class='form-control' name ='" + fields[n].name + "' id='" + fields[n].lable + "' value=''>";
                    }
                    context += "</div></div></div>";
                }
          
            } else {
                for (var i = 0; i < thlist.length; i++) {
                    for (var n = 0; n < fields.length; n++) {
                        if (fields[n].lable == thlist.eq(i).text()) {
                            context += "<div class='row '><div ><label class='col-sm-4 control-label'>" + fields[n].lable + "</label> <div class='col-sm-8'>";
                            if (fields[n].key || button == 'delete') {
                                context += "<input type='text' readonly='true' class='form-control' name ='" + fields[n].name + "' id='" + fields[n].lable + "' value='" + tdlist.eq(i).text() + "'> ";
                            }
                                //更新方法页面
                            else if (button == 'update') {
                                //dropdownlist
                                if (fields[n].dropdownlist) {
                                    context += "<select name ='" + fields[n].name + "' id='" + fields[n].lable + "'  class='form-control'>";
                                    $.each(fields[n].dropdownlist, function(index, item) {
                                        if (item.text == tdlist.eq(i).text()) {
                                            context += " <option selected value ='" + item.value + "'>" + item.text + "</option>";
                                        } else {
                                            context += " <option value ='" + item.value + "'>" + item.text + "</option>";
                                        }
                                    });
                                    context += "</select>";
                                }
                                //date
                                else if (fields[n].date) {
                                    context += "<div class=\"input-group date form_date \" data-date=\"\" data-date-format=\"yyyy-mm-dd\" data-link-field='" + fields[n].name + "' data-link-format=\"yyyy-mm-dd\">";
                                    context += "<input class=\"form-control\"  type=\"text\" name ='" + fields[n].name + "' id='" + fields[n].lable + "' value='" + tdlist.eq(i).text() + "' readonly>";
                                    context += " <span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-remove\"></span></span>";
                                    context += "<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-calendar\"></span></span>";
                                    context += "  </div><input type=\"hidden\"  value='" + tdlist.eq(i).text() + "' />";
                                }
                                //time
                                else if (fields[n].time) {
                                    context += "<div class=\"input-group date form_time\" data-date=\"\" data-date-format=\"hh:ii\" data-link-field='" + fields[n].name + "'  data-link-format=\"hh:ii\">";
                                    context += "<input class=\"form-control \" type=\"text\" name ='" + fields[n].name + "' id='" + fields[n].lable + "' value='" + tdlist.eq(i).text() + "' readonly>";
                                    context += "<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-remove\"></span></span>";
                                    context += "<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-time\"></span></span></div>";
                                    context += "<input type=\"hidden\"  value='" + tdlist.eq(i).text() + "'  />";
                                }
                                //input
                                else {
                                    context += "<input type='text' class='form-control' name ='" + fields[n].name + "' id='" + fields[n].lable + "' value='" + tdlist.eq(i).text() + "'>";
                                }
                            }
                            context += "</div></div></div>";
                        }
                    }
                }
            }
            context += "</form></div></div>";
            html += context;
            html += "</div><div class='modal-footer'><button type='button' class='btn btn-default' data-dismiss='modal' title='关闭'><span class ='fa fa-fw fa-close'></span>关闭</button><button type='button' class='btn btn-primary' id='datatableedit_edit'>";
            //按钮名称
            if (button == 'update') {
                html += " <span class ='fa fa-fw fa-save'></span>更新";
            } else if (button == 'delete') {
                html += "<span class ='fa fa-fw fa-trash'></span> 删除";
            }
            else if (button == 'create') {
                html += "<span class ='fa fa-fw fa-plus'></span>新增";
            }
            //loanding 
            html += "</button></div></div><div id ='editloanding' class='overlay' style='display:none'><i class='fa fa-refresh fa-spin'></i></div></div></div>";
            $("#datatableedit").html(html);

            $('#datatableedit_Modal').on('shown.bs.modal', function() {
                //$("[data-mask]").inputmask();

                //date
                $('.form_date').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    minView: 2,
                    forceParse: 0
                });
                $('.form_time').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 1,
                    minView: 0,
                    maxView: 1,
                    forceParse: 0
                });
            });
            //显示modal
            $('#datatableedit_Modal').modal("show");
            //绑定ajax事件 更新页面数据
            $('#datatableedit_edit').bind('click', function () {
                $('#editloanding').show();
                var arr = $('#datatableedit_form').serializeArray();
                var dataList = 'DT_RowId=' + trid + "&" + $.param(arr);
                $.ajax({
                    type: 'post',
                    url: url,
                    dataType: "json",
                    data: dataList,
                    success: function (data) {
                        //遍历方法更新table值
                        if (button == 'update') {
                            for (var i = 0; i < thlist.length; i++) {
                                for (var n = 0; n < fields.length; n++) {
                                    if (fields[n].lable == thlist.eq(i).text()) {
                                        for (var m = 0; m < arr.length; m++) {
                                            if (arr[m].name == fields[n].name) {
                                                if (fields[n].dropdownlist) {
                                                    $.each(fields[n].dropdownlist, function(index, item) {
                                                        if (item.value == arr[m].value) {
                                                            //context += " <option selected value ='" + item.value + "'>" + item.text + "</option>";
                                                            tdlist.eq(i).html(item.text);
                                                        }
                                                    });
                                                }
                                                else {
                                                    // tdlist.eq(i).html(arr[m].value);
                                                    tdlist.eq(i).html(data[fields[n].name]);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (button == 'delete') {
                            //删除时直接删除行
                            trData.remove();
                        }
                        $('#datatableedit_Modal').modal("hide");
                        $('#editloanding').hide();
                    },
                    error: function(xhr, status, error) {
                        var json = $.parseJSON(xhr.responseText);
                        alert('error:' + json.ErrorMessage);
                    }
                });
            });
        } catch (err) {
            alert(err.message);
        }
    };
    //更新方法
    $.fn.DatatableUpdate = function(option) {
        $(this).on('click', 'a.editor_edit', function(e) {
            e.preventDefault();
            option.button = "update";
            dataTableEditor(this, option.title, option.url, option.fields, option.button);
        });
    };
    //删除方法 
    $.fn.DatatableDelete = function(option) {
        $(this).on('click', 'a.editor_remove', function(e) {
            e.preventDefault();
            option.button = "delete";
            dataTableEditor(this, option.title, option.url, option.fields, option.button);
        });
    };
    //新增方法 
    $.fn.DatatableCreate = function (option) {
            option.button = "create";
            dataTableEditor(this, option.title, option.url, option.fields, option.button);
    };
})(jQuery);
