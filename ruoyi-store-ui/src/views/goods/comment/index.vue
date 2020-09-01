<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="会员id" prop="customerId">
        <el-input
          v-model="queryParams.customerId"
          placeholder="请输入会员id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单品id" prop="skuId">
        <el-input
          v-model="queryParams.skuId"
          placeholder="请输入单品id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品满意度评分1到5分" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入商品满意度评分1到5分"
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
          v-hasPermi="['goods:comment:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['goods:comment:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['goods:comment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['goods:comment:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="commentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="会员id" align="center" prop="customerId" />
      <el-table-column label="单品id" align="center" prop="skuId" />
      <el-table-column label="店铺id" align="center" prop="storeId" />
      <el-table-column label="订单号" align="center" prop="orderId" />
      <el-table-column label="是否匿名  0 否 1是  默认0 " align="center" prop="isAnonymous" :formatter="isAnonymousFormat" />
      <el-table-column label="评论" align="center" prop="comment" />
      <el-table-column label="商品满意度评分1到5分" align="center" prop="score" />
      <el-table-column label="是否有图片  0 没有 1 有 默认0 " align="center" prop="hasPic" />
      <el-table-column label="是否可见 0 可见 1 不可见 默认0 " align="center" prop="isShow" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['goods:comment:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['goods:comment:remove']"
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

    <!-- 添加或修改单品评论对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会员id" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入会员id" />
        </el-form-item>
        <el-form-item label="单品id" prop="skuId">
          <el-input v-model="form.skuId" placeholder="请输入单品id" />
        </el-form-item>
        <el-form-item label="店铺id" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入店铺id" />
        </el-form-item>
        <el-form-item label="订单号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="是否匿名  0 否 1是  默认0 " prop="isAnonymous">
          <el-input v-model="form.isAnonymous" placeholder="请输入是否匿名  0 否 1是  默认0 " />
        </el-form-item>
        <el-form-item label="评论" prop="comment">
          <el-input v-model="form.comment" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="商品满意度评分1到5分" prop="score">
          <el-input v-model="form.score" placeholder="请输入商品满意度评分1到5分" />
        </el-form-item>
        <el-form-item label="是否有图片  0 没有 1 有 默认0 " prop="hasPic">
          <el-input v-model="form.hasPic" placeholder="请输入是否有图片  0 没有 1 有 默认0 " />
        </el-form-item>
        <el-form-item label="是否可见 0 可见 1 不可见 默认0 " prop="isShow">
          <el-input v-model="form.isShow" placeholder="请输入是否可见 0 可见 1 不可见 默认0 " />
        </el-form-item>
        <el-form-item label="是否删除 0 未删除1 删除 默认0 " prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入是否删除 0 未删除1 删除 默认0 " />
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
import { listComment, getComment, delComment, addComment, updateComment, exportComment } from "@/api/goods/comment";

export default {
  name: "Comment",
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
      // 单品评论表格数据
      commentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否匿名  0 否 1是  默认0 字典
      isAnonymousOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        customerId: undefined,
        skuId: undefined,
        score: undefined,
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
    this.getDicts("ye_no01").then(response => {
      this.isAnonymousOptions = response.data;
    });
  },
  methods: {
    /** 查询单品评论列表 */
    getList() {
      this.loading = true;
      listComment(this.queryParams).then(response => {
        this.commentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否匿名  0 否 1是  默认0 字典翻译
    isAnonymousFormat(row, column) {
      return this.selectDictLabel(this.isAnonymousOptions, row.isAnonymous);
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
        customerId: undefined,
        skuId: undefined,
        storeId: undefined,
        orderId: undefined,
        isAnonymous: undefined,
        comment: undefined,
        score: undefined,
        hasPic: undefined,
        createTime: undefined,
        isShow: undefined,
        delFlag: undefined
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
      this.title = "添加单品评论";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getComment(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改单品评论";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateComment(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addComment(this.form).then(response => {
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
      this.$confirm('是否确认删除单品评论编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delComment(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有单品评论数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportComment(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
