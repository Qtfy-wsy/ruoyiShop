<template>
  <div class="app-container">
    <el-alert title="注意！ 短信接口，若修改不当，会影响短信的发送和接收。" type="warning" :closable="false" show-icon></el-alert>
    <el-form label-position="right" :model="form" :rules="rules" ref="form" label-width="180px"
             style="margin-top: 20px">
      <el-form-item prop="key">
        <span slot="label">AccessKey ID</span>
        <el-input v-model="form.key" maxlength="35"/>
      </el-form-item>
      <el-form-item prop="secret">
        <span slot="label">Access Key Secret</span>
        <el-input v-model="form.secret" maxlength="35"/>
      </el-form-item>
      <el-form-item prop="sign">
        <span slot="label">短信签名</span>
        <el-input v-model="form.sign" maxlength="15"/>
      </el-form-item>
      <el-form-item prop="templateId">
        <span slot="label">身份验证模板ID</span>
        <el-input v-model="form.templateId" maxlength="20"/>
        <div style="color: #999; line-height: initial; margin-top: 5px">您的身份码${code}，您正在进行身份验证，打死不要告诉别人！</div>
      </el-form-item>
      <el-form-item prop="writeoffTemplateId">
        <span slot="label">核销门店订单模板ID</span>
        <el-input v-model="form.writeoffTemplateId" maxlength="20"/>
        <div style="color: #999; line-height: initial; margin-top: 5px">
          ${storename}订单${code}的提货码是：${writeoffcode}，请妥善保管并及时到门店取货。
        </div>
      </el-form-item>
      <el-form-item prop="virtualOrderTemplateId">
        <span slot="label">核销虚拟订单模版ID</span>
        <el-input v-model="form.virtualOrderTemplateId" maxlength="20"/>
        <div style="color: #999; line-height: initial; margin-top: 5px">
          ${storename}订单${code}的兑换码是：${writeoffcode}，请妥善保管并及时兑换。
        </div>
      </el-form-item>
      <el-form-item prop="auditTemplateId">
        <span slot="label">团长审核模版ID</span>
        <el-input v-model="form.auditTemplateId" maxlength="20"/>
        <div style="color: #999; line-height: initial; margin-top: 5px">您的团长申请已于${submittime}审批${status}，特此通知。</div>
      </el-form-item>
      <el-form-item prop="settlementTemplateId">
        <span slot="label">团长佣金结算模版ID</span>
        <el-input v-model="form.settlementTemplateId" maxlength="20"/>
        <div style="color: #999; line-height: initial; margin-top: 5px">
          您的团购活动${communityname}佣金结算已成功，金额：${price}，特此通知。
        </div>
      </el-form-item>
      <el-form-item prop="withdrawTemplateId">
        <span slot="label">团长提现打款模版ID</span>
        <el-input v-model="form.withdrawTemplateId" maxlength="20"/>
        <div style="color: #999; line-height: initial; margin-top: 5px">您的提现申请已于${submittime}打款成功，金额：${price}，请尽快查收。
        </div>
      </el-form-item>
      <el-form-item prop="key">
        <el-button type="primary" @click="updateSmsSet">保存设置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {getLsSmsSetting, updateLsSmsSetting,} from '@/api/setting/LsSmsSetting'

  export default {
    data() {
      return {
        isProcess: false, // 接口是否处理中
        form: { // 提交form表单
          id: '', // 短信设置id
          key: '', // AccessKey ID
          secret: '', // Access Key Secret
          sign: '', // 短信签名
          templateId: '', // 身份验证模板ID
          writeoffTemplateId: '', // 核销门店订单模板ID
          virtualOrderTemplateId: '', // 核销虚拟订单模版ID
          auditTemplateId: '', // 团长审核模版ID
          settlementTemplateId: '', // 团长佣金结算模版ID
          withdrawTemplateId: '', // 团长提现打款模版ID
        },
        rules: { // 校验规则
          key: [
            {
              required: true,
              message: '请输入AccessKey ID',
              trigger: 'blur'
            }
          ],
          secret: [
            {
              required: true,
              message: '请输入Access Key Secret',
              trigger: 'blur'
            }
          ],
          sign: [
            {
              required: true,
              message: '请输入短信签名',
              trigger: 'blur'
            }
          ],
          templateId: [
            {
              required: true,
              message: '请输入身份验证模板ID',
              trigger: 'blur'
            }
          ],
          writeoffTemplateId: [
            {
              required: true,
              message: '请输入核销门店订单模板ID',
              trigger: 'blur'
            }
          ],
          virtualOrderTemplateId: [
            {
              required: true,
              message: '请输入核销虚拟订单模版ID',
              trigger: 'blur'
            }
          ],
          auditTemplateId: [
            {
              required: true,
              message: '请输入团长审核模版ID',
              trigger: 'blur'
            }
          ],
          settlementTemplateId: [
            {
              required: true,
              message: '请输入团长佣金结算模版ID',
              trigger: 'blur'
            }
          ],
          withdrawTemplateId: [
            {
              required: true,
              message: '请输入团长提现打款模版ID',
              trigger: 'blur'
            }
          ],
        },
      }
    },
    created() {
      this.querySmsSet()
    },
    methods: {
      // 查询短信设置
      querySmsSet() {
        getLsSmsSetting(1).then(res => {
          this.form = res.data;
          if (this.$refs["form"]) {
            this.$refs["form"].resetFields();
          }
        })
      },
      // 修改短信设置
      updateSmsSet() {
        this.$refs['form'].validate(valid => {
          if (valid && !this.isProcess) {
            this.isProcess = true;
            updateLsSmsSetting(this.form).then(response => {
              this.isProcess = false;
               if (response.code === 200) {
                                                      this.msgSuccess("修改成功");
                                                     this.querySmsSet();
                                         }

            })
          }
        });
      },
    }
  }
</script>
