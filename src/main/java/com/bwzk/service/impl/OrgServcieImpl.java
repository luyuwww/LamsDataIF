package com.bwzk.service.impl;

import ch.qos.logback.classic.Logger;
import com.bwzk.dao.i.da.SGroupMapper;
import com.bwzk.dao.i.da.SQzhMapper;
import com.bwzk.dao.i.da.SUserMapper;
import com.bwzk.dao.i.da.SUserroleMapper;
import com.bwzk.dao.i.oa.OaDepMapper;
import com.bwzk.dao.i.oa.OaOrgMapper;
import com.bwzk.dao.i.oa.OaUserMapper;
import com.bwzk.pojo.*;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.OrgService;
import com.bwzk.util.IsExistDepOrUser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("orgServiceImpl")
public class OrgServcieImpl extends BaseService implements OrgService {


    public String syncUserGroup(){
        String msg = syncGroup()+ IOUtils.LINE_SEPARATOR + syncUser();
        return msg;
    }

    private String syncGroup(){
        String msg = "";
        List<OaOrg> orgList = oaOrgMapper.listOrg();
        for (OaOrg oaOrg : orgList) {
            dualGroup(oaOrg);
        }

        List<OaDep> depList = oaOrgMapper.listDept();
        for (OaDep oaDep : depList) {
            dualGroup(oaDep);
        }
        msg = "部门同步成功.";
        return msg;
    }

    private String syncUser(){
        String msg = "";
        List<OaUser> oaUserLsit =  listOaUsersLess4();

        for (OaUser oaUser : oaUserLsit) {
            dualUser(oaUser);
        }
        msg = "用户同步成功.";
        return msg;
    }

    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());

    public List<SUser> listDaUsers() {
        return sUserMapper.getAllUserList();
    }

    public List<OaUser> listOaUsers() {
        OaUserExample example = new OaUserExample();
        example.createCriteria().andLoginidIsNotNull();
        List<OaUser> resultr = oaUserMapper.selectByExample(example);
        System.out.println(resultr.size());
        return resultr;
    }

    public List<OaUser> listOaUsersLess4(){
        String[] inStatus = {"0","1","2","3"};
        OaUserExample example = new OaUserExample();
        example.createCriteria().andLoginidIsNotNull()
        .andStatusIn(Arrays.asList(inStatus));
        List<OaUser> resultr = oaUserMapper.selectByExample(example);
        return resultr;
    }

    private Boolean dualGroup(OaOrg oaOrg){
        Boolean result = Boolean.FALSE;
        //OA主键 gfzj放id  depid放supsubcomid/

        String gfzj = "org_"+oaOrg.getId();//oaid
        String depid = "org_"+oaOrg.getSupsubcomid();//oapid
        SGroup daDep = sGroupMapper.getGroupByGfzj(gfzj);
        if(null == daDep){
            SGroup sgroup = new SGroup();
            Integer maxdid = getMaxDid("s_group");
            SGroup parent = sGroupMapper.getGroupByGfzj(depid);
            Integer pid = (parent == null ? 0 : parent.getDid());
            sgroup.setDid(maxdid);
            sgroup.setPid(pid);
            sgroup.setGname(oaOrg.getSubcompanyname());
            sgroup.setQzh(defaultQzh);
            sgroup.setGfzj(gfzj);
            sgroup.setDepid(depid);
            sGroupMapper.insert(sgroup);
            result = Boolean.TRUE;
            log.error("增加一个部门. " + gfzj);
        }else{
            if(daDep.getGname().equals(oaOrg.getSubcompanyname())
                    && ("org_"+oaOrg.getSupsubcomid()).equals(daDep.getDepid())){
                log.error("没有改变. " + gfzj);
                result = Boolean.TRUE;
            }else{
                SGroup parent = sGroupMapper.getGroupByGfzj(depid);
                Integer pid = (parent == null ? defaultDeptPid : parent.getDid());
                daDep.setPid(pid);
                daDep.setGname(oaOrg.getSubcompanyname());
                daDep.setGfzj(gfzj);
                daDep.setDepid(depid);
                sGroupMapper.updateByPrimaryKey(daDep);
                result = Boolean.TRUE;
                log.error("更新一个部门. " + gfzj);
            }
        }
        return result;
    }

    private Boolean dualGroup(OaDep oaDep){
        Boolean result = Boolean.FALSE;
        //OA主键 gfzj放id  depid放supsubcomid/

        String gfzj = "dep_"+oaDep.getId();//oaid
        String depid = "dep_"+oaDep.getSupdepid();//oapid
        SGroup daDep = sGroupMapper.getGroupByGfzj(gfzj);
        if(null == daDep){
            SGroup sgroup = new SGroup();
            Integer maxdid = getMaxDid("S_GROUP");

            SGroup parent = oaDep.getSupdepid().equals("0") ?
                    sGroupMapper.getGroupByGfzj("org_"+oaDep.getSubcompanyid1()) : sGroupMapper.getGroupByGfzj(depid);
            Integer pid = (parent == null ? defaultDeptPid : parent.getDid());
            sgroup.setDid(maxdid);
            sgroup.setPid(pid);
            sgroup.setGname(oaDep.getDepartmentname());
            sgroup.setQzh(defaultQzh);
            sgroup.setGfzj(gfzj);
            sgroup.setDepid(depid);
            sGroupMapper.insert(sgroup);
            result = Boolean.TRUE;
            log.error("增加一个部门. " + gfzj);
        }else{
            if(daDep.getGname().equals(oaDep.getDepartmentname())
                    && ("dep_"+oaDep.getSupdepid()).equals(daDep.getDepid())){
                log.error("没有改变. " + gfzj);
                result = Boolean.TRUE;
            }else{
                SGroup parent = sGroupMapper.getGroupByGfzj(depid);
                Integer pid = (parent == null ? defaultDeptPid : parent.getDid());
                daDep.setPid(pid);
                daDep.setGname(oaDep.getDepartmentname());
                daDep.setGfzj(gfzj);
                daDep.setDepid(depid);
                sGroupMapper.updateByPrimaryKey(daDep);
                result = Boolean.TRUE;
                log.error("更新一个部门. " + gfzj);
            }
        }
        return result;
    }

    private Boolean dualUser(OaUser oaUser){
        Boolean result = Boolean.FALSE;
        //OA主键 gfzj放id  depid放supsubcomid/

        String esbid = oaUser.getId();//oaid
        String depid = "dep_"+oaUser.getDepartmentid();//oapid
        SUser user = sUserMapper.getUserByEsbid(esbid);
        if(null == user){
            SUserWithBLOBs ssuser = new SUserWithBLOBs();
            Integer maxdid = getMaxDid("S_USER");
            
            SGroup parent =  sGroupMapper.getGroupByGfzj(depid);
            Integer pid = (parent == null ? defaultDeptPid : parent.getDid());
            ssuser.setDid(maxdid);
            ssuser.setPid(pid);
            ssuser.setUsercode(oaUser.getLoginid());
            ssuser.setUsername(oaUser.getLastname());
            ssuser.setEmail(oaUser.getEmail());
            ssuser.setEsbid(esbid);
            ssuser.setEsbcode(depid);
            sUserMapper.insert(ssuser);
            SUserrole userrole = new SUserrole();
            userrole.setDid(getMaxDid("S_USERROLE"));
            userrole.setYhid(maxdid);
            userrole.setJsid(jsid);
            sUserroleMapper.insert(userrole);
            log.error("用户:" + esbid + " 关联角色");

            result = Boolean.TRUE;
            log.error("增加一个用户. " + ssuser.getUsercode()+":"+ssuser.getUsername());
        }else{
            if(user.getUsername().equals(oaUser.getLastname())
                    && ("dep_"+oaUser.getDepartmentid()).equals(user.getEsbcode())){
                log.error(user.getUsername() + " 没有改变.");
                result = Boolean.TRUE;
            }else{
                SGroup parent = sGroupMapper.getGroupByGfzj(depid);
                Integer pid = (parent == null ? defaultDeptPid : parent.getDid());
                user.setPid(pid);
                user.setUsername(oaUser.getLastname());
                user.setEmail(oaUser.getEmail());
                user.setEsbid(esbid);
                user.setEsbcode(depid);
                sUserMapper.updateByPrimaryKey(user);
                result = Boolean.TRUE;
                log.error(user.getUsername() + "更新一个用户.");
            }
        }
        return result;
    }

    @Autowired
    private OaOrgMapper oaOrgMapper;
    @Autowired
    private OaDepMapper oaDepMapper;
    @Autowired
    private OaUserMapper oaUserMapper;

    @Autowired
    private SQzhMapper sQzhMapper;
    @Autowired
    private SGroupMapper sGroupMapper;
    @Autowired
    private SUserMapper sUserMapper;
    @Autowired
    private SUserroleMapper sUserroleMapper;
}
