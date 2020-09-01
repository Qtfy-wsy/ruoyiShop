<template>
  <div class="app-container">
    <div class="filter-container">
    <el-form :model="listQuery" ref="queryForm" :inline="true" label-width="68px">
          <el-form-item label="商品编号" prop="id">
            <el-input
              v-model="listQuery.id"
              placeholder="请输入商品编号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
<el-form-item label="商品标题" prop="name">
            <el-input
              v-model="listQuery.name"
              placeholder="请输入商品标题"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
<el-form-item label="店铺名称" prop="storeName">
            <el-input
              v-model="listQuery.storeName"
              placeholder="请输入店铺名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>

          </el-form-item>
        </el-form>


    </div>

    <el-table
      :data="list"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="商品图片" width="80">
        <template slot-scope="scope"><img :src="scope.row.url" width="50" height="50"></template>
      </el-table-column>
      <el-table-column label="商品标题" min-width="200">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="销售价">
        <template slot-scope="scope">{{ scope.row.price |toFixed }}</template>
      </el-table-column>
      <el-table-column label="分类">
        <template slot-scope="scope">{{ scope.row.thirdCategory.name }}</template>
      </el-table-column>
      <el-table-column label="品牌">
        <template slot-scope="scope">{{ scope.row.brand.name }}</template>
      </el-table-column>
      <el-table-column label="店铺名称">
        <template slot-scope="scope">{{ scope.row.storeName }}</template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <router-link :to="{path:'/marketingManager/marketingGoods/goodAudit',query:{id:scope.row.id}}">
            <el-button type="success" style="margin: 0 5px 0 0" size="mini">审核</el-button>
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
    queryAllStoreToBeAuditdSpus, //分页查询所有店铺待审核的商品
    queryCategoryByParentId //根据分类id查询所有子分类信息
  } from '@/api/store/goodsauditlist';

  export default {
    data() {
      return {
        isProcess: false, //接口是否正在处理中
        listLoading: true, //数据加载中
        listQuery: {
          pageNum: 0,
          pageSize: 10,
          id: '',
          name: '',
          storeName: ''
        }, // 查询参数
        list: null, // 订单数据,
        total: null, // 数据总数,
        cateOptions: [{
          value: '',
          label: '全部',
        }],//分类数据
        cateSelectedOptions: [],
        multipleSelection: [],
        props: {
          lazy: true,
          lazyLoad(node, resolve) {
            const {level} = node;
            let parentId;
            let categoryList = [];
            if (node.value) {
              parentId = node.value;
            } else {
              parentId = 0;
            }
            queryCategoryByParentId(parentId).then(response => {
              categoryList = categoryList.concat(response);
              const nodes = Array.from(categoryList)
                .map(item => ({
                  value: item.id,
                  label: item.name,
                  leaf: level >= 2
                }));
              // 通过调用resolve将子节点数据返回，通知组件数据加载完成
              resolve(nodes);
            });
          }
        },
      }
    },
    created() {
      this.getList()
    },
    filters: {
      toFixed(value) {
        // 截取当前数据到小数点后两位
        let realVal = Number(value).toFixed(2);
        if (realVal == 0.00) {
          return '';
        }
        return realVal;
      },
    },
    methods: {
      getList() {
        this.listLoading = true
        queryAllStoreToBeAuditdSpus(this.listQuery).then(response => {
          this.list = response.data;
          this.total = response.recordsTotal;
          this.listLoading = false
        });
      },
      // 过滤查询
      handleQuery() {

        this.listQuery.pageNum = 0;
        this.getList();
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
