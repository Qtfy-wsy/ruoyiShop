<template>
  <div class="app-container">
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="支付宝" name="1">
        <el-form label-position="right" :model="paySetCommon.aliPaySet" :rules="aliPaySetRules" ref="aliPaySetForm"
                 label-width="200px" style="margin-top: 20px">
          <el-form-item prop="appId">
            <span slot="label">应用ID(appId)</span>
            <el-input v-model="paySetCommon.aliPaySet.appId" maxlength="2048"/>
          </el-form-item>
          <el-form-item prop="appPrivateKey">
            <span slot="label">开发者私钥(RSA(SHA256))</span>
            <el-input v-model="paySetCommon.aliPaySet.appPrivateKey" type="textarea" :rows="6"
                      maxlength="2048"></el-input>
          </el-form-item>
          <el-form-item prop="alipayPublicKey">
            <span slot="label">支付宝公钥(RSA(SHA256))</span>
            <el-input v-model="paySetCommon.aliPaySet.alipayPublicKey" type="textarea" :rows="6"
                      maxlength="2048"></el-input>
          </el-form-item>
          <el-form-item label="是否启用">
            <el-select v-model="paySetCommon.aliPaySet.isUse" style="width: 100px">
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePaySet('aliPaySetForm')">保存设置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="微信(公众号、扫码、H5)" name="2">
        <el-form label-position="right" :model="paySetCommon.wechatPaySet" :rules="wechatPaySetRules"
                 ref="wechatPaySetForm" label-width="150px" style="margin-top: 20px">
          <el-form-item prop="appId">
            <span slot="label">APPID</span>
            <el-input v-model="paySetCommon.wechatPaySet.appId" maxlength="2048"/>
          </el-form-item>
          <el-form-item prop="appSecret">
            <span slot="label">AppSecret</span>
            <el-input v-model="paySetCommon.wechatPaySet.appSecret" maxlength="2048"/>
          </el-form-item>
          <el-form-item prop="merchantNum">
            <span slot="label">商户号</span>
            <el-input v-model="paySetCommon.wechatPaySet.merchantNum" maxlength="2048"/>
          </el-form-item>
          <el-form-item prop="apiKey">
            <span slot="label">API密钥</span>
            <el-input v-model="paySetCommon.wechatPaySet.apiKey" maxlength="2048"/>
          </el-form-item>

          <el-form-item prop="loginNotice">
                      <span slot="label">登录回调地址</span>
                      <el-input v-model="paySetCommon.wechatPaySet.loginNotice" maxlength="2048"/>
                    </el-form-item>
          <el-form-item label="是否启用">
            <el-select v-model="paySetCommon.wechatPaySet.isUse" style="width: 100px">
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePaySet('wechatPaySetForm')">保存设置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="银联" name="3">
        <el-form label-position="right" :model="paySetCommon.unionPaySet" :rules="unionPaySetRules"
                 ref="unionPaySetForm" label-width="150px" style="margin-top: 20px">
          <el-form-item prop="merchantNum">
            <span slot="label">商户号</span>
            <el-input v-model="paySetCommon.unionPaySet.merchantNum" maxlength="2048"/>
          </el-form-item>
          <el-form-item label="是否启用">
            <el-select v-model="paySetCommon.unionPaySet.isUse" style="width: 100px">
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePaySet('unionPaySetForm')">保存设置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="微信(APP)" name="5">
        <el-form label-position="right" :model="paySetCommon.wechatAppPaySet" :rules="wechatPaySetRules"
                 ref="wechatAppPaySetForm" label-width="150px" style="margin-top: 20px">
          <el-form-item prop="appId">
            <span slot="label">APPID</span>
            <el-input v-model="paySetCommon.wechatAppPaySet.appId" maxlength="2048"/>
          </el-form-item>
          <el-form-item prop="appSecret">
            <span slot="label">AppSecret</span>
            <el-input v-model="paySetCommon.wechatAppPaySet.appSecret" maxlength="2048"/>
          </el-form-item>
          <el-form-item prop="merchantNum">
            <span slot="label">商户号</span>
            <el-input v-model="paySetCommon.wechatAppPaySet.merchantNum" maxlength="2048"/>
          </el-form-item>
          <el-form-item prop="apiKey">
            <span slot="label">API密钥</span>
            <el-input v-model="paySetCommon.wechatAppPaySet.apiKey" maxlength="2048"/>
          </el-form-item>
          <el-form-item label="是否启用">
            <el-select v-model="paySetCommon.wechatAppPaySet.isUse" style="width: 100px">
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePaySet('wechatAppPaySetForm')">保存设置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="微信(小程序)" name="6">
        <el-form label-position="right" :model="paySetCommon.wechatAppletPaySet" :rules="wechatPaySetRules"
                 ref="wechatAppletPaySetForm" label-width="150px" style="margin-top: 20px">
          <el-form-item prop="appId">
            <span slot="label">APPID</span>
            <el-input v-model="paySetCommon.wechatAppletPaySet.appId" maxlength="2048"/>
          </el-form-item>
          <el-form-item prop="appSecret">
            <span slot="label">AppSecret</span>
            <el-input v-model="paySetCommon.wechatAppletPaySet.appSecret" maxlength="2048"/>
          </el-form-item>
          <el-form-item prop="merchantNum">
            <span slot="label">商户号</span>
            <el-input v-model="paySetCommon.wechatAppletPaySet.merchantNum" maxlength="2048"/>
          </el-form-item>
          <el-form-item prop="apiKey">
            <span slot="label">API密钥</span>
            <el-input v-model="paySetCommon.wechatAppletPaySet.apiKey" maxlength="2048"/>
          </el-form-item>
          <el-form-item label="是否启用">
            <el-select v-model="paySetCommon.wechatAppletPaySet.isUse" style="width: 100px">
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePaySet('wechatAppletPaySetForm')">保存设置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import {queryPaySet, updatePaySet,} from '@/api/setting/LsPaySetting'

  export default {
    data() {
      return {
        isProcess: false, // 接口是否处理中
        activeName: '1', // 选中的支付设置类别，1 支付宝 2 微信支付(扫码，公众号，H5) 3 银联 4预存款 5 微信支付（app） 6 微信小程序支付
        paySetCommon: {
          aliPaySet: {}, // 支付宝设置
          wechatPaySet: {}, // 微信支付(扫码，公众号，H5)设置
          unionPaySet: {}, // 银联设置
          prePaySet: {}, // 预存款设置
          wechatAppPaySet: {}, // 微信支付（app）设置
          wechatAppletPaySet: {} // 微信小程序支付
        },
        aliPaySetRules: { // 支付宝设置校验规则
          appId: [
            {
              required: true,
              message: '请输入应用ID(appId)',
              trigger: 'blur'
            }
          ],
          appPrivateKey: [
            {
              required: true,
              message: '请输入开发者私钥(RSA(SHA256))',
              trigger: 'blur'
            }
          ],
          alipayPublicKey: [
            {
              required: true,
              message: '请输入支付宝公钥(RSA(SHA256))',
              trigger: 'blur'
            }
          ],
        },
        wechatPaySetRules: { // 微信设置校验规则
          appId: [
            {
              required: true,
              message: '请输入APPID',
              trigger: 'blur'
            }
          ],
          appSecret: [
            {
              required: true,
              message: '请输入AppSecret',
              trigger: 'blur'
            }
          ],
          merchantNum: [
            {
              required: true,
              message: '请输入商户号',
              trigger: 'blur'
            }
          ],
          apiKey: [
            {
              required: true,
              message: '请输入API密钥',
              trigger: 'blur'
            }
          ],
        },
        unionPaySetRules: { // 银联设置校验规则
          merchantNum: [
            {
              required: true,
              message: '请输入商户号',
              trigger: 'blur'
            }
          ],
        },
      }
    },
    created() {
      this.queryPaySet()
    },
    methods: {
      // 查询支付设置
      queryPaySet() {
        queryPaySet().then(res => {
          this.paySetCommon = res;
          if (this.$refs["aliPaySetForm"]) {
            this.$refs["aliPaySetForm"].resetFields();
          }
          if (this.$refs["wechatPaySetForm"]) {
            this.$refs["wechatPaySetForm"].resetFields();
          }
          if (this.$refs["unionPaySetForm"]) {
            this.$refs["unionPaySetForm"].resetFields();
          }
          if (this.$refs["wechatAppPaySetForm"]) {
            this.$refs["wechatAppPaySetForm"].resetFields();
          }
          if (this.$refs["wechatAppletPaySetForm"]) {
            this.$refs["wechatAppletPaySetForm"].resetFields();
          }
        })
      },
      // 修改支付设置
      updatePaySet(formName) {
        this.$refs[formName].validate(valid => {
          if (valid && !this.isProcess) {
            this.isProcess = true;
            updatePaySet(this.paySetCommon, this.activeName).then(res => {
              this.isProcess = false;
               if (res>= 1) {
                                                                    this.msgSuccess("修改成功");
                                                                  this.queryPaySet();
                                                       }else{
                                                         this.msgSuccess("修改失败");
                                                       }

            })
          }
        });
      },
    }
  }
</script>
