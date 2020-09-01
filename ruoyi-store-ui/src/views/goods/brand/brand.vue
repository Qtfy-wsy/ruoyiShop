<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="品牌名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入品牌名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['goods:brand:add']"
        >申请品牌</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['goods:brand:remove']"
        >删除</el-button>
      </el-col>

    </el-row>

    <el-table v-loading="loading" :data="brandList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="品牌名称" align="center" prop="name" />

        <el-table-column label="图片" width="80">
                  <template slot-scope="scope"><img :src="scope.row.url" width="50" height="50"></template>
                </el-table-column>


      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">

          <el-button
            size="mini"
            type="text" icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['goods:brand:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改品牌对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

       <el-form-item label="选择品牌" prop="brandId">
              <el-select v-model="form.brandId" placeholder="请选择品牌" clearable size="small">
                <el-option
                  v-for="dict in allBrandList"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBrand, getBrand, delBrand, addBrand, updateBrand, exportBrand,getAllBrandList } from "@/api/goods/brand";
  import {getToken} from '@/utils/auth'
export default {
  name: "Brand",
  data() {
    return {
      // 遮罩层
      loading: true,
      headers: { // 设置上传的请求头部
                Authorization: 'Bearer ' + getToken()
              },
       uploadApi: process.env.VUE_APP_BASE_API+'/aliyun/oss/uploadToAliOss',// 上传文件路由
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 品牌表格数据
      brandList: [],
       allBrandList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态  0 申请中  1通过 2 拒绝字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        name: undefined,
        nickName: undefined,
        brandId: 0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "品牌名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("audit_thress").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询品牌列表 */
    getList() {
      this.loading = true;
      listBrand(this.queryParams).then(response => {
        this.brandList = response.data;
        this.total = response.recordsTotal;
        this.loading = false;
      });
    },
    // 状态  0 申请中  1通过 2 拒绝字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        nickName: undefined,
        url: undefined,
        certificatUrl: undefined,
        storeId: undefined,
        status: "0",
        delFlag: undefined,
        reason: undefined,
        createName: undefined,
        modifyName: undefined,
        delName: undefined,
        createTime: undefined,
        modifyTime: undefined,
        delTime: undefined
      };
      this.resetForm("form");
    },
     // 上传移动端抢购广告图回调
          uploadMobilePanicPicSuccess(res) {
            this.form.url = res;
          },
          // 上传PC预售广告图回调
          uploadPcPreSalePicSuccess(res) {
            this.form.certificatUrl = res;
          },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 0;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();

      getAllBrandList().then(response => {
              this.allBrandList = response;
              this.open = true;
              this.title = "申请品牌";
            });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBrand(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改品牌";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateBrand(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addBrand(this.form).then(response => {
              if (response === 1) {
                this.msgSuccess("申请成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除品牌编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBrand(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有品牌数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportBrand(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
