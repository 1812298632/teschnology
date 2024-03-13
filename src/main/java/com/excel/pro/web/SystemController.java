package com.excel.pro.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excel.pro.dao.SystemSetDao;
import com.excel.pro.entity.ResponseEntity;
import com.excel.pro.entity.Systemset;
import com.excel.pro.entity.TreeEntity;
import com.excel.pro.service.SystemSetService;
import com.excel.pro.util.ConstantUtil;
import com.excel.pro.util.RequestUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SystemController {

    @Resource
    private SystemSetDao systemSetDao;

    @Resource
    private SystemSetService systemSetService;

    //aaaa


    @RequestMapping("/querySystemList")
    public ResponseEntity querySystemList(HttpServletRequest request) {


        ResponseEntity responseEntity = new ResponseEntity();
        QueryWrapper<Systemset> queryWrapper = new QueryWrapper<>();


        queryWrapper.lambda().isNull(Systemset::getParentid);


        List<Systemset> systemsetList = systemSetDao.selectList(queryWrapper);

        ArrayList<TreeEntity> topTreeEntitie = new ArrayList<>();

        for (Systemset systemset : systemsetList) {

            QueryWrapper<Systemset> childWrapper = new QueryWrapper<>();
            childWrapper.lambda().eq(Systemset::getParentid, systemset.getId());

            List<Systemset> systemsetChildList = systemSetDao.selectList(childWrapper);
            TreeEntity topTreeEntity = new TreeEntity();

            topTreeEntity.setId(systemset.getId());
            topTreeEntity.setLabel(systemset.getTitle());


            ArrayList<TreeEntity> childTreeEntities = new ArrayList<>();

            for (Systemset systemsetChild : systemsetChildList) {
                TreeEntity childTreeEntity = new TreeEntity();
                childTreeEntity.setId(systemsetChild.getId());
                childTreeEntity.setLabel(systemsetChild.getTitle());
                childTreeEntity.setNum(systemsetChild.getNum().toString());
                childTreeEntity.setParentid(systemsetChild.getParentid());

                childTreeEntities.add(childTreeEntity);
            }
            if(childTreeEntities.size() >= 1){
                topTreeEntity.setChildren(childTreeEntities);
            }

            topTreeEntitie.add(topTreeEntity);
        }


        responseEntity.setResList(topTreeEntitie);
        responseEntity.setResMessage("查询成功");
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        return responseEntity;
    }

    /*public List<TreeEntity> tree(TreeEntity childTreeEntity){
        QueryWrapper<Systemset> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().eq(Systemset::getParentid, childTreeEntity.getId());

        List<Systemset> systemsetChildList = systemDao.selectList(queryWrapper);
        if(systemsetChildList.size() == 0){
            return null;
        }

        for (Systemset systemsetChild : systemsetChildList) {
            childTreeEntity.setId(systemsetChild.getId());
            childTreeEntity.setLabel(systemsetChild.getTitle());
            tree(childTreeEntity);



        }

    }*/
    @RequestMapping("/querySystemSelect")
    public ResponseEntity querySystemSelect(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();
        QueryWrapper<Systemset> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().isNull(Systemset::getParentid);

        List<Systemset> systemsetList = systemSetDao.selectList(queryWrapper);

        responseEntity.setResList(systemsetList);
        responseEntity.setResMessage("查询成功");
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        return responseEntity;
    }


    @RequestMapping("/queryMaxId")
    public ResponseEntity queryMaxId(HttpServletRequest request) {
        ResponseEntity responseEntity = new ResponseEntity();
        QueryWrapper<Systemset> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().select(Systemset::getId).orderByDesc(Systemset::getId);

        List<Systemset> systemsetList = systemSetDao.selectList(queryWrapper);

        Systemset systemset = systemsetList.get(0);
        Long id = systemset.getId();
        id = id + 1;

        responseEntity.setId(id.toString());
        responseEntity.setResMessage("查询成功");
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        return responseEntity;
    }


    @RequestMapping("/queryMaxNum")
    public ResponseEntity queryMaxNum(HttpServletRequest request) {
        String requestParam = RequestUtil.getJsonObjectData(request);
        String parentid = RequestUtil.getObjectValue(requestParam, "parentid");

        ResponseEntity responseEntity = new ResponseEntity();
        QueryWrapper<Systemset> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().eq(Systemset::getParentid, parentid).select(Systemset::getNum).orderByDesc(Systemset::getNum);

        List<Systemset> systemsetList = systemSetDao.selectList(queryWrapper);
        Long num = 1L;

        if (systemsetList.size() != 0) {
            Systemset systemset = systemsetList.get(0);
            num = systemset.getNum();
            num = num + 1;
        }


        responseEntity.setId(num.toString());
        responseEntity.setResMessage("查询成功");
        responseEntity.setRes(ConstantUtil.RESPONSE_SUCCESS);

        return responseEntity;
    }


    @RequestMapping("/addSystem")
    public ResponseEntity addSystem(HttpServletRequest request) throws Exception {

        ResponseEntity responseEntity = new ResponseEntity();
        try {

            String requestParam = RequestUtil.getJsonObjectData(request);
            String id = RequestUtil.getObjectValue(requestParam, "id");
            String num = RequestUtil.getObjectValue(requestParam, "num");
            String title = RequestUtil.getObjectValue(requestParam, "title");
            String parentid = RequestUtil.getObjectValue(requestParam, "parentid");

            Systemset systemset = new Systemset();

            if (!StringUtils.isEmpty(id)) {
                systemset.setId(Long.parseLong(id));

            }

            if (!StringUtils.isEmpty(num)) {
                systemset.setNum(Long.parseLong(num));

            }

            if (!StringUtils.isEmpty(title)) {
                systemset.setTitle(title);

            }

            if (!StringUtils.isEmpty(parentid)) {
                systemset.setParentid(Long.parseLong(parentid));

            }

            systemSetService.insert(systemset,responseEntity);

        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());

            e.printStackTrace();
        }

        return responseEntity;
    }




    @RequestMapping("/updateSystem")
    public ResponseEntity updateSystem(HttpServletRequest request) throws Exception {

        ResponseEntity responseEntity = new ResponseEntity();
        try {

            String requestParam = RequestUtil.getJsonObjectData(request);
            String id = RequestUtil.getObjectValue(requestParam, "id");
            String num = RequestUtil.getObjectValue(requestParam, "num");
            String title = RequestUtil.getObjectValue(requestParam, "title");
            String parentid = RequestUtil.getObjectValue(requestParam, "parentid");

            Systemset systemset = new Systemset();

            if (!StringUtils.isEmpty(id)) {
                systemset.setId(Long.parseLong(id));

            }
            if (!StringUtils.isEmpty(num)) {
                systemset.setNum(Long.parseLong(num));

            }
            if (!StringUtils.isEmpty(title)) {
                systemset.setTitle(title);

            }
            if (!StringUtils.isEmpty(parentid)) {
                systemset.setParentid(Long.parseLong(parentid));
            }

            systemSetService.updateSystem(systemset,responseEntity);

        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());

            e.printStackTrace();
        }

        return responseEntity;
    }



    @RequestMapping("/deleteSystem")
    public ResponseEntity deleteSystem(HttpServletRequest request) throws Exception {

        ResponseEntity responseEntity = new ResponseEntity();
        try {

            String requestParam = RequestUtil.getJsonObjectData(request);
            String id = RequestUtil.getObjectValue(requestParam, "id");


            Systemset systemset = new Systemset();

            if (!StringUtils.isEmpty(id)) {
                systemset.setId(Long.parseLong(id));

            }

            systemSetService.deleteSystem(systemset,responseEntity);

        } catch (Exception e) {
            responseEntity.setRes(ConstantUtil.RESPONSE_ERROR);
            responseEntity.setResMessage(e.getMessage());

            e.printStackTrace();
        }

        return responseEntity;
    }
}



