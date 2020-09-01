<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="订单id  对应oms_order表中的order_code" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单id  对应oms_order表中的order_code"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单品id 对应pms_sku 表中的id" prop="skuId">
        <el-input
          v-model="queryParams.skuId"
          placeholder="请输入单品id 对应pms_sku 表中的id"
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
      <el-form-item label="单品的价格(详情页的价格)" prop="skuPrice">
        <el-input
          v-model="queryParams.skuPrice"
          placeholder="请输入单品的价格(详情页的价格)"
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
      <el-form-item label="单品的编号" prop="skuNo">
        <el-input
          v-model="queryParams.skuNo"
          placeholder="请输入单品的编号"
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
      <el-form-item label="单品的分拥比例 0 表示不分佣金" prop="commissionRate">
        <el-input
          v-model="queryParams.commissionRate"
          placeholder="请输入单品的分拥比例 0 表示不分佣金"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单品的分拥比例 0 表示不分佣金" prop="sCommissionRate">
        <el-input
          v-model="queryParams.sCommissionRate"
          placeholder="请输入单品的分拥比例 0 表示不分佣金"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类扣率" prop="cateRate">
        <el-input
          v-model="queryParams.cateRate"
          placeholder="请输入分类扣率"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单品的原价格" prop="oldPrice">
        <el-input
          v-model="queryParams.oldPrice"
          placeholder="请输入单品的原价格"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供货价格" prop="supplyPrice">
        <el-input
          v-model="queryParams.supplyPrice"
          placeholder="请输入供货价格"
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
          v-hasPermi="['order:OmsOrderSku:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['order:OmsOrderSku:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:OmsOrderSku:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['order:OmsOrderSku:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="OmsOrderSkuList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="订单id  对应oms_order表中的order_code" align="center" prop="orderId" />
      <el-table-column label="单品id 对应pms_sku 表中的id" align="center" prop="skuId" />
      <el-table-column label="购买的数量" align="center" prop="num" />
      <el-table-column label="单品的最终价格" align="center" prop="price" />
      <el-table-column label="单品的价格(详情页的价格)" align="center" prop="skuPrice" />
      <el-table-column label="单品的名称" align="center" prop="skuName" />
      <el-table-column label="单品的编号" align="center" prop="skuNo" />
      <el-table-column label="单品的图片" align="center" prop="skuImage" />
      <el-table-column label="单品的规格" align="center" prop="skuSpecs" />
      <el-table-column label="使用各个优惠减去的价格" align="center" prop="priceDetail" />
      <el-table-column label="单品的分拥比例 0 表示不分佣金" align="center" prop="commissionRate" />
      <el-table-column label="单品的分拥比例 0 表示不分佣金" align="center" prop="sCommissionRate" />
      <el-table-column label="分类扣率" align="center" prop="cateRate" />
      <el-table-column label="单品的原价格" align="center" prop="oldPrice" />
      <el-table-column label="供货价格" align="center" prop="supplyPrice" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['order:OmsOrderSku:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:OmsOrderSku:remove']"
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

    <!-- 添加或修改订单单品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单id  对应oms_order表中的order_code" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单id  对应oms_order表中的order_code" />
        </el-form-item>
        <el-form-item label="单品id 对应pms_sku 表中的id" prop="skuId">
          <el-input v-model="form.skuId" placeholder="请输入单品id 对应pms_sku 表中的id" />
        </el-form-item>
        <el-form-item label="购买的数量" prop="num">
          <el-input v-model="form.num" placeholder="请输入购买的数量" />
        </el-form-item>
        <el-form-item label="单品的最终价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入单品的最终价格" />
        </el-form-item>
        <el-form-item label="单品的价格(详情页的价格)" prop="skuPrice">
          <el-input v-model="form.skuPrice" placeholder="请输入单品的价格(详情页的价格)" />
        </el-form-item>
        <el-form-item label="单品的名称" prop="skuName">
          <el-input v-model="form.skuName" placeholder="请输入单品的名称" />
        </el-form-item>
        <el-form-item label="单品的编号" prop="skuNo">
          <el-input v-model="form.skuNo" placeholder="请输入单品的编号" />
        </el-form-item>
        <el-form-item label="单品的图片" prop="skuImage">
          <el-input v-model="form.skuImage" placeholder="请输入单品的图片" />
        </el-form-item>
        <el-form-item label="单品的规格" prop="skuSpecs">
          <el-input v-model="form.skuSpecs" placeholder="请输入单品的规格" />
        </el-form-item>
        <el-form-item label="使用各个优惠减去的价格" prop="priceDetail">
          <el-input v-model="form.priceDetail" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="单品的分拥比例 0 表示不分佣金" prop="commissionRate">
          <el-input v-model="form.commissionRate" placeholder="请输入单品的分拥比例 0 表示不分佣金" />
        </el-form-item>
        <el-form-item label="单品的分拥比例 0 表示不分佣金" prop="sCommissionRate">
          <el-input v-model="form.sCommissionRate" placeholder="请输入单品的分拥比例 0 表示不分佣金" />
        </el-form-item>
        <el-form-item label="分类扣率" prop="cateRate">
          <el-input v-model="form.cateRate" placeholder="请输入分类扣率" />
        </el-form-item>
        <el-form-item label="单品的原价格" prop="oldPrice">
          <el-input v-model="form.oldPrice" placeholder="请输入单品的原价格" />
        </el-form-item>
        <el-form-item label="供货价格" prop="supplyPrice">
          <el-input v-model="form.supplyPrice" placeholder="请输入供货价格" />
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
import { listOmsOrderSku, getOmsOrderSku, delOmsOrderSku, addOmsOrderSku, updateOmsOrderSku, exportOmsOrderSku } from "@/api/order/OmsOrderSku";

export default {
  name: "OmsOrderSku",
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
      // 订单单品表格数据
      OmsOrderSkuList: [],
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
        priceDetail: undefined,
        commissionRate: undefined,
        sCommissionRate: undefined,
        cateRate: undefined,
        oldPrice: undefined,
        supplyPrice: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单id  对应oms_order表中的order_code不能为空", trigger: "blur" }
        ],
        skuId: [
          { required: true, message: "单品id 对应pms_sku 表中的id不能为空", trigger: "blur" }
        ],
        num: [
          { required: true, message: "购买的数量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单单品列表 */
    getList() {
      this.loading = true;
      listOmsOrderSku(this.queryParams).then(response => {
        this.OmsOrderSkuList = response.rows;
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
        priceDetail: undefined,
        commissionRate: undefined,
        sCommissionRate: undefined,
        cateRate: undefined,
        oldPrice: undefined,
        supplyPrice: undefined
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
      this.title = "添加订单单品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOmsOrderSku(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单单品";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateOmsOrderSku(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addOmsOrderSku(this.form).then(response => {
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
      this.$confirm('是否确认删除订单单品编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOmsOrderSku(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有订单单品数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOmsOrderSku(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
