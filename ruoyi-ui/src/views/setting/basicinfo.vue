<template>
<el-card class="form-container" shadow="never">

    <el-alert title="注意！ 基本信息设置，若修改不当，会影响相关网站地址和logo，会造成页面显示和跳转出错。" type="warning" :closable="false"
              show-icon></el-alert>
    <el-form label-position="right" :model="form" :rules="rules" ref="form" label-width="150px"
             style="margin-top: 20px">
      <el-form-item prop="partSiteUrl">
        <span slot="label">商城前台网址</span>
        <el-input v-model="form.partSiteUrl" maxlength="256">
          <el-select v-model="httpSelect" slot="prepend" style="width: 100px">
            <el-option label="http://" value="1"></el-option>
            <el-option label="https://" value="2"></el-option>
          </el-select>
        </el-input>
      </el-form-item>
      <el-form-item prop="siteName">
        <span slot="label">商城名称</span>
        <el-input v-model="form.siteName" maxlength="256"/>
      </el-form-item>
       <el-form-item label="浏览器标签图标：" prop="labelLog">
              <single-upload v-model="form.labelLog"></single-upload>
            </el-form-item>
             <el-form-item label="商城LOGO：" prop="logo">
                    <single-upload v-model="form.logo"></single-upload>
                  </el-form-item>
                   <el-form-item label="管理端登录LOGO：" prop="adminLogo">
                          <single-upload v-model="form.adminLogo"></single-upload>
                        </el-form-item>
                         <el-form-item label="管理端LOGO：" prop="adminIndexLogo">
                                <single-upload v-model="form.adminIndexLogo"></single-upload>
                              </el-form-item>
                               <el-form-item label="商城登录页面图片：" prop="siteLoginPic">
                                      <single-upload v-model="form.siteLoginPic"></single-upload>
                                    </el-form-item>
        <el-form-item label="商家登录页面图片：" prop="storeLoginPic">
                                           <single-upload v-model="form.storeLoginPic"></single-upload>
                                         </el-form-item>



      <el-form-item label="商家开店协议">
        <tinymce v-model="form.storeOpenProtocol" :height="300"/>
      </el-form-item>
      <el-form-item label="商城用户注册协议">
        <tinymce v-model="form.siteRegisterProtocol" :height="300"/>
      </el-form-item>
      <el-form-item label="众筹支持者协议">
        <tinymce v-model="form.crowdFundingProtocol" :height="300"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateBaseInfoSet">保存设置</el-button>
      </el-form-item>
    </el-form>

    </el-card>
</template>

<script>
  import Tinymce from '@/components/Tinymce';

 import {
    queryBaseInfoSet,
    updateBaseInfoSet,
  } from '@/api/basicinfo';
import { getToken,get } from '@/utils/auth'
  import SingleUpload from '@/components/Upload/singleUpload'
  export default {
    components: {Tinymce},
    data() {
      return {
        isProcess: false, // 接口是否处理中
        httpSelect: '1', // 商城前台网址前缀 1 http 2 https
        uploadApi: process.env.VUE_APP_BASE_API+'/aliyun/oss/uploadToAliOss',// 上传文件路由
        headers: { // 设置上传的请求头部
          Authorization: 'Bearer ' + getToken()
        },
        form: { // 提交form表单
          partSiteUrl: '', // 商城前台网址（没有前缀http和https)
          siteName: '', // 商城名称
          phone: '', // 商城联系电话
          labelLog: '', // 标签页图标
          logo: '', // 网站LOGO
          adminLogo: '', // 后台登陆LOGO
          adminIndexLogo: '', // 后台首页LOGO
          siteLoginPic: '', // 前台登录页面图片
          storeLoginPic: '', // 商家登录页面图片
          copyrightInfo: '', // 后台版权信息
          storeOpenProtocol: '', // 商家开店协议
          siteRegisterProtocol: '', // 商城用户注册协议
          crowdFundingProtocol: '', // 众筹支持者协议
        },
        rules: { // 校验规则
          partSiteUrl: [
            {
              required: true,
              message: '请输入商城前台网址',
              trigger: 'blur'
            }
          ],
          siteName: [
            {
              required: true,
              message: '请输入商城名称',
              trigger: 'blur'
            }
          ]
        },
      }
    },
    created() {
      this.queryBaseInfoSet()
    },
    methods: {
      // 查询基本信息设置
      queryBaseInfoSet() {
        queryBaseInfoSet(1).then(res => {

          // 推荐给form表单根据属性，对照返回数据对象属性 挨个赋值
          this.form = {
            partSiteUrl: res.siteUrl.split('://')[1], // 商城前台网址（没有前缀http和https)
            siteName: res.siteName, // 商城名称
            phone: res.phone, // 商城联系电话
            labelLog: res.labelLog, // 标签页图标
            logo: res.logo, // 网站LOGO
            adminLogo: res.adminLogo, // 后台登陆LOGO
            adminIndexLogo: res.adminIndexLogo, // 后台首页LOGO
            siteLoginPic: res.siteLoginPic, // 前台登录页面图片
            storeLoginPic: res.storeLoginPic, // 商家登录页面图片
            copyrightInfo: res.copyrightInfo, // 后台版权信息
            storeOpenProtocol: res.storeOpenProtocol, // 商家开店协议
            siteRegisterProtocol: res.siteRegisterProtocol, // 商城用户注册协议
            crowdFundingProtocol: res.crowdFundingProtocol, // 众筹支持者协议
          };

          this.httpSelect = res.siteUrl.split('://')[0] == 'http' ? '1' : '2';
          if (this.$refs["form"]) {
            this.$refs["form"].resetFields();
          }
        })
      },
      // 修改基本信息设置
      updateBaseInfoSet() {
        this.$refs['form'].validate(valid => {
          if (valid && this.validateLength() && !this.isProcess) {
            // 如果商城前台网址不是以斜杠"/"结尾，自动补全斜杠"/"
            if (!this.form.partSiteUrl.endsWith("/")) {
              this.form.partSiteUrl = this.form.partSiteUrl + '/';
            }
            this.form.siteUrl = (this.httpSelect == '1' ? 'http://' : 'https://') + this.form.partSiteUrl;
            this.isProcess = true;
            updateBaseInfoSet(1,this.form).then(res => {
              this.isProcess = false;
              if (res == 1) {
                this.queryBaseInfoSet();
                this.$message({
                  message: '修改成功',
                  type: 'success'
                })
              } else {
                this.$message({
                  message: '修改失败',
                  type: 'error'
                })
              }
            })
          }
        });
      },
      // 上传标签页图标回调
      uploadLabelLogSuccess(res) {
        this.form.labelLog = res;
      },
      // 上传网站LOGO回调
      uploadLogoSuccess(res) {
        this.form.logo = res;
      },
      // 上传后台登陆LOGO回调
      uploadAdminLogoSuccess(res) {
        this.form.adminLogo = res;
      },
      // 上传后台首页LOGO回调
      uploadAdminIndexLogoSuccess(res) {
        this.form.adminIndexLogo = res;
      },
      // 上传前台登录页面图片回调
      uploadSiteLoginPicSuccess(res) {
        this.form.siteLoginPic = res;
      },
      // 上传商家登录页面图片回调
      uploadStoreLoginPicSuccess(res) {
        this.form.storeLoginPic = res;
      },
      // 验证协议长度
      validateLength() {
        if (this.form.storeOpenProtocol.length > 65535) {
          this.$message({
            message: '商家开店协议长度不能超过65535',
            type: 'warning'
          });
          return false;
        }
        if (this.form.siteRegisterProtocol.length > 65535) {
          this.$message({
            message: '商城用户注册协议长度不能超过65535',
            type: 'warning'
          });
          return false;
        }
        if (this.form.crowdFundingProtocol.length > 65535) {
          this.$message({
            message: '众筹支持者协议长度不能超过65535',
            type: 'warning'
          });
          return false;
        }
        return true;
      },
    }
  }
</script>
