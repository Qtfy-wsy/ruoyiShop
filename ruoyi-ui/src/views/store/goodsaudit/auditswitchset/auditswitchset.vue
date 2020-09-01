<template>
  <div class="app-container">
    <el-form label-position="right" label-width="150px" style="margin-top: 20px">
      <el-form-item label="商品审核开关">
        <el-switch
          style="vertical-align: sub"
          v-model="Value1"
          active-color="#13ce66"
          inactive-color="#ff4949">
        </el-switch>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateData">保存设置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

  import {
    queryBaseInfoSet, //查询基本信息和高级信息设置
    setAuditSwitch //设置审核开关状态
  } from '@/api/store/auditswitchset';

  export default {
    data() {
      return {
        Value1: true,
        isProcess: false, //接口是否正在处理中
      }
    },
    created() {
      //查询审核状态
      this.queryStatus()
    },
    methods: {

      //查询审核状态
      queryStatus() {
        queryBaseInfoSet().then(response => {
          if (response.storeSpuAudit == 0) {
            this.Value1 = true;
          } else if (response.storeSpuAudit == 1) {
            this.Value1 = false;
          }
        });
      },

      //保存更新
      updateData() {
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        let storeSpuAudit = this.Value1 ? 0 : 1;
        setAuditSwitch(storeSpuAudit).then(response => {
          this.isProcess = false;
          if (response == 1) {
            this.$message({
              type: 'success',
              message: '更新成功!'
            });
          } else {
            this.$message({
              type: 'error',
              message: '更新失败!'
            });
          }
          this.queryStatus();
        });
      }
    }
  }
</script>
