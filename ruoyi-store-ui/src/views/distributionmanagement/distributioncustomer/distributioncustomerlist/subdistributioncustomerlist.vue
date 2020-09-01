<template>
  <div class="app-container">
    <el-tag type="info"
            style="width: 100%; padding: 20px 0; height: auto; line-height: normal; text-align: center; font-size: 20px">
      下级数量： <span>{{count}}</span>
    </el-tag>
    <router-link to="/distributionmanagement/distributioncustomer/distributioncustomerlist">
      <el-button class="filter-item" size="medium" style="margin: 10px 0;" type="primary" icon="el-icon-back">返回分销会员列表
      </el-button>
    </router-link>
    <el-table
      :data="list"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="用户名">
        <template slot-scope="scope">{{ scope.row.userName }}</template>
      </el-table-column>
      <el-table-column label="等级">
        <template slot-scope="scope">{{ scope.row.customerLevel.name }}</template>
      </el-table-column>
      <el-table-column label="手机号">
        <template slot-scope="scope">{{ scope.row.mobile }}</template>
      </el-table-column>
      <el-table-column label="邮箱">
        <template slot-scope="scope">{{ scope.row.email }}</template>
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '1'" type="success">正常</el-tag>
          <el-tag v-if="scope.row.status == '2'" type="danger">冻结</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="类型" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.type == '1'">普通</span>
          <span v-if="scope.row.type == '2'">商家</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>

  import {
    querySpreadCustomerByCustomerId
  } from '@/api/subdistributioncustomerlist';


  export default {
    data() {
      return {
        listLoading: true,
        list: null, // 页面数据,
        total: null, // 数据总数,
        count: 0
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        querySpreadCustomerByCustomerId(this.$route.query.id).then(response => {
          this.list = response;
          this.count = response.length;
          this.listLoading = false
        });
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val - 1;
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.getList();
      },
    }
  }
</script>
