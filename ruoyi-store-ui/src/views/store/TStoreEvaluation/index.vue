<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="门店评分" prop="score">
        <el-select v-model="queryParams.score" placeholder="请选择门店评分" clearable size="small">
          <el-option
            v-for="dict in scoreOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单code" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入订单code"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺id" prop="storeId">
        <el-input
          v-model="queryParams.storeId"
          placeholder="请输入店铺id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员id" prop="customerId">
        <el-input
          v-model="queryParams.customerId"
          placeholder="请输入会员id"
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
          v-hasPermi="['store:TStoreEvaluation:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['store:TStoreEvaluation:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['store:TStoreEvaluation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['store:TStoreEvaluation:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="TStoreEvaluationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="门店评分" align="center" prop="score" :formatter="scoreFormat" />
      <el-table-column label="评价" align="center" prop="desc" />
      <el-table-column label="订单code" align="center" prop="orderCode" />
      <el-table-column label="店铺id" align="center" prop="storeId" />
      <el-table-column label="会员id" align="center" prop="customerId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['store:TStoreEvaluation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['store:TStoreEvaluation:remove']"
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

    <!-- 添加或修改门店评价对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="门店评分">
          <el-select v-model="form.score" placeholder="请选择门店评分">
            <el-option
              v-for="dict in scoreOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评价" prop="desc">
          <el-input v-model="form.desc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="订单code" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入订单code" />
        </el-form-item>
        <el-form-item label="店铺id" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入店铺id" />
        </el-form-item>
        <el-form-item label="会员id" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入会员id" />
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
import { listTStoreEvaluation, getTStoreEvaluation, delTStoreEvaluation, addTStoreEvaluation, updateTStoreEvaluation, exportTStoreEvaluation } from "@/api/store/TStoreEvaluation";

export default {
  name: "TStoreEvaluation",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 门店评价表格数据
      TStoreEvaluationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 门店评分字典
      scoreOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        score: undefined,
        desc: undefined,
        orderCode: undefined,
        storeId: undefined,
        customerId: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("descScop").then(response => {
      this.scoreOptions = response.data;
    });
  },
  methods: {
    /** 查询门店评价列表 */
    getList() {
      this.loading = true;
      listTStoreEvaluation(this.queryParams).then(response => {
        this.TStoreEvaluationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 门店评分字典翻译
    scoreFormat(row, column) {
      return this.selectDictLabel(this.scoreOptions, row.score);
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
        score: undefined,
        desc: undefined,
        orderCode: undefined,
        storeId: undefined,
        customerId: undefined,
        createTime: undefined
      };
      this.resetForm("form");
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
      this.open = true;
      this.title = "添加门店评价";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTStoreEvaluation(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门店评价";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTStoreEvaluation(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTStoreEvaluation(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
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
      this.$confirm('是否确认删除门店评价编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTStoreEvaluation(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门店评价数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTStoreEvaluation(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
