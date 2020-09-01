<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="收货人姓名" prop="receiptName">
        <el-input
          v-model="queryParams.receiptName"
          placeholder="请输入收货人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收货人的手机号码" prop="receiptMobile">
        <el-input
          v-model="queryParams.receiptMobile"
          placeholder="请输入收货人的手机号码"
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
          v-hasPermi="['order:OmsOrderAttr:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['order:OmsOrderAttr:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:OmsOrderAttr:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['order:OmsOrderAttr:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="OmsOrderAttrList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="订单id  对应oms_order 表中的order_code" align="center" prop="orderId" />
      <el-table-column label="收货人姓名" align="center" prop="receiptName" />
      <el-table-column label="收货人的地址 " align="center" prop="receiptAddress" />
      <el-table-column label="收货人的详细地址" align="center" prop="receiptDetailAddress" />
      <el-table-column label="收货人的手机号码" align="center" prop="receiptMobile" />
      <el-table-column label="收货人的固定电话" align="center" prop="receiptPhone" />
      <el-table-column label="收货人的邮编" align="center" prop="receiptZipCode" />
      <el-table-column label="发票类型  0 不需要发票 1增值税普票 2增值税专票 默认0" align="center" prop="invoiceType" />
      <el-table-column label="发票抬头" align="center" prop="invoiceTitle" />
      <el-table-column label="发票内容
1:非图书商品
2:明细
3:耗材
4:办公用品" align="center" prop="invoiceContent" />
      <el-table-column label="税号" align="center" prop="invoiceTaxid" />
      <el-table-column label="订单备注" align="center" prop="remark" />
      <el-table-column label="订单的赠品信息格式为
[
    {
        "num": 1,
        "skuId": "15096899899251410",
        "skuName": "平台商品(规格5)",
        "skuNo": "201711031419380",
        "specs": "版本:规格5|",
        "url": "http://lecshop.b0.upaiyun.com/1509689972311.jpg"
    }
]" align="center" prop="giftInfos" />
      <el-table-column label="捐赠寄语" align="center" prop="donationMessage" />
      <el-table-column label="单位名称" align="center" prop="invoiceCompanyName" />
      <el-table-column label="注册地址" align="center" prop="invoiceRegisterAddress" />
      <el-table-column label="注册电话" align="center" prop="invoiceRegisterMobile" />
      <el-table-column label="开户银行" align="center" prop="invoiceOpenBank" />
      <el-table-column label="银行账户" align="center" prop="invoiceBankAccount" />
      <el-table-column label="抬头类型  1 企业  2 个人 " align="center" prop="invoiceTitleType" />
      <el-table-column label="送达时间 社区团购使用" align="center" prop="deliveryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所在地区 省+市+区 社区团购使用" align="center" prop="address" />
      <el-table-column label="详细信息 取货小区 社区团购使用" align="center" prop="detailAddress" />
      <el-table-column label="提货点 社区团购使用" align="center" prop="pickUpAddress" />
      <el-table-column label="提货方式 1 团长送货上门 2 自提" align="center" prop="deliveryType" />
      <el-table-column label="门牌号 社区团购使用" align="center" prop="houseNumber" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['order:OmsOrderAttr:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:OmsOrderAttr:remove']"
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

    <!-- 添加或修改订单属性对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单id  对应oms_order 表中的order_code" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单id  对应oms_order 表中的order_code" />
        </el-form-item>
        <el-form-item label="收货人姓名" prop="receiptName">
          <el-input v-model="form.receiptName" placeholder="请输入收货人姓名" />
        </el-form-item>
        <el-form-item label="收货人的地址 " prop="receiptAddress">
          <el-input v-model="form.receiptAddress" placeholder="请输入收货人的地址 " />
        </el-form-item>
        <el-form-item label="收货人的详细地址" prop="receiptDetailAddress">
          <el-input v-model="form.receiptDetailAddress" placeholder="请输入收货人的详细地址" />
        </el-form-item>
        <el-form-item label="收货人的手机号码" prop="receiptMobile">
          <el-input v-model="form.receiptMobile" placeholder="请输入收货人的手机号码" />
        </el-form-item>
        <el-form-item label="收货人的固定电话" prop="receiptPhone">
          <el-input v-model="form.receiptPhone" placeholder="请输入收货人的固定电话" />
        </el-form-item>
        <el-form-item label="收货人的邮编" prop="receiptZipCode">
          <el-input v-model="form.receiptZipCode" placeholder="请输入收货人的邮编" />
        </el-form-item>
        <el-form-item label="发票类型  0 不需要发票 1增值税普票 2增值税专票 默认0">
          <el-select v-model="form.invoiceType" placeholder="请选择发票类型  0 不需要发票 1增值税普票 2增值税专票 默认0">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="发票抬头" prop="invoiceTitle">
          <el-input v-model="form.invoiceTitle" placeholder="请输入发票抬头" />
        </el-form-item>
        <el-form-item label="发票内容
1:非图书商品
2:明细
3:耗材
4:办公用品" prop="invoiceContent">
          <el-input v-model="form.invoiceContent" placeholder="请输入发票内容
1:非图书商品
2:明细
3:耗材
4:办公用品" />
        </el-form-item>
        <el-form-item label="税号" prop="invoiceTaxid">
          <el-input v-model="form.invoiceTaxid" placeholder="请输入税号" />
        </el-form-item>
        <el-form-item label="订单的赠品信息格式为
[
    {
        "num": 1,
        "skuId": "15096899899251410",
        "skuName": "平台商品(规格5)",
        "skuNo": "201711031419380",
        "specs": "版本:规格5|",
        "url": "http://lecshop.b0.upaiyun.com/1509689972311.jpg"
    }
]" prop="giftInfos">
          <el-input v-model="form.giftInfos" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="捐赠寄语" prop="donationMessage">
          <el-input v-model="form.donationMessage" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="单位名称" prop="invoiceCompanyName">
          <el-input v-model="form.invoiceCompanyName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="注册地址" prop="invoiceRegisterAddress">
          <el-input v-model="form.invoiceRegisterAddress" placeholder="请输入注册地址" />
        </el-form-item>
        <el-form-item label="注册电话" prop="invoiceRegisterMobile">
          <el-input v-model="form.invoiceRegisterMobile" placeholder="请输入注册电话" />
        </el-form-item>
        <el-form-item label="开户银行" prop="invoiceOpenBank">
          <el-input v-model="form.invoiceOpenBank" placeholder="请输入开户银行" />
        </el-form-item>
        <el-form-item label="银行账户" prop="invoiceBankAccount">
          <el-input v-model="form.invoiceBankAccount" placeholder="请输入银行账户" />
        </el-form-item>
        <el-form-item label="抬头类型  1 企业  2 个人 ">
          <el-select v-model="form.invoiceTitleType" placeholder="请选择抬头类型  1 企业  2 个人 ">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="送达时间 社区团购使用" prop="deliveryTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.deliveryTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择送达时间 社区团购使用">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="所在地区 省+市+区 社区团购使用" prop="address">
          <el-input v-model="form.address" placeholder="请输入所在地区 省+市+区 社区团购使用" />
        </el-form-item>
        <el-form-item label="详细信息 取货小区 社区团购使用" prop="detailAddress">
          <el-input v-model="form.detailAddress" placeholder="请输入详细信息 取货小区 社区团购使用" />
        </el-form-item>
        <el-form-item label="提货点 社区团购使用" prop="pickUpAddress">
          <el-input v-model="form.pickUpAddress" placeholder="请输入提货点 社区团购使用" />
        </el-form-item>
        <el-form-item label="提货方式 1 团长送货上门 2 自提">
          <el-select v-model="form.deliveryType" placeholder="请选择提货方式 1 团长送货上门 2 自提">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="门牌号 社区团购使用" prop="houseNumber">
          <el-input v-model="form.houseNumber" placeholder="请输入门牌号 社区团购使用" />
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
import { listOmsOrderAttr, getOmsOrderAttr, delOmsOrderAttr, addOmsOrderAttr, updateOmsOrderAttr, exportOmsOrderAttr } from "@/api/order/OmsOrderAttr";

export default {
  name: "OmsOrderAttr",
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
      // 订单属性表格数据
      OmsOrderAttrList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        receiptName: undefined,
        receiptMobile: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单id  对应oms_order 表中的order_code不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单属性列表 */
    getList() {
      this.loading = true;
      listOmsOrderAttr(this.queryParams).then(response => {
        this.OmsOrderAttrList = response.rows;
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
        receiptName: undefined,
        receiptAddress: undefined,
        receiptDetailAddress: undefined,
        receiptMobile: undefined,
        receiptPhone: undefined,
        receiptZipCode: undefined,
        invoiceType: undefined,
        invoiceTitle: undefined,
        invoiceContent: undefined,
        invoiceTaxid: undefined,
        remark: undefined,
        giftInfos: undefined,
        donationMessage: undefined,
        invoiceCompanyName: undefined,
        invoiceRegisterAddress: undefined,
        invoiceRegisterMobile: undefined,
        invoiceOpenBank: undefined,
        invoiceBankAccount: undefined,
        invoiceTitleType: undefined,
        deliveryTime: undefined,
        address: undefined,
        detailAddress: undefined,
        pickUpAddress: undefined,
        deliveryType: undefined,
        houseNumber: undefined
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
      this.title = "添加订单属性";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOmsOrderAttr(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单属性";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateOmsOrderAttr(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addOmsOrderAttr(this.form).then(response => {
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
      this.$confirm('是否确认删除订单属性编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOmsOrderAttr(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有订单属性数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOmsOrderAttr(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
