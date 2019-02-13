var shenheFlags = [{ id: 0, text: '未审' }, { id: 1, text: '已审'}];
       	
var orderFlags = [{ id: 0, text: '未完成' }, { id: 1, text: '已完成'}, { id: 2, text: '作废'},
                  { id: 3, text: '调坯完成'}, { id: 4, text: '反审核申请'}];

var disableFlags = [{ id: 0, text: '正常' }, { id: 1, text: '已禁用'}];

var managerFlags = [{ id: 0, text: '' }, { id: 1, text: '是'}];

var gongxuTypes = [{ id: 'ranse', text: '染色' }, { id: 'houzhengli', text: '后整理'}];

var rukuByOrdercolorFlags = [{ id: 0, text: '否' }, { id: 1, text: '是'}];

var jifeiTypes = [{ id: 0, text: '按回仓计算' }, { id: 1, text: '按检验计算'}];

var gongxuSorts = [{ id: 1, text: '第一步' }, { id: 2, text: '第二步'}, { id: 3, text: '第三步'}, { id: 4, text: '第四步'}, 
                   { id: 5, text: '第五步'}, { id: 6, text: '第六步'}, { id: 7, text: '第七步'}, { id: 8, text: '第八步'} ,
                   { id: 9, text: '第九步'}, { id: 10, text: '第十步'}];

var commonFlags = [{ id: 0, text: '否' }, { id: 1, text: '是'}];

var enableFlags = [{ id: 0, text: '<input type="checkbox" style="outline:none;" hidefocus />' }, { id: 1, text: ''}];

var churuTypes = [{ id: 0, text: '其它' },{ id: 1, text: '入库' }, { id: 2, text: '出库'}];

var calcYsyfFlags = [{ id: 0, text: '不计算应收应付' },{ id: 1, text: '应收' }, { id: 2, text: '应付'}];

var kaipiaoFlags = [{ id: 0, text: '不开票' }, { id: 1, text: '开票'}];

var chukuTypeFlags = [{ id: "xiaoshouChuku", text: '销售出库' }, { id: "xiaoshouTongzhi", text: '<font color=red>通知单</font>'}];

var fahuoFlags = [{ id: "0", text: '<font color=red>未完成</font>' }, { id: "1", text: '<font color=green>已完成</font>'}];

var month = [{ id: "1", text: '一月' }, { id: "2", text: '二月'}, { id: "3", text: '三月'}, { id: "4", text: '四月'}, 
               { id: "5", text: '五月'}, { id: "6", text: '六月'}, { id: "7", text: '七月'}, { id: "8", text: '八月'} ,
               { id: "9", text: '九月'}, { id: "10", text: '十月'}, { id: "11", text: '十一月'}, { id: "12", text: '十二月'}];

var dabaoLaiyuans = [{value:1,text:'正常打包'},{value:2,text:'退货打包'},{value:3,text:'领料打包'}];


function onShenheFlagRenderer(e) {
    for (var i = 0, l = shenheFlags.length; i < l; i++) {
        var g = shenheFlags[i];
        if (g.id == e.value && e.value=='1') {
        	return '<font color="green">' + g.text + '</font>';
        } else {
        	return '<font color="red">' + g.text + '</font>';
        }
    }
    return "";
}

function onOrderFlagsRenderer(e) {
    for (var i = 0, l = orderFlags.length; i < l; i++) {
        var g = orderFlags[i];
        if (g.id == e.value) return g.text;
    }
    return "";
}

function onDisableFlagRenderer(e) {
    for (var i = 0, l = disableFlags.length; i < l; i++) {
        var g = disableFlags[i];
        if (g.id == e.value) {
        	if(g.id==1) { // 禁用了,红色显示
        		return '<font color="red">' + g.text + '</font>';
        	} else {
        		return '<font color="green">' + g.text + '</font>';
        	}
        }
    }
    return "";
}

function onManagerFlagRenderer(e) {
    for (var i = 0, l = managerFlags.length; i < l; i++) {
        var g = managerFlags[i];
        if (g.id == e.value) return g.text;
    }
    return "";
}
function onGongxuTypeRenderer(e) {
    for (var i = 0, l = gongxuTypes.length; i < l; i++) {
        var g = gongxuTypes[i];
        if (g.id == e.value) return g.text;
    }
    return "";
}
function onRukuByOrdercolorFlagRenderer(e) {
    for (var i = 0, l = rukuByOrdercolorFlags.length; i < l; i++) {
        var g = rukuByOrdercolorFlags[i];
        if (g.id == e.value) return g.text;
    }
    return "";
}
function onCommonFlagRenderer(e) {
    for (var i = 0, l = commonFlags.length; i < l; i++) {
        var g = commonFlags[i];
        if (g.id == e.value) return g.text;
    }
    return "";
}
function onEnableFlagRenderer(e) {
    for (var i = 0, l = enableFlags.length; i < l; i++) {
        var g = enableFlags[i];
        if (g.id == e.value) return g.text;
    }
    return "";
}
function renderShenheFlag(e) {
	if (e.record.shenheFlag == 1) {
		return '<font color="green">已审</font>';
	} else {
		return '<font color="red">未审</font>';
	}
}
function renderOrderFlag(e) {
	if (e.record.orderFlag == 1) {
		return '<div class="icon-lock">&nbsp;</div>';
	}else if(e.record.orderFlag == null || e.record.orderFlag== 0) {
		return '<div class="icon-unlock">&nbsp;</div>';
	} else if(e.record.orderFlag == -1) {
		return '<div class="icon-invalid">&nbsp;</div>';
	}
}
function renderContractFlag(e) {
	if (e.record.contractFlag == 1) {
		return '<div class="icon-lock">&nbsp;</div>';
	}else {
		return '<div class="icon-unlock">&nbsp;</div>';
	}
}
function onChuruTypesRenderer(e) {
    for (var i = 0, l = churuTypes.length; i < l; i++) {
        var g = churuTypes[i];
        if (g.id == e.value) return g.text;
    }
    return "其它";
}
function onCalcYsyfFlagRenderer(e) {
    for (var i = 0, l = calcYsyfFlags.length; i < l; i++) {
        var g = calcYsyfFlags[i];
        if (g.id == e.value) return g.text;
    }
    return "其它";
}

function onKaipiaoFlagRenderer(e) {
    for (var i = 0, l = kaipiaoFlags.length; i < l; i++) {
        var g = kaipiaoFlags[i];
        if (g.id == e.value) return g.text;
    }
    return "不开票";
}

function onChukuTypeRenderer(e) {
	for (var i = 0, l = chukuTypeFlags.length; i < l; i++) {
        var g = chukuTypeFlags[i];
        if (g.id == e.value) return g.text;
    }
    return "";
}

function onFahuoFlagRenderer(e) {
	for (var i = 0, l = fahuoFlags.length; i < l; i++) {
        var g = fahuoFlags[i];
        if (g.id == e.value) return g.text;
    }
    return "<font color=red>未完成</font>";
}

function onDabaoLaiyuanRenderer(e) {
	for (var i = 0, l = dabaoLaiyuans.length; i < l; i++) {
        var g = dabaoLaiyuans[i];
        if (g.id == e.value) return g.text;
    }
    return "<font>正常打包</font>";
}



function renderQianshouFlag(e) {
	if (e.record.isQianshou == 1) {
		return '<font color="green">已签收</font>';
	} else {
		return '<font color="red">未签收</font>';
	}
}

//return true停止  false继续
var checkRowBeforeEdit = function() {
	var grid = this.grid;
	var select = grid.getSelected();//选中行
	var editingRow = grid.findRow(function(row){//编辑行
	    if(row._editing) return true;
	});
	var isEditing = grid.isEditing();
	if(isEditing){//表格确定为编辑状态
		//编辑行新建时  选中行 等于 编辑行
		if(!editingRow.id && select.id == editingRow.id) {
			return true;
		}
		if (confirm("您还有正在编辑的单据，是否放弃编辑？")) {
			if(!editingRow.id) {
				grid.removeRow(editingRow);
			} else {
				grid.cancelEdit();
			}
			return false;
        }else{
        	return true;
        }
	}
};

function clearButtonEdit(e) {
	this.setValue("");
	this.setText("");
}

//屏蔽backSpace
function doKey(e) {
    var ev = e || window.event; //获取event对象   
    var obj = ev.target || ev.srcElement; //获取事件源   
    var t = obj.type || obj.getAttribute('type'); //获取事件源类型   
    if (ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea") {
        return false;
    }
}
//禁止后退键 作用于Firefox、Opera   
document.onkeypress=doKey;   
//禁止后退键  作用于IE、Chrome   
document.onkeydown=doKey;

//得到对象属性的个数
function getObjectSize(obj) {
	if(obj != null) {
		var i = 0;
		for(o in obj) {
			i++;
		}
		return i;//Object.keys(obj).length;
	} else {
		return 0;
	}
}


function rendererGongxuSort(e) {
    for (var i = 0, l = gongxuSorts.length; i < l; i++) {
        var g = gongxuSorts[i];
        if (g.id == e.value) {
        	return  g.text;
        }
    }
    return "";
}

//取绝对值
function renderTuihuoAbs(e) {
	if(e.record.chukuType == 'xiaoshouTuihuo' || e.record.rukuType == 'caigouTuihuo') {
		return Math.abs(Number(e.value));
	} else {
		return e.value;
	}
}
function renderAbs(e) {
	if(e && e.value) {
		return Math.abs(Number(e.value));
	} else {
		return 0;
	}
}

mini.VTypes["fixedNumber"] = function (v,args) {
    var n = parseInt(args);
    if (!v || isNaN(n)) return true;
    v = String(v);
    if (v.indexOf(".")==-1) return true;//没有小数点的情况
    if (!isFloat(v)){
    	mini.VTypes["fixedNumberErrorText"] = "请输入数字";
    	return false;
    };
    var fixedNumber = v.length-v.indexOf(".")-1;
    if (fixedNumber <= n) return true;
    else{
    	mini.VTypes["fixedNumberErrorText"] = "小数位数不得超过{0}位";
    	return false;
    } 
}

function isFloat(s) {
    if (s < 0) {
        s = -s;
    }
    var n = String(s);
    var nn = n.split(".");
    var n0 = String(nn[0]);
    var n1 = String(nn[1]);
    if (nn.length > 2 ||n0.length==0 || n1.length==0){
    	return false;
    }
    return n.length > 0 && !(/[^0-9.]/).test(n); 
}

function getGirstDayOfTheMonth(){
	var today = new Date();
	
    var year = today.getFullYear();
    var month = today.getMonth()+1;
    if (month<10){
        month = "0"+month;
    }
    var firstDay = year+"-"+month+"-"+"01";
    return firstDay;
}



function renderOrderChengbenFlag(e) {
	if (e.record.executeOrderChengben == 1) {
		return '<font color="green">记</font>';
	} else {
		return '<font color="red">不记</font>';
	}
}
