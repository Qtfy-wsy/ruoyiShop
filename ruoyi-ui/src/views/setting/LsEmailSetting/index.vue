<template>
  <div class="app-container">
    <el-alert title="注意！ 邮箱接口，若修改不当，会影响邮件的接收和验证。" type="warning" :closable="false" show-icon></el-alert>
    <el-form label-position="right" :model="form" :rules="rules" ref="form" label-width="150px"
             style="margin-top: 20px">
      <el-form-item prop="senderMail">
        <span slot="label">发信邮箱</span>
        <el-input v-model="form.senderMail" maxlength="30"/>
      </el-form-item>
      <el-form-item prop="senderName">
        <span slot="label">发信人</span>
        <el-input v-model="form.senderName" maxlength="30"/>
      </el-form-item>
      <el-form-item prop="smtpServer">
        <span slot="label">SMTP服务器</span>
        <el-input v-model="form.smtpServer" maxlength="30"/>
      </el-form-item>
      <el-form-item prop="smtpPort">
        <span slot="label">SMTP端口号</span>
        <el-input v-model="form.smtpPort" maxlength="30"/>
      </el-form-item>
      <el-form-item prop="username">
        <span slot="label">SMTP账号</span>
        <el-input v-model="form.username" maxlength="30"/>
      </el-form-item>
      <el-form-item prop="password">
        <span slot="label">SMTP密码</span>
        <el-input type="password" v-model="form.password" maxlength="30"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateEmailSet">保存设置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {getLsEmailSetting, updateLsEmailSetting,} from '@/api/setting/LsEmailSetting'

  export default {
    data() {
      return {
        isProcess: false, // 接口是否处理中
        form: { // 提交form表单
          id: '', // 邮箱设置id
          senderMail: '', // 发信邮箱
          senderName: '', // 发信人
          smtpServer: '', // SMTP服务器
          smtpPort: '', // SMTP端口号
          username: '', // SMTP账号
          password: '', // SMTP密码
        },
        rules: { // 校验规则
          senderMail: [
            {
              required: true,
              message: '请输入发信邮箱',
              trigger: 'blur'
            }
          ],
          senderName: [
            {
              required: true,
              message: '请输入发信人',
              trigger: 'blur'
            }
          ],
          smtpServer: [
            {
              required: true,
              message: '请输入SMTP服务器',
              trigger: 'blur'
            }
          ],
          smtpPort: [
            {
              required: true,
              message: '请输入SMTP端口号',
              trigger: 'blur'
            }
          ],
          username: [
            {
              required: true,
              message: '请输入SMTP账号',
              trigger: 'blur'
            }
          ],
          password: [
            {
              required: true,
              message: '请输入SMTP密码',
              trigger: 'blur'
            }
          ],

        },
      }
    },
    created() {
      this.queryEmailSet()
    },
    methods: {
      // 查询邮箱设置
      queryEmailSet() {
        getLsEmailSetting(1).then(res => {
          this.form = res;
          if (this.$refs["form"]) {
            this.$refs["form"].resetFields();
          }
        })
      },
      // 修改邮箱设置
      updateEmailSet() {
        this.$refs['form'].validate(valid => {
          if (valid && !this.isProcess) {
            this.isProcess = true;
            this.form.id=1;
            updateLsEmailSetting(this.form).then(response => {
              this.isProcess = false;
               if (response.code === 200) {
                                        this.msgSuccess("修改成功");
                                        this.queryEmailSet()
                           }

            })
          }
        });
      },
    }
  }
</script>
