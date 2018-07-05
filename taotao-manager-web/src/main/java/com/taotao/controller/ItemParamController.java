package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Resource
    private ItemParamService paramService;

    //获取规格参数模板列表
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemParamList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows) {

        return paramService.getItemParamList(page, rows);
    }

    //查询某个类目的规格参数模板
    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable Long cid) {

        return paramService.getItemParamByCid(cid);
    }

    //添加某个类目的规格参数模板
    @RequestMapping(value = "/save/{cid}", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable Long cid,
                                        @RequestParam String paramData) {
        return paramService.insertItemParam(cid, paramData);
    }

    //删除规格参数模板(可批量)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteItemParams(@RequestParam String ids){
        return paramService.deleteItemParam(ids);
    }

}
