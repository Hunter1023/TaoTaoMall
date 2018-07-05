package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.PictureResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.FastDFSClient;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Controller
public class ItemController {
    @Value("${IMAGE_SERVER_BASE_URL}")
    private String IMAGE_SERVER_BASE_URL;

    @Resource
    private ItemService itemService;

    //商品列表
    @RequestMapping(value = "/item/list", method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult getItemList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows) {


        return itemService.getItemList(page, rows);
    }

    //上传图片,dubbo不能传输MultipartFile，于是在Controller中进行上传；
    //使用了produces＝MediaType.TEXT_PLAIN_VALUE 的方法，返回值必须是String
    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8",
            method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestPart("uploadFile") MultipartFile picFile) {

        PictureResult result = new PictureResult();

        //判断图片是否为空
        if (picFile.isEmpty()) {
            result.setError(1);
            result.setMessage("图片为空");
        } else {
            try {
                //获取图片扩展名，不要"."
                String originalFilename = picFile.getOriginalFilename();
                String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

                // 上传图片
                FastDFSClient client = new FastDFSClient(
                        "classpath:resource/fastdfs-client.conf");
                String url = client.uploadFile(picFile.getBytes(), extName);
                //拼接图片服务器的域名
                url = IMAGE_SERVER_BASE_URL + url;

                //把url响应给客户端
                result.setError(0);
                result.setUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
                result.setError(1);
                result.setMessage("图片上传失败");
            }
        }

        String json = JsonUtils.objectToJson(result);
        return json;
    }

    /**
     * 添加商品
     */
    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult saveItem(TbItem item, String desc) {
        return itemService.createItem(item, desc);
    }

}
