<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input placeholder="品牌名称" size="medium" v-model="listQuery.brandName" maxlength="20" style="width: 150px;"
                class="filter-item"/>
      <el-input placeholder="店铺名称" size="medium" v-model="listQuery.storeName" maxlength="20" style="width: 150px;"
                class="filter-item"/>
      <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleQuery">搜索
      </el-button>
      <br/>
      <el-button class="filter-item" size="medium" style="margin-left: 5px;" type="primary" icon="el-icon-check"
                 @click="passDialog">通过
      </el-button>
      <el-button class="filter-item" size="medium" style="margin-left: 5px;" type="danger" icon="el-icon-close"
                 @click="refuseBatchShow">拒绝
      </el-button>
    </div>

    <el-table
      :data="list"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" align="center" width="80"/>
      <el-table-column label="申请店铺">
        <template slot-scope="scope">{{ scope.row.storeName }}</template>
      </el-table-column>
      <el-table-column label="品牌LOGO">
        <template slot-scope="scope"><img :src="scope.row.url" height="36" style="vertical-align: middle">
        </template>
      </el-table-column>
      <el-table-column label="品牌证书">
        <template slot-scope="scope"><a :href="scope.row.certificatUrl" target="_blank"><img
          :src="scope.row.certificatUrl" height="36" style="vertical-align: middle"></a></template>
      </el-table-column>
      <el-table-column label="品牌名称">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button  type="text" icon="el-icon-edit" size="mini" @click="passOne(scope.row.id)">通过</el-button>
          <el-button type="text" icon="el-icon-delete" size="mini" @click="refuseOneShow(scope.row.id)">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>


    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="listQuery.pageNum+1" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog title="品牌审核拒绝" :visible.sync="refuseDialogVisible">
      <el-form label-position="right" :model="form" :rules="rules" ref="form" label-width="100px">
        <el-form-item prop="reason">
          <span slot="label">拒绝原因</span>
          <el-input type="text" v-model="form.reason"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeWindow">取消</el-button>
        <el-button type="primary" @click="refuse">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

  import {
    isEmpty,
  } from '@/utils/validate';

  import {
    queryMySelfBrands,  //分页查询自定义品牌
    passMySelfBrandAudit, //通过自定义品牌审核
    refuseMySelfBrandAudit, //拒绝自定义品牌审核
    batchPassMySelfBrandAudit,//批量通过自定义品牌审核
    batchRefuseMySelfBrandAudit,//批量拒绝自定义品牌审核
  } from '@/api/store/custombrandsauditlist';


  export default {
    data() {
      return {
        updateType: '',//操作类型  0 单条操作  1 批量操作
        isProcess: false, //接口是否正在处理中
        refuseDialogVisible: false,
        listLoading: true, //页面加载中
        selectedIds: [], //选中的 id 集合
        listQuery: {
          pageNum: 0,
          pageSize: 10,
          brandName: '',
          storeName: '',
        }, // 查询参数
        list: null, // 订单数据,
        total: null, // 数据总数,
        multipleSelection: [],
        form: {
          id: '',  //品牌 ID
          reason: '' //拒绝原因
        },
        rules: { //校验规则
          reason: [
            {
              required: true,
              trigger: 'blur',
              validator: isEmpty,
            }
          ]
        },
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        queryMySelfBrands(this.listQuery).then(response => {
          this.list = response.data;
          this.total = response.recordsTotal;
          this.listLoading = false
        });
      },
      handleSelectionChange(val) {
        if (val && val.length > 0) {
          this.selectedIds = val.map(row => row.id);
        } else {
          this.selectedIds = [];
        }
      },
      closeWindow() {
        this.$nextTick(() => {
          this.$refs['form'].resetFields();
        });
        this.form.reason = '';
        this.refuseDialogVisible = false;
      },
      //翻页
      handleCurrentChange(val) {
        this.listQuery.pageNum = val - 1;
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.getList();
      },
      // 审核单条记录
      passOne(id) {
        this.$confirm('确定要通过该品牌审核吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.isProcess) {
            return;
          }
          this.isProcess = true;
          passMySelfBrandAudit(id).then(response => {
            this.isProcess = false;
            if (response == 1) {
              this.$message({
                type: 'success',
                message: '审核通过成功!'
              });
              this.getList();
            } else {
              this.$message({
                type: 'erroe',
                message: '审核通过失败!'
              })
            }
          });
        });
      },
      //单条拒绝时显示拒绝窗口
      refuseOneShow(id) {
        this.updateType = 0;
        this.form.id = id;
        this.refuseDialogVisible = true;
      },
      //批量拒绝时显示拒绝窗口
      refuseBatchShow() {
        if (!this.selectedIds.length > 0) {
          this.$message({
            message: '请至少选择一条记录！',
            type: 'warning'
          });
          return;
        }
        this.$refs['form'].resetFields();
        this.updateType = 1;
        this.refuseDialogVisible = true;
      },
      //拒绝
      refuse() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.updateType == 0) {
              this.refuseOne();
            } else {
              this.refuseBatch();
            }
          }
        });
      },
      //单条拒绝
      refuseOne() {
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        refuseMySelfBrandAudit(this.form).then(response => {
          this.isProcess = false;
          this.refuseDialogVisible = false;
          if (response == 1) {
            this.$message({
              type: 'success',
              message: '审核拒绝成功!'
            });
            this.getList();
          } else {
            this.$message({
              type: 'erroe',
              message: '审核拒绝失败!'
            })
          }
        });
      },
      //批量通过
      passDialog() {
        if (!this.selectedIds.length > 0) {
          this.$message({
            message: '请至少选择一条记录！',
            type: 'warning'
          });
          return;
        }
        this.$confirm('确定要通过该品牌审核吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.isProcess) {
            return;
          }
          this.isProcess = true;
          let param = {
            ids: this.selectedIds.toString()
          };
          batchPassMySelfBrandAudit(param).then(response => {
            this.isProcess = false;
            if (response > 0) {
              this.$message({
                type: 'success',
                message: '审核成功!'
              });
              this.getList();
            } else {
              this.$message({
                type: 'erroe',
                message: '审核失败!'
              })
            }
          });
        })
      },
      // 过滤查询
      handleQuery() {
        this.listQuery.pageNum = 0;
        this.getList();
      },
      //批量拒绝
      refuseBatch() {
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        let param = {
          ids: this.selectedIds.toString(),
          reason: this.form.reason
        };
        batchRefuseMySelfBrandAudit(param).then(response => {
          this.isProcess = false;
          this.refuseDialogVisible = false;
          if (response > 0) {
            this.$message({
              type: 'success',
              message: '批量拒绝成功!'
            });
            this.getList();
          } else {
            this.$message({
              type: 'erroe',
              message: '批量拒绝失败!'
            })
          }
        });
      },
    }
  }
</script>
