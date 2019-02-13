package com.goabraod.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.entity.A;
import com.base.entity.AB;
import com.base.entity.GoEvisa;
import com.base.entity.GoOrderTravel;
import com.base.entity.GoOrders;
import com.base.entity.GoTravel;
import com.base.entity.GoUser;
import com.base.entity.SysUser;
import com.base.util.ByteByUrl;
import com.base.util.ExcelUtil;
import com.base.util.FileControl;
import com.base.util.SHA;
import com.base.util.String_operation;
import com.base.util.UploadImg;
import com.goabraod.service.TravUserService;
import com.google.common.base.Strings;

@Controller
@RequestMapping("/trav")
public class TravUserController extends BaseController{

	private final Logger logger =Logger.getLogger(TravUserController.class);
	
	@Autowired
	private TravUserService travUserService;
	
	
	@RequestMapping("/getLogin")
	@ResponseBody
	public Map<String, Object> getLogin(HttpServletRequest request,HttpServletResponse response){
		this.setCrossHeader(response);
		//创建map
		Map<String, Object> map = new HashMap<String,Object>();
		//接收参数表
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		//非空判断
		this.validateNullException(userName, "用户名不能为空！");
		this.validateNullException(passWord, "密码不能为空！");
		//执行逻辑
		try {
			//通过用户名获取用户信息
			SysUser travUser = travUserService.getLogin(userName);
			//判断是否存在
			if (travUser != null) {
				//获取加密后的密码
				String passWord1 = SHA.encrypt(passWord);
				String passWord2 = travUser.getPassWord();
				if (passWord1.equals(passWord2)) {
					map.put("success", true);
					map.put("data", travUser);
		            map.put("message", "登录成功");
				}else {
					map.put("success", true);
					map.put("data", new HashMap<>());
		            map.put("message", "对不起，密码错误，请重新输入！");
				}
			}else {
				map.put("success", true);
				map.put("data", new HashMap<>());
	            map.put("message", "对不起，用户名不存在！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
            map.put("success", false);
            map.put("message", "执行失败！");
            map.put("data", new HashMap<>());
		}
		return map;
	}
	
	/**
	 * 获取批文列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getApplyPersonInfo")
	@ResponseBody
	public Map<String, Object> getApplyPersonInfo(HttpServletRequest request,HttpServletResponse response){
		this.setCrossHeader(response);
		//创建map
		Map<String, Object> map = new HashMap<String,Object>();
		//接收参数
		String airport = request.getParameter("airport");
		String visaType = request.getParameter("visaType");
		String pageIndex = request.getParameter("pageIndex");
		String tableStatus = request.getParameter("tableStatus");
		//非空判断
		this.validateNullException(airport, "机场参数不能为空");
		this.validateNullException(visaType, "类型不能为空");
		this.validateNullException(tableStatus, "打印状态");
		//执行逻辑
		try {
			//页码
			int pageSize = 5;
			int pageStart = Integer.parseInt(pageIndex) * pageSize;
			//创建总数组
			JSONArray jsArray = new JSONArray();
			Map<String, Object> parma = new HashMap<String, Object>();
			parma.put("airport", airport);
			parma.put("pageLimit", pageSize);
			parma.put("pageStart", pageStart);
			if (tableStatus.equals("0")) {
				parma.put("goOrderPassInfo", "L");
				parma.put("goOrderDownloadStatus", "N");
			}else if (tableStatus.equals("1")) {
				parma.put("goOrderPassInfo", "L");
				parma.put("goOrderDownloadStatus", "Y");
			}else if (tableStatus.equals("2")) {
				parma.put("goOrderPassInfo", "Y");
				parma.put("goOrderDownloadStatus", "Y");
			}
			parma.put("goOrderVisaTypeCode", visaType);
			//获取订单列表
			List<GoOrders> orderInfoList = travUserService.getYOrderInfo(parma);
			//循环获取
			for(int i=0;i<orderInfoList.size();i++) {
				//创建jsonobject
				JSONObject jsObject = new JSONObject();
				//创建数组
				JSONArray jsonArray = new JSONArray();
				//获取订单
				GoOrders goOrders = orderInfoList.get(i);
				//获取订单号
				String goId = goOrders.getGoId();
				//获取用户信息
				List<GoOrderTravel> goOrderTravels = serveService.getGoOrderTravels(goId);
				//循环
				for(int j=0;j<goOrderTravels.size();j++) {
					//创建对象
					JSONObject jsonObject = new JSONObject();
					//获取gvId
					String gvId = goOrderTravels.get(j).getGvId();
					//判断
					if (gvId.substring(0, 1).equals("v")) {
						//获取用户对象
						GoUser allGoUsers = serveService.getAllGoUsers(gvId);
						//获取信息
						String guName = allGoUsers.getGuName();
						jsonObject.put("guName", guName);
						String guPassportNum = allGoUsers.getGuPassportNum();
						jsonObject.put("guPassportNum", guPassportNum);
						GoTravel goTravel = serveService.getAllGoIssu(gvId).get(0);
						String gvFlightNum = goTravel.getGvFlightNum();
						jsonObject.put("gvFlightNum", gvFlightNum);
						String gvEntryDate = goTravel.getGvEntryDate();
						jsonObject.put("gvEntryDate", gvEntryDate);
						jsonObject.put("gfArrAp", airport);
						jsonArray.add(jsonObject);
					}
				}
				jsObject.put("goId", goId);
				jsObject.put("goUserList", jsonArray);
				jsArray.add(jsObject);
			}
			//获取总数
			A a = travUserService.getYOrderInfoSum(parma);
			String yOrderInfoSum="";
			if (a == null) {
				yOrderInfoSum = "0";
			}else {
				yOrderInfoSum = a.getA();
			}
			//写入对象
			JSONObject jObject = new JSONObject();
			jObject.put("total", yOrderInfoSum);
			jObject.put("userList", jsArray);
			//返回参数
			map.put("data", jObject);
			map.put("success", true);
            map.put("message", "查询成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
            map.put("success", false);
            map.put("message", "执行失败！");
            map.put("data", new HashMap<>());
		}
		return map;
	}
	
	/**
	 * 获取用户基本信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getOnePersonInfo")
	@ResponseBody
	public Map<String, Object> getOnePersonInfo(HttpServletRequest request,HttpServletResponse response){
		this.setCrossHeader(response);
		Map<String, Object> map = new HashMap<String, Object>();
		//接收参数
		String goId = request.getParameter("goId");
		String airport = request.getParameter("airport");
		//旅行id不能为空
		this.validateNullException(goId, "订单id不能为空");
		this.validateNullException(airport, "机场信息不能为空");
		//执行逻辑
		try {
			JSONArray jsonArray = new JSONArray();
			//创建json对象
			JSONObject jsonObject = new JSONObject();
			//获取中间表集合
			List<GoOrderTravel> goOrderTravels = serveService.getGoOrderTravels(goId);
			//循环获取
			for(int i=0;i<goOrderTravels.size();i++) {
				//获取单独
				GoOrderTravel goOrderTravel = goOrderTravels.get(i);
				//获取
				String gvId = goOrderTravel.getGvId();
				if (gvId.substring(0, 1).equals("v")) {
					//获取用户基本信息
					jsonObject.put("goId", goId);
					jsonObject.put("gvAirport", airport);
					GoUser allGoUsers = serveService.getAllGoUsers(gvId);
					String guName = allGoUsers.getGuName();
					jsonObject.put("guNameCn", guName);
					String guFamiNameE = allGoUsers.getGuFamiNameE();
					String guFirstNameE = allGoUsers.getGuFirstNameE();
					String guNameE = guFamiNameE + " " + guFirstNameE;
					jsonObject.put("guNameEn", guNameE);
					String guSexC = allGoUsers.getGuSexC();
					jsonObject.put("guSexC", guSexC);
					String guBirthDate = allGoUsers.getGuBirthDate();
					jsonObject.put("guBirthDate", guBirthDate);
					String guNationalityC = allGoUsers.getGuNationalityC();
					jsonObject.put("guNationalityC", guNationalityC);
					String guPassportNum = allGoUsers.getGuPassportNum();
					jsonObject.put("guPassportNum", guPassportNum);
					String guIssueDate = allGoUsers.getGuIssueDate();
					jsonObject.put("guIssueDate", guIssueDate);
					String guExpiryDate = allGoUsers.getGuExpiryDate();
					jsonObject.put("guExpiryDate", guExpiryDate);
					//获取用户旅行信息
					GoTravel goTravel = serveService.getAllGoIssu(gvId).get(0);
					String gvEntryDate = goTravel.getGvEntryDate();
					jsonObject.put("gvEntryDate", gvEntryDate);
					String gvLeaveDate = goTravel.getGvLeaveDate();
					jsonObject.put("gvLeaveDate", gvLeaveDate);
					String gvFlightNum = goTravel.getGvFlightNum();
					jsonObject.put("gvFlightNum", gvFlightNum);
					String gvPrintPlace = goTravel.getGvPrintPlace();
					jsonObject.put("gvPrintPlace", gvPrintPlace);
					jsonArray.add(jsonObject);
				}
			}
			//饭回信息
			map.put("success", true);
			map.put("data", jsonArray);
            map.put("message", "查询成功！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
            map.put("success", false);
            map.put("message", "执行失败！");
            map.put("data", new HashMap<>());
		}
		return map;
	}
	
	/**
	 * 下载申请表格
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downLoadFile")
	@ResponseBody
	public Map<String, Object> downLoadFile(HttpServletRequest request,HttpServletResponse response){
		this.setCrossHeader(response);
		Map<String, Object> map = new HashMap<String, Object>();
		//接收参数
		String goIds = request.getParameter("goIds");
		//非空判断
		this.validateNullException(goIds, "订单id串不能为空");
		//执行逻辑
		try {
			//创建list
			List<JSONObject> list = new LinkedList<JSONObject>();
    		//复制出新表格
    		//定义原表格的路径
    		String oldPath = "/Users/liyangguang/Documents/ccmini/vietnamVisa.xls";
    		//文件名
    		String fileName = "vietnamVisa"+new Date().getTime()+".xls";
    		//定义新表格的路径
    		String newPath = "/Users/liyangguang/Documents/cdmini/"+fileName;
    		//进行复制
    		FileControl.copyFile(oldPath, newPath);
			//切分订单ID串
			String[] arr = goIds.split("-");
			//创建序号
			int No = 1;
			//循环得出goId
			for(int i=0;i<arr.length;i++) {
				//获取订单id
				String goId = arr[i];
				//写入下载状态
				Map<String, Object> parma = new HashMap<>();
				parma.put("goId", goId);
				parma.put("goOrderDownloadStatus", "Y");
				travUserService.updateDownloadStatus(parma);
				//获取用户信息
				List<GoOrderTravel> goOrderTravels = serveService.getGoOrderTravels(goId);
				//循环用户
				for(int j=0;j<goOrderTravels.size();j++) {
					//创建对象
					JSONObject jsonObject = new JSONObject();
					//获取每一个用户旅行id
					String gvId = goOrderTravels.get(j).getGvId();
					//判断
					if (gvId.substring(0,1).equals("v")) {
						//获取用户信息
						GoUser allGoUsers = serveService.getAllGoUsers(gvId);
						jsonObject.put("guNo", No);
						//获取基本信息
						String guNameCn = allGoUsers.getGuName().replace("-", "");
						jsonObject.put("guNameCn", guNameCn);
						String guFamiNameE = allGoUsers.getGuFamiNameE();
						String guFirstNameE = allGoUsers.getGuFirstNameE();
						String guNameEn = guFamiNameE + " " + guFirstNameE;
						jsonObject.put("guNameEn", guNameEn);
						String guSexC = allGoUsers.getGuSexC();
						if (guSexC.equals("男")) {
							guSexC = "M";
						}else {
							guSexC = "F";
						}
						jsonObject.put("guSexC", guSexC);
						String guBirthDate = allGoUsers.getGuBirthDate();
						jsonObject.put("guBirthDate", guBirthDate);
						String guNationalityC = allGoUsers.getGuNationalityC();
						if (guNationalityC.equals("中国")) {
							guNationalityC = "CN";
						}
						jsonObject.put("guNationalityC", guNationalityC);
						String guPassportNum = allGoUsers.getGuPassportNum();
						jsonObject.put("guPassportNum", guPassportNum);
						String guIssueDate = allGoUsers.getGuIssueDate();
						jsonObject.put("guIssueDate", guIssueDate);
						String guExpiryDate = allGoUsers.getGuExpiryDate();
						jsonObject.put("guExpiryDate", guExpiryDate);
						//获取旅行信息
						GoTravel goTravel = serveService.getAllGoIssu(gvId).get(0);
						String gvEntryDate = goTravel.getGvEntryDate();
						jsonObject.put("gvEntryDate", gvEntryDate);
						String gvLeaveDate = goTravel.getGvLeaveDate();
						jsonObject.put("gvLeaveDate", gvLeaveDate);
						String gvPrintPlace = goTravel.getGvPrintPlace();
						jsonObject.put("gvPrintPlace", gvPrintPlace);
						String gvFlightNum = goTravel.getGvFlightNum();
						jsonObject.put("gvFlightNum", gvFlightNum);
						//获取航班信息
						String gvAirport = "";
						Map<String, Object> parm = new HashMap<String,Object>();
						parm.put("fgArrCou", "越南");
						parm.put("fgFgNo", gvFlightNum);
						AB flightInfo = travUserService.getFlightInfo(parm);
						if (flightInfo == null) {
							gvAirport = "";
						}else {
							gvAirport = flightInfo.getA();
						}
						jsonObject.put("gvAirport", gvAirport);
						list.add(jsonObject);
						No += 1;
					}
				}
			}
			//写入表格
			ExcelUtil.writeExcel1(list, newPath);
			//下载表格
			this.setResponseHeader(response, newPath, fileName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
            map.put("success", false);
            map.put("message", "执行失败！");
            map.put("data", new HashMap<>());
		}
		return map;
	}
	
	/**
	 * 上传落地签批文
	 * @param files
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/uploadPdfFiles")
    @ResponseBody
    public Map<String, Object> uploadPdfMap(@RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request, HttpServletResponse response) {
        this.setCrossHeader(response);
        //集合
        Map<String, Object> map = new HashMap<String, Object>();
        //接收
        String goId = request.getParameter("goId");

        //判断集合中是否有文件
        if (files.length == 0) {
            this.validateObjectNullException(files, "文件列表为空！");
        }
        //判断订单id是否存在
        if (Strings.isNullOrEmpty(goId)) goId = "";
//		this.validateNullException(giId, "导游id不能为空");

        try {
            //////////////////////////////////

            //pdf对象
            GoEvisa goEvisa = new GoEvisa();
            //创建容器
            JSONArray jsonArray = new JSONArray();

            //穿件临时文件夹
            String contextpath = "/tmp/tempAppPdfFileDir" + new Date().getTime();
            File f = new File(contextpath);
            if (!f.exists()) {
                f.mkdirs();
            }
            //遍历集合
            for (int i = 0; i < files.length; i++) {
                //图片集合
                List<String> list = new LinkedList<String>();
                //获取单个文件
                CommonsMultipartFile pdfFile = files[i];
                //生成创建时间
                String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE").format(new Date());

                if (!pdfFile.isEmpty()) {
                    JSONObject jsonObject = new JSONObject();
//					if (!guPhoto.trim().equals("")) {
                    //在tempfileDir的临时文件夹下创建一个新的和上传文件名一样的文件
                    String name = pdfFile.getOriginalFilename();
//                    String name = pdfFile.getName();
                    jsonObject.put("gfName", name);
                    String filename = new Date().getTime() + pdfFile.getOriginalFilename();
                    String filepath = contextpath + "/" + filename;
                    pdfFile.transferTo(new File(filepath));
                    if (pdfFile.getOriginalFilename().contains(".pdf")) {
                        //pdf文件转换成图片
                        List<String> pdfToImagePath = UploadImg.pdfToImagePath(filepath, filename.replace(".pdf", ""));
                        for (int j = 0; j < pdfToImagePath.size(); j++) {
                            //删除pdf文件
                            String_operation.deleteFile(filepath);
                            //图片路径
                            String filepath0 = contextpath + "/" + filename.replace(".pdf", "") + j + ".jpg";
                            logger.info("--------SUCCESS-------");
                            //加入集合
                            list.add(filepath0);
                        }
                    } else {
                        list.add(filepath);
                    }
                    for (int j = 0; j < list.size(); j++) {
                        String filepath1 = list.get(j);
                        //签发编号
                        String gfId = "Pdf_" + new Date().getTime() + (int) (Math.random() * 10);
                        jsonObject.put("gfId", gfId);
                        //图片转换base64
                        String getImageStr = ByteByUrl.GetImageStr(new FileInputStream(new File(filepath1)));
                        //图片的名字
                        String key0 = "Pdf_" + new Date().getTime() + (int) (Math.random() * 10) + ".jpg";
                        //图片上传七牛云
                        String putBase64image = new UploadImg().putBase64image(getImageStr, key0);
                        //图片路径
                        String gfUrl = "https://cdn.travbao.com/" + putBase64image;
                        jsonObject.put("gfUrl", gfUrl);
                        //执行删除图片
                        String_operation.deleteDirectory(filepath);

                        jsonObject.put("gfType", "C");
                        jsonObject.put("gfCreateTime", createTime);
                        //写入表格
                        goEvisa.setGfId(gfId);
                        goEvisa.setGfUrl(gfUrl);
                        goEvisa.setGfName(name);
                        goEvisa.setGfType("C");
                        goEvisa.setGfCreateTime(createTime);
                        webServeService.insertPdfUrl(goEvisa);
                        //插入中间表
                        String gotId = "got" + new Date().getTime() + (int) (Math.random() * 9);
                        GoOrderTravel goOrderTravel = new GoOrderTravel();
                        goOrderTravel.setGotId(gotId);
                        goOrderTravel.setGvId(gfId);
                        goOrderTravel.setGoId(goId);
                        goOrderTravel.setGotcreatetime(createTime);
                        serveService.addOrderTravel(goOrderTravel);
                        jsonArray.add(jsonObject);
                    }
                }
            }
            //返回参数
            map.put("success", true);
            map.put("data", jsonArray);
            map.put("message", "执行成功！");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "执行失败！");
            map.put("data", new HashMap<>());
        }
        return map;
    }

}
