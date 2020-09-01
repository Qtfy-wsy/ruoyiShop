<template> 
    <el-card class="form-container" shadow="never">
        <el-form :model="sysSystemSetting" :rules="rules" ref="SysSystemSettingFrom" label-width="150px">




                    <el-form-item prop="siteUrl">
                            <span slot="label">商城前台网址</span>
                            <el-input v-model="sysSystemSetting.siteUrl" maxlength="256">

                            </el-input>
                          </el-form-item>


                    <el-form-item
                            label="网站名称"
                            prop="siteName">
                        <el-input v-model="sysSystemSetting.siteName" style="width: 370px;"/>
                    </el-form-item>


                   <el-form-item label="浏览器标签图标：" prop="labelLog">
                                <single-upload v-model="sysSystemSetting.labelLog"></single-upload>
                              </el-form-item>
                               <el-form-item label="商城LOGO：" prop="logo">
                                      <single-upload v-model="sysSystemSetting.logo"></single-upload>
                                    </el-form-item>
                                     <el-form-item label="管理端登录LOGO：" prop="adminLogo">
                                            <single-upload v-model="sysSystemSetting.adminLogo"></single-upload>
                                          </el-form-item>
                                           <el-form-item label="管理端LOGO：" prop="adminIndexLogo">
                                                  <single-upload v-model="sysSystemSetting.adminIndexLogo"></single-upload>
                                                </el-form-item>
                                                 <el-form-item label="商城登录页面图片：" prop="siteLoginPic">
                                                        <single-upload v-model="sysSystemSetting.siteLoginPic"></single-upload>
                                                      </el-form-item>
                          <el-form-item label="商家登录页面图片：" prop="storeLoginPic">
                                                             <single-upload v-model="sysSystemSetting.storeLoginPic"></single-upload>
                                                           </el-form-item>



                        <el-form-item label="商家开店协议">
                          <tinymce v-model="sysSystemSetting.storeOpenProtocol" :height="300"/>
                        </el-form-item>
                        <el-form-item label="商城用户注册协议">
                          <tinymce v-model="sysSystemSetting.siteRegisterProtocol" :height="300"/>
                        </el-form-item>
                        <el-form-item label="众筹支持者协议">
                          <tinymce v-model="sysSystemSetting.crowdFundingProtocol" :height="300"/>
                        </el-form-item>




            <el-form-item>
                <el-button type="primary" @click="onSubmit('SysSystemSettingFrom')">提交</el-button>
                <el-button v-if="!isEdit" @click="resetForm('SysSystemSettingFrom')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>

      import {
         queryBaseInfoSet,
         updateBaseInfoSet,
       } from '@/api/basicinfo';
    import SingleUpload from '@/components/Upload/singleUpload'
    import Tinymce from '@/components/Tinymce';
    const defaultSysSystemSetting = {
        name: ''
    };
    export default {

        name: 'SysSystemSettingDetail',
        components: {SingleUpload,Tinymce},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            sysSystemSetting:
            Object.assign({},
        defaultSysSystemSetting),
            rules: {
                name: [
                    {required: true, message: '请输入品牌名称', trigger: 'blur'},
                    {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
                ],
                    logo
            :
                [
                    {required: true, message: '请输入品牌logo', trigger: 'blur'}
                ],
                    sort
            :
                [
                    {type: 'number', message: '排序必须为数字'}
                ],
            }
        }
        },
        created() {

                queryBaseInfoSet(1).then(response => {
                    this.sysSystemSetting = response;
            })
                ;

        },
        methods: {
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if(valid) {
                        this.$confirm('是否提交数据', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            if(this.isEdit
                    )
                        {
                            updateBaseInfoSet(this.sysSystemSetting).then(response => {

                                this.$refs[formName].resetFields();
                                this.$message({
                                    message: '修改成功',
                                    type: 'success',
                                    duration: 1000
                                });
                                this.$router.back();



                        })
                            ;
                        }
                    else
                        {
                            updateBaseInfoSet(1,this.sysSystemSetting).then(response => {
                                if(response.code == 200
                        )
                            {
                                this.$refs[formName].resetFields();
                                this.sysSystemSetting = Object.assign({},
                            defaultSysSystemSetting)
                                ;
                                this.$message({
                                    message: '提交成功',
                                    type: 'success',
                                    duration: 1000
                                });
                                this.$router.back();
                            }
                        else
                            {
                                this.$message({
                                    message: response.msg,
                                    type: 'error',
                                    duration: 1000
                                });
                            }

                        })
                            ;
                        }
                    })
                        ;

                    } else {
                        this.$message({
                            message: '验证失败',
                            type: 'error',
                            duration: 1000
                        });
                return false;
            }
            })
                ;
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.sysSystemSetting = Object.assign({},
            defaultSysSystemSetting)
                ;
            }
        }
    }
</script>
<style>
</style>


