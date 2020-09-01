<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="退单号" prop="backCode">
        <el-input
          v-model="queryParams.backCode"
          placeholder="请输入退单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单号" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入订单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择退款／退货状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="物流公司名称" prop="logisCompanyName">
        <el-input
          v-model="queryParams.logisCompanyName"
          placeholder="请输入物流公司名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物流单号" prop="waybillCode">
        <el-input
          v-model="queryParams.waybillCode"
          placeholder="请输入物流单号"
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
          v-hasPermi="['order:OmsBackOrder:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['order:OmsBackOrder:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:OmsBackOrder:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['order:OmsBackOrder:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="OmsBackOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="退单号" align="center" prop="backCode" />

      <el-table-column label="订单号" align="center" prop="orderCode" />
      <el-table-column label="店铺id 平台的为0 " align="center" prop="storeId" />
      <el-table-column label="用户id" align="center" prop="customerId" />

      <el-table-column label="类型" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="退款／退货原因" align="center" prop="reason" />
      <el-table-column label="问题说明" align="center" prop="desc" />
      <el-table-column label="申请凭据" align="center" prop="credential" />
      <el-table-column label="返回方式" align="center" prop="backType" />
      <el-table-column label="退款／退货金额" align="center" prop="backPrice" />
      <el-table-column label="退货时候实际退款金额" align="center" prop="realBackPrice" />

      <el-table-column label="退款／退货状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="是否预存款支付" align="center" prop="predepositPay" />
      <el-table-column label="物流公司名称" align="center" prop="logisCompanyName" />
      <el-table-column label="物流单号" align="center" prop="waybillCode" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['order:OmsBackOrder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:OmsBackOrder:remove']"
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

    <!-- 添加或修改退单退款对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="退单号" prop="backCode">
          <el-input v-model="form.backCode" placeholder="请输入退单号" />
        </el-form-item>
        <el-form-item label="订单id 对应订单表 oms_order中的id" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单id " />
        </el-form-item>
        <el-form-item label="订单号 对应oms_order 表中的order_code" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入订单号 对应oms_order 表中的order_code" />
        </el-form-item>
        <el-form-item label="店铺id 平台的为0 " prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入店铺id 平台的为0 " />
        </el-form-item>
        <el-form-item label="用户id" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="退货的单品ID多个用 " prop="skuidNums">
          <el-input v-model="form.skuidNums" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="1 退款 2 退货">
          <el-select v-model="form.type" placeholder="请选择1 退款 2 退货">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="退款／退货原因" prop="reason">
          <el-input v-model="form.reason" placeholder="请输入退款／退货原因" />
        </el-form-item>
        <el-form-item label="问题说明" prop="desc">
          <el-input v-model="form.desc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="申请凭据 0 没有任何凭据 1 有发票 2有质检报告" prop="credential">
          <el-input v-model="form.credential" placeholder="请输入申请凭据 0 没有任何凭据 1 有发票 2有质检报告" />
        </el-form-item>
        <el-form-item label="返回方式 1 快递返回 目前只有快递返回 ">
          <el-select v-model="form.backType" placeholder="请选择返回方式 1 快递返回 目前只有快递返回 ">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="退款／退货金额" prop="backPrice">
          <el-input v-model="form.backPrice" placeholder="请输入退款／退货金额" />
        </el-form-item>
        <el-form-item label="退货时候实际退款金额" prop="realBackPrice">
          <el-input v-model="form.realBackPrice" placeholder="请输入退货时候实际退款金额" />
        </el-form-item>
        <el-form-item label="上传的退款凭证或者质检发票 多个图片 用, 隔开" prop="pics">
          <el-input v-model="form.pics" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="退款／退货状态">
          <el-select v-model="form.status" placeholder="请选择退款／退货状态">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否预存款支付" prop="predepositPay">
          <el-input v-model="form.predepositPay" placeholder="请输入是否预存款支付  0 否 1 是  默认0" />
        </el-form-item>
        <el-form-item label="物流公司名称" prop="logisCompanyName">
          <el-input v-model="form.logisCompanyName" placeholder="请输入物流公司名称" />
        </el-form-item>
        <el-form-item label="物流单号" prop="waybillCode">
          <el-input v-model="form.waybillCode" placeholder="请输入物流单号" />
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
import { listOmsBackOrder, getOmsBackOrder, delOmsBackOrder, addOmsBackOrder, updateOmsBackOrder, exportOmsBackOrder } from "@/api/order/OmsBackOrder";

export default {
  name: "OmsBackOrder",
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
      // 退单退款表格数据
      OmsBackOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 1 退款 2 退货字典
      typeOptions: [],
      // 退款／退货状态1:退款申请 字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        backCode: undefined,
        orderCode: undefined,
        type: undefined,
        status: undefined,
        logisCompanyName: undefined,
        waybillCode: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        backCode: [
          { required: true, message: "退单号不能为空", trigger: "blur" }
        ],
        orderId: [
          { required: true, message: "订单id 对应订单表 oms_order中的id不能为空", trigger: "blur" }
        ],
        orderCode: [
          { required: true, message: "订单号 对应oms_order 表中的order_code不能为空", trigger: "blur" }
        ],
        storeId: [
          { required: true, message: "店铺id 平台的为0 不能为空", trigger: "blur" }
        ],
        customerId: [
          { required: true, message: "用户id不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "1 退款 2 退货不能为空", trigger: "blur" }
        ],

      }
    };
  },
  created() {
    this.getList();
    this.getDicts("after_type").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("afterSaleStatus").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询退单退款列表 */
    getList() {
      this.loading = true;
      listOmsBackOrder(this.queryParams).then(response => {
        this.OmsBackOrderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 1 退款 2 退货字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 退款／退货状态1:退款申请 字典翻译
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
        backCode: undefined,
        orderId: undefined,
        orderCode: undefined,
        storeId: undefined,
        customerId: undefined,
        skuidNums: undefined,
        type: undefined,
        reason: undefined,
        desc: undefined,
        credential: undefined,
        backType: undefined,
        backPrice: undefined,
        realBackPrice: undefined,
        pics: undefined,
        status: undefined,
        predepositPay: undefined,
        logisCompanyName: undefined,
        waybillCode: undefined,
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
      this.title = "添加退单退款";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOmsBackOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改退单退款";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateOmsBackOrder(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addOmsBackOrder(this.form).then(response => {
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
      this.$confirm('是否确认删除退单退款编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOmsBackOrder(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有退单退款数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOmsBackOrder(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
