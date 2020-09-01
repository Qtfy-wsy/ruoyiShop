<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="订单id" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单id"
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
      <el-form-item label="购买的数量" prop="num">
        <el-input
          v-model="queryParams.num"
          placeholder="请输入购买的数量"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单品的最终价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入单品的最终价格"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单品的价格" prop="skuPrice">
        <el-input
          v-model="queryParams.skuPrice"
          placeholder="请输入单品的价格"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单品的名称" prop="skuName">
        <el-input
          v-model="queryParams.skuName"
          placeholder="请输入单品的名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单品的id" prop="skuNo">
        <el-input
          v-model="queryParams.skuNo"
          placeholder="请输入单品的id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单品的图片" prop="skuImage">
        <el-input
          v-model="queryParams.skuImage"
          placeholder="请输入单品的图片"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单品的规格" prop="skuSpecs">
        <el-input
          v-model="queryParams.skuSpecs"
          placeholder="请输入单品的规格"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="红包减去的价格" prop="redEnvelopePrice">
        <el-input
          v-model="queryParams.redEnvelopePrice"
          placeholder="请输入红包减去的价格"
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
          v-hasPermi="['store:TStoreOrderSku:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['store:TStoreOrderSku:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['store:TStoreOrderSku:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['store:TStoreOrderSku:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="TStoreOrderSkuList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="订单id" align="center" prop="orderId" />
      <el-table-column label="单品id" align="center" prop="skuId" />
      <el-table-column label="购买的数量" align="center" prop="num" />
      <el-table-column label="单品的最终价格" align="center" prop="price" />
      <el-table-column label="单品的价格" align="center" prop="skuPrice" />
      <el-table-column label="单品的名称" align="center" prop="skuName" />
      <el-table-column label="单品的id" align="center" prop="skuNo" />
      <el-table-column label="单品的图片" align="center" prop="skuImage" />
      <el-table-column label="单品的规格" align="center" prop="skuSpecs" />
      <el-table-column label="红包减去的价格" align="center" prop="redEnvelopePrice" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['store:TStoreOrderSku:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['store:TStoreOrderSku:remove']"
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

    <!-- 添加或修改门店订单单品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单id" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单id" />
        </el-form-item>
        <el-form-item label="单品id" prop="skuId">
          <el-input v-model="form.skuId" placeholder="请输入单品id" />
        </el-form-item>
        <el-form-item label="购买的数量" prop="num">
          <el-input v-model="form.num" placeholder="请输入购买的数量" />
        </el-form-item>
        <el-form-item label="单品的最终价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入单品的最终价格" />
        </el-form-item>
        <el-form-item label="单品的价格" prop="skuPrice">
          <el-input v-model="form.skuPrice" placeholder="请输入单品的价格" />
        </el-form-item>
        <el-form-item label="单品的名称" prop="skuName">
          <el-input v-model="form.skuName" placeholder="请输入单品的名称" />
        </el-form-item>
        <el-form-item label="单品的id" prop="skuNo">
          <el-input v-model="form.skuNo" placeholder="请输入单品的id" />
        </el-form-item>
        <el-form-item label="单品的图片" prop="skuImage">
          <el-input v-model="form.skuImage" placeholder="请输入单品的图片" />
        </el-form-item>
        <el-form-item label="单品的规格" prop="skuSpecs">
          <el-input v-model="form.skuSpecs" placeholder="请输入单品的规格" />
        </el-form-item>
        <el-form-item label="红包减去的价格" prop="redEnvelopePrice">
          <el-input v-model="form.redEnvelopePrice" placeholder="请输入红包减去的价格" />
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
import { listTStoreOrderSku, getTStoreOrderSku, delTStoreOrderSku, addTStoreOrderSku, updateTStoreOrderSku, exportTStoreOrderSku } from "@/api/store/TStoreOrderSku";

export default {
  name: "TStoreOrderSku",
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
      // 门店订单单品信息表格数据
      TStoreOrderSkuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        orderId: undefined,
        skuId: undefined,
        num: undefined,
        price: undefined,
        skuPrice: undefined,
        skuName: undefined,
        skuNo: undefined,
        skuImage: undefined,
        skuSpecs: undefined,
        redEnvelopePrice: undefined
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
  },
  methods: {
    /** 查询门店订单单品信息列表 */
    getList() {
      this.loading = true;
      listTStoreOrderSku(this.queryParams).then(response => {
        this.TStoreOrderSkuList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        orderId: undefined,
        skuId: undefined,
        num: undefined,
        price: undefined,
        skuPrice: undefined,
        skuName: undefined,
        skuNo: undefined,
        skuImage: undefined,
        skuSpecs: undefined,
        redEnvelopePrice: undefined
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
      this.title = "添加门店订单单品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTStoreOrderSku(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门店订单单品信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTStoreOrderSku(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTStoreOrderSku(this.form).then(response => {
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
      this.$confirm('是否确认删除门店订单单品信息编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTStoreOrderSku(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门店订单单品信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTStoreOrderSku(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
