<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input placeholder="帮助标题" v-model="listQuery.name" size="medium" style="width: 200px;" class="filter-item"/>
      <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleQuery">搜索
      </el-button>
      <br/>
      <router-link to="/marketingManager/marketingGoods/addhelp">
        <el-button class="filter-item" size="medium" style="margin-left: 5px;" type="primary" icon="el-icon-plus">添加帮助
        </el-button>
      </router-link>
      <el-button class="filter-item" size="medium" style="margin-left: 5px;" type="danger" icon="el-icon-delete"
                 @click="toBatchDeleteHelp">批量删除
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="helpList"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" align="center" width="40"/>
      <el-table-column label="帮助标题" min-width="250">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="帮助分类">
        <template slot-scope="scope">{{ scope.row.cateName }}</template>
      </el-table-column>
      <el-table-column label="排序">
        <template slot-scope="scope">{{ scope.row.sort }}</template>
      </el-table-column>
      <el-table-column label="是否显示">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isShow == '0'" type="success">是</el-tag>
          <el-tag v-if="scope.row.isShow == '1'" type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150">
        <template slot-scope="scope">
          <router-link :to="{path:'/marketingManager/marketingGoods/updatehelp',query:{id:scope.row.id}}">
            <el-button type="primary" size="mini">编辑</el-button>
          </router-link>
          <el-button size="mini" type="danger" @click="toDeleteHelp(scope.row.id)">删除</el-button>
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
  import {queryHelpList, deleteHelp} from '@/api/cms/helplist';

  export default {
    data() {
      return {
        isProcess: false, // 接口是否处理中
        delIds: [],// 批量删除选中id数组
        listLoading: true, // 列表加载标识
        helpList: null, // 列表数据集合
        total: null, // 总记录数
        listQuery: {
          pageNum: 0, // 当前页码
          pageSize: 10, // 每页显示记录数
          name: '' // 帮助标题
        },
      }
    },
    created() {
      this.getList()
    },
    methods: {
      // 查询帮助列表
      getList() {
        this.listLoading = true;
        queryHelpList(this.listQuery).then((response) => {
          this.helpList = response.data;
          this.total = response.recordsTotal;
          this.listLoading = false
        })
      },
      // 过滤查询
      handleQuery() {
        this.listQuery.pageNum = 0;
        this.getList();
      },
      // 改变当前页码
      handleCurrentChange(val) {
        this.listQuery.pageNum = val - 1;
        this.getList();
      },
      // 改变每页显示记录数
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.getList();
      },
      // 删除帮助二次确认
      toDeleteHelp(id) {
        this.$confirm('是否确认删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteHelp(id);
        })
      },
      // 多选选中帮助
      handleSelectionChange(val) {
        if (val && val.length > 0) {
          this.delIds = val.map(row => row.id);
        } else {
          this.delIds = [];
        }
      },
      // 批量删除帮助二次确认
      toBatchDeleteHelp() {
        if (this.delIds && this.delIds.length > 0) {
          this.$confirm('是否确认删除, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.deleteHelp(this.delIds);
          })
        } else {
          this.$message({
            message: '请至少选择一个帮助',
            type: 'warning'
          })
        }
      },
      // 删除帮助
      deleteHelp(ids) {
        if (!this.isProcess) {
          this.isProcess = true;
          deleteHelp({"ids": ids.toString()}).then(res => {
            this.isProcess = false;
            if (res >= 1) {
              this.getList();
              this.delIds = [];
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            } else {
              this.$message({
                message: '删除失败!',
                type: 'error'
              })
            }
          });
        }
      },
    }
  }
</script>
