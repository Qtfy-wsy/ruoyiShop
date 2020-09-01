<template>
  <div class="app-container">
    <div class="filter-container">
    </div>

    <el-table
      :data="list"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="用户名">
        <template slot-scope="scope">{{ scope.row.username }}</template>
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
      <el-table-column label="下级数量" width="80">
        <template slot-scope="scope">{{ scope.row.subSpreadCustomerCount }}</template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <router-link
            :to="{path:'/distributionmanagement/distributioncustomer/subdistributioncustomerlist',query:{id:scope.row.id}}">
            <el-button type="success" size="mini">查看下级</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="listQuery.pageNum+1" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>

  import {
    spreadCustomerList
  } from '@/api/distributioncustomerlist';

  export default {
    data() {
      return {
        isProcess: false, //接口是否正在处理中
        stateValue: '',
        typeValue: '',
        listLoading: true,
        listQuery: {
          pageNum: 0,
          pageSize: 10,
        }, // 查询参数
        list: null, // 页面数据,
        total: null, // 数据总数,
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        spreadCustomerList(this.listQuery).then(response => {
          this.list = response.data;
          this.total = response.recordsTotal;
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
