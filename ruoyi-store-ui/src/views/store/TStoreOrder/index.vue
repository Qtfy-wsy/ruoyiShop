<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="订单编号" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入订单编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单" prop="masterOrderCode">
        <el-input
          v-model="queryParams.masterOrderCode"
          placeholder="请输入主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单"
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
      <el-form-item label="订单的原始价格" prop="originalPrice">
        <el-input
          v-model="queryParams.originalPrice"
          placeholder="请输入订单的原始价格"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单的最终价格(订单的最终成交价格)" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入订单的最终价格(订单的最终成交价格)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单状态 1 待付款" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择订单状态 1 待付款" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
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
      <el-form-item label="评价状态  0 未评价 1 已评价  默认为0 未评价" prop="evaluationStatus">
        <el-select v-model="queryParams.evaluationStatus" placeholder="请选择评价状态  0 未评价 1 已评价  默认为0 未评价" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="使用红包减去的金额" prop="redEnvelopePrice">
        <el-input
          v-model="queryParams.redEnvelopePrice"
          placeholder="请输入使用红包减去的金额"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="红包的卷码" prop="redEnvelopeCode">
        <el-input
          v-model="queryParams.redEnvelopeCode"
          placeholder="请输入红包的卷码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="核销码" prop="writeOffCode">
        <el-input
          v-model="queryParams.writeOffCode"
          placeholder="请输入核销码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单来源 1pc  2 h5   3 app 4 代客下单" prop="source">
        <el-input
          v-model="queryParams.source"
          placeholder="请输入订单来源 1pc  2 h5   3 app 4 代客下单"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付时间" prop="payTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.payTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择支付时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="取消时间" prop="cancelTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.cancelTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择取消时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="订单取消原因 1 现在不想买 2商品价格较贵 3价格波动 4商品缺货 5 重复下单 6发票信息有误 7 其他原因 8 无法备齐货物 9不是有效订单 10 买家主动要求" prop="cancelReason">
        <el-input
          v-model="queryParams.cancelReason"
          placeholder="请输入订单取消原因 1 现在不想买 2商品价格较贵 3价格波动 4商品缺货 5 重复下单 6发票信息有误 7 其他原因 8 无法备齐货物 9不是有效订单 10 买家主动要求"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="取货时间" prop="pickUpTime">
        <el-input
          v-model="queryParams.pickUpTime"
          placeholder="请输入取货时间"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际取货时间" prop="realPickUpTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.realPickUpTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择实际取货时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="支付类型" prop="payType">
        <el-select v-model="queryParams.payType" placeholder="请选择支付类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否是预存款支付  0否1 是 默认0 " prop="predepositPay">
        <el-input
          v-model="queryParams.predepositPay"
          placeholder="请输入是否是预存款支付  0否1 是 默认0 "
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
          v-hasPermi="['store:TStoreOrder:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['store:TStoreOrder:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['store:TStoreOrder:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['store:TStoreOrder:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="TStoreOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="订单编号" align="center" prop="orderCode" />
      <el-table-column label="主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单" align="center" prop="masterOrderCode" />
      <el-table-column label="会员id" align="center" prop="customerId" />
      <el-table-column label="订单的原始价格" align="center" prop="originalPrice" />
      <el-table-column label="订单的最终价格(订单的最终成交价格)" align="center" prop="price" />
      <el-table-column label="订单状态 1 待付款" align="center" prop="status" />
      <el-table-column label="店铺id" align="center" prop="storeId" />
      <el-table-column label="评价状态  0 未评价 1 已评价  默认为0 未评价" align="center" prop="evaluationStatus" />
      <el-table-column label="使用红包减去的金额" align="center" prop="redEnvelopePrice" />
      <el-table-column label="红包的卷码" align="center" prop="redEnvelopeCode" />
      <el-table-column label="核销码" align="center" prop="writeOffCode" />
      <el-table-column label="订单来源 1pc  2 h5   3 app 4 代客下单" align="center" prop="source" />
      <el-table-column label="支付时间" align="center" prop="payTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="取消时间" align="center" prop="cancelTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.cancelTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单取消原因 1 现在不想买 2商品价格较贵 3价格波动 4商品缺货 5 重复下单 6发票信息有误 7 其他原因 8 无法备齐货物 9不是有效订单 10 买家主动要求" align="center" prop="cancelReason" />
      <el-table-column label="取货时间" align="center" prop="pickUpTime" />
      <el-table-column label="实际取货时间" align="center" prop="realPickUpTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.realPickUpTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支付类型" align="center" prop="payType" />
      <el-table-column label="是否是预存款支付  0否1 是 默认0 " align="center" prop="predepositPay" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['store:TStoreOrder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['store:TStoreOrder:remove']"
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

    <!-- 添加或修改门店订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单" prop="masterOrderCode">
          <el-input v-model="form.masterOrderCode" placeholder="请输入主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单" />
        </el-form-item>
        <el-form-item label="会员id" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入会员id" />
        </el-form-item>
        <el-form-item label="订单的原始价格" prop="originalPrice">
          <el-input v-model="form.originalPrice" placeholder="请输入订单的原始价格" />
        </el-form-item>
        <el-form-item label="订单的最终价格(订单的最终成交价格)" prop="price">
          <el-input v-model="form.price" placeholder="请输入订单的最终价格(订单的最终成交价格)" />
        </el-form-item>
        <el-form-item label="订单状态 1 待付款">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="店铺id" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入店铺id" />
        </el-form-item>
        <el-form-item label="评价状态  0 未评价 1 已评价  默认为0 未评价">
          <el-radio-group v-model="form.evaluationStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="使用红包减去的金额" prop="redEnvelopePrice">
          <el-input v-model="form.redEnvelopePrice" placeholder="请输入使用红包减去的金额" />
        </el-form-item>
        <el-form-item label="红包的卷码" prop="redEnvelopeCode">
          <el-input v-model="form.redEnvelopeCode" placeholder="请输入红包的卷码" />
        </el-form-item>
        <el-form-item label="核销码" prop="writeOffCode">
          <el-input v-model="form.writeOffCode" placeholder="请输入核销码" />
        </el-form-item>
        <el-form-item label="订单来源 1pc  2 h5   3 app 4 代客下单" prop="source">
          <el-input v-model="form.source" placeholder="请输入订单来源 1pc  2 h5   3 app 4 代客下单" />
        </el-form-item>
        <el-form-item label="支付时间" prop="payTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.payTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择支付时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="取消时间" prop="cancelTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.cancelTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择取消时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单取消原因 1 现在不想买 2商品价格较贵 3价格波动 4商品缺货 5 重复下单 6发票信息有误 7 其他原因 8 无法备齐货物 9不是有效订单 10 买家主动要求" prop="cancelReason">
          <el-input v-model="form.cancelReason" placeholder="请输入订单取消原因 1 现在不想买 2商品价格较贵 3价格波动 4商品缺货 5 重复下单 6发票信息有误 7 其他原因 8 无法备齐货物 9不是有效订单 10 买家主动要求" />
        </el-form-item>
        <el-form-item label="取货时间" prop="pickUpTime">
          <el-input v-model="form.pickUpTime" placeholder="请输入取货时间" />
        </el-form-item>
        <el-form-item label="实际取货时间" prop="realPickUpTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.realPickUpTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择实际取货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="支付类型">
          <el-select v-model="form.payType" placeholder="请选择支付类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否是预存款支付  0否1 是 默认0 " prop="predepositPay">
          <el-input v-model="form.predepositPay" placeholder="请输入是否是预存款支付  0否1 是 默认0 " />
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
import { listTStoreOrder, getTStoreOrder, delTStoreOrder, addTStoreOrder, updateTStoreOrder, exportTStoreOrder } from "@/api/store/TStoreOrder";

export default {
  name: "TStoreOrder",
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
      // 门店订单表格数据
      TStoreOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        orderCode: undefined,
        masterOrderCode: undefined,
        customerId: undefined,
        originalPrice: undefined,
        price: undefined,
        status: undefined,
        storeId: undefined,
        evaluationStatus: undefined,
        redEnvelopePrice: undefined,
        redEnvelopeCode: undefined,
        writeOffCode: undefined,
        source: undefined,
        payTime: undefined,
        cancelTime: undefined,
        cancelReason: undefined,
        pickUpTime: undefined,
        realPickUpTime: undefined,
        payType: undefined,
        predepositPay: undefined
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
    /** 查询门店订单列表 */
    getList() {
      this.loading = true;
      listTStoreOrder(this.queryParams).then(response => {
        this.TStoreOrderList = response.rows;
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
        orderCode: undefined,
        masterOrderCode: undefined,
        customerId: undefined,
        originalPrice: undefined,
        price: undefined,
        status: "0",
        storeId: undefined,
        evaluationStatus: "0",
        redEnvelopePrice: undefined,
        redEnvelopeCode: undefined,
        writeOffCode: undefined,
        source: undefined,
        createTime: undefined,
        payTime: undefined,
        cancelTime: undefined,
        cancelReason: undefined,
        pickUpTime: undefined,
        realPickUpTime: undefined,
        payType: undefined,
        predepositPay: undefined
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
      this.title = "添加门店订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTStoreOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门店订单";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTStoreOrder(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTStoreOrder(this.form).then(response => {
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
      this.$confirm('是否确认删除门店订单编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTStoreOrder(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门店订单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTStoreOrder(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
