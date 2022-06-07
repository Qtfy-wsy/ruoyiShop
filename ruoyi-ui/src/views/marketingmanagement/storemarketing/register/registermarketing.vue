<template>
  <div>
    <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="促销时间" prop="promotionTime">
        <el-date-picker type="datetimerange" v-model="formData.promotionTime" format="yyyy-MM-dd HH:mm:ss"
                        value-format="yyyy-MM-dd HH:mm:ss" :style="{width: '100%'}" start-placeholder="开始日期"
                        end-placeholder="结束日期" range-separator="至" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="送积分" prop="integral">
        <el-input v-model="formData.integral" placeholder="请输入送积分" clearable :style="{width: '100%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="选择优惠券" prop="isSendCoupon">
        <el-select v-model="formData.isSendCoupon" placeholder="请选择下拉选择" clearable :style="{width: '100%'}">
          <el-option v-for="(item, index) in isSendCouponOptions" :key="index" :label="item.label"
                     :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="启用状态">
        <el-switch
          style="vertical-align: sub"
          v-model="formData.isUseStatus"
          active-color="#13ce66"
          inactive-color="#ff4949">
        </el-switch>
      </el-form-item>
      <el-form-item size="large">
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import {
  queryRegisterMarketing, updateRegisterMarketing,addRegisterMarketing
} from '@/api/marketing/registermarketing';



export default {
  components: {},
  props: [],
  data() {
    return {
      formData: {
        promotionTime: ["2021-12-09 00:00:00", "2022-01-06 00:00:00"],
        integral: '',
        isSendCoupon: '',
        isUseStatus:true,
      },
      rules: {
        promotionTime: [{
          required: true,
          type: 'array',
          message: '请至少选择一个时间',
          trigger: 'change'
        }],
        integral: [{
          required: true,
          message: '请输入送积分',
          trigger: 'blur'
        }],
        isSendCoupon: [],
      },
      isSendCouponOptions: [{
        "label": "未选择优惠券",
        "value": 1
      }, {
        "label": "注册送优惠券",
        "value": 2
      }],
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (valid) {
          if (this.formData != undefined) {
            addRegisterMarketing(this.formData).then(response => {
              if (response.code === 200) {
                this.msgSuccess("提交成功");
                this.open = false;
                this.getList(this.queryStatus);
              }
            });
          }
        }
      });
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}

</script>
