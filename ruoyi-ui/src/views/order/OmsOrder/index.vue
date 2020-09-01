<template>
  <div class="app-container">
    <el-radio-group @change="changeStatus()" v-model="orderStatus" style="margin-bottom: 20px">
        <el-radio-button :label="0">全部</el-radio-button>
        <el-radio-button :label="1">待付款</el-radio-button>
        <el-radio-button :label="2">待发货</el-radio-button>
        <el-radio-button :label="3">待收货</el-radio-button>
        <el-radio-button :label="4">已完成</el-radio-button>
        <el-radio-button :label="5">已关闭</el-radio-button>
      </el-radio-group>
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="订单号" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入订单code"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="订单状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择 " clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预售状态" prop="presaleStatus">
        <el-select v-model="queryParams.presaleStatus" placeholder="请选择预售订单状态" clearable size="small">
          <el-option
            v-for="dict in presaleStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单来源" prop="source">
        <el-select v-model="queryParams.source" placeholder="请选择订单来源" clearable size="small">
          <el-option
            v-for="dict in sourceOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="运单号" prop="waybillCode">
        <el-input
          v-model="queryParams.waybillCode"
          placeholder="请输入运单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单类型" prop="orderType">
        <el-select v-model="queryParams.orderType" placeholder="请选择订单类型" clearable size="small">
          <el-option
            v-for="dict in orderTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="物流公司" prop="logisticsCode">
        <el-input
          v-model="queryParams.logisticsCode"
          placeholder="请输入物流公司code"
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
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:OmsOrder:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['order:OmsOrder:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="OmsOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="订单号" align="center" prop="orderCode" />


      <el-table-column label="订单价格" align="center" prop="price" />

      <el-table-column label="订单状态" align="center" prop="status" :formatter="statusFormat" />

      <el-table-column label="支付类型" align="center" prop="payType" :formatter="payTypeFormat" />

      <el-table-column label="订单来源" align="center" prop="source" :formatter="sourceFormat" />
      <el-table-column label="运单号" align="center" prop="waybillCode" />
      <el-table-column label="订单类型" align="center" prop="orderType" :formatter="orderTypeFormat" />
      <el-table-column label="支付时间" align="center" prop="payTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发货时间" align="center" prop="deliveryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>


      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <router-link :to="{path:'/marketingManager/marketingGoods/orderdetail',query:{id:scope.row.id}}">
                     <el-button  type="text"
                                            icon="el-icon-edit" style="margin: 0 10px 5px 0" size="mini">详情</el-button>
                   </router-link>
                   <el-button @click="toConfirmOrder(scope.row.id)" v-if="confirmBtnShow(scope.row)"
                              type="text"
                                         icon="el-icon-edit"
                              size="mini">确认付款
                   </el-button>
                   <el-button @click="toChangePrice(scope.row.id,scope.row.price,scope.row.freightPrice)"
                              v-if="changePriceBtnShow(scope.row)"   type="text"
                                                                                icon="el-icon-edit"
                              style="margin: 0 10px 0 0" size="mini">改价
                   </el-button>
                   <el-button @click="toCancelOrder(scope.row.id)" v-if="cancelBtnShow(scope.row)" size="mini"
                              style="margin: 0 10px 0 0" type="danger">取消
                   </el-button>
                   <el-button @click="toDelivery(scope.row.id)" v-if="scope.row.status == '2' && scope.row.orderType !='7'"
                               type="text"
                                          icon="el-icon-edit" style="margin: 0 10px 0 0" size="mini">发货
                   </el-button>
                   <el-button @click="toWriteOff(scope.row.id)" v-if="scope.row.status == '2' && scope.row.orderType =='7'"
                               type="text"
                                          icon="el-icon-edit"
                              style="margin: 0 10px 0 0" size="mini">核销
                   </el-button>
                   <el-button v-if="scope.row.status == '3'"  type="text"
                                                                         icon="el-icon-edit" style="margin: 0 10px 0 0" size="mini"
                              @click="toChangeDelivery(scope.row.id)">修改物流单号
                   </el-button>
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
 <el-dialog title="确认付款" :visible.sync="confirmShow">
      <el-alert title="注意！ 确认付款后订单状态将修改为待发货，你确定要修改吗？" style="margin-bottom: 20px" type="warning" :closable="false"
                show-icon></el-alert>
      <el-form :model="confirmReason" :rules="confirmRules" ref="confirmForm" label-position="right"
               label-width="100px">
        <el-form-item prop="reason">
          <span slot="label">请填写原因</span>
          <el-input maxlength="200" v-model="confirmReason.reason"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="confirmShow = false">取消</el-button>
        <el-button type="primary" @click="confrimOrder()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="改价" :visible.sync="changePriceShow">
      <el-form :model="changePriceForm" :rules="changePriceRules" ref="changePriceForm" label-position="right"
               label-width="100px">
        <el-form-item prop="price">
          <span slot="label">优惠金额</span>
          <el-input v-model="changePriceForm.price" type="number" maxlength="20" style="width: 200px">
            <template slot="prepend">¥</template>
          </el-input>
        </el-form-item>
        <el-form-item prop="reason">
          <span slot="label">原因</span>
          <el-input v-model="changePriceForm.reason" maxlength="200"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="changePriceShow = false">取消</el-button>
        <el-button type="primary" @click="changePrice">确定</el-button>
      </div>
    </el-dialog>


    <el-dialog title="发货" :visible.sync="deliveryShow">
      <el-form :model="deliveryForm" :rules="deliveryRules" ref="deliveryForm" label-position="right"
               label-width="100px">
        <el-form-item prop="companyId">
          <span slot="label">物流公司</span>
          <el-select v-model="deliveryForm.companyId" filterable placeholder="请选择物流公司" style="width: 100%;">
            <el-option
              v-for="item in companys"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="waybillCode">
          <span slot="label">运单号</span>
          <el-input v-model="deliveryForm.waybillCode" maxlength="20"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="deliveryShow = false">取消</el-button>
        <el-button type="primary" @click="delivery()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改物流单号" :visible.sync="changeDeliveryShow">
      <el-form :model="deliveryForm" :rules="deliveryRules" ref="deliveryForm" label-position="right"
               label-width="100px">
        <el-form-item prop="companyId">
          <span slot="label">物流公司</span>
          <el-select v-model="deliveryForm.companyId" filterable placeholder="请选择物流公司" style="width: 100%;">
            <el-option
              v-for="item in companys"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="waybillCode">
          <span slot="label">运单号</span>
          <el-input v-model="deliveryForm.waybillCode" maxlength="20"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="changeDeliveryShow = false">取消</el-button>
        <el-button type="primary" @click="changeDelivery()">确定</el-button>
      </div>
    </el-dialog>


    <el-dialog title="核销" :visible.sync="writeOffShow" width="480px">
      <el-form>
        <el-form-item style="margin-bottom: 10px">
          <el-input type="text" v-model="writeOffValue" maxlength="6"/>
        </el-form-item>
        <el-form-item class="writeOffButton">
          <el-button @click="digitalButton(1)">1</el-button>
          <el-button @click="digitalButton(2)">2</el-button>
          <el-button @click="digitalButton(3)">3</el-button>
          <el-button @click="digitalButton(4)">4</el-button>
          <el-button @click="digitalButton(5)">5</el-button>
          <el-button @click="digitalButton(6)">6</el-button>
          <el-button @click="digitalButton(7)">7</el-button>
          <el-button @click="digitalButton(8)">8</el-button>
          <el-button @click="digitalButton(9)">9</el-button>
          <el-button @click="digitalButton(0)">0</el-button>
          <el-button @click="clearButton">清除</el-button>
          <el-button @click="backspaceButton">后退</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="writeOffShow = false">取消</el-button>
        <el-button type="primary" @click="writeOff()">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOmsOrder, getOmsOrder, delOmsOrder, addOmsOrder, updateOmsOrder, exportOmsOrder,
 cancelOrder,
     confirmOrder,
     changePrice,
     queryLogisticsCompanys,
     deliverOrder,
     chargeOffVirtualOrder,
     changeExpressNo,
     exportAllOrder,
     exportCheckedOrder
     } from "@/api/order/OmsOrder";

import {priceValidator} from '@/utils/validate';
  // 发货失败错误码
  const deliverErrorMap = {
    '0': '失败',
    '-2': '订单正在申请退款',
    '-1': '订单号含有中文',
    '-3': '拼团未成功',
    '-4': '众筹还未成功',
    '-5': '订单不存在',
    '-6': '物流公司不存在'
  }
export default {
  name: "OmsOrder",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      orderStatus: '0',// 搜索条件订单状态
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
       isProcess: false,// 接口是否处理中
              changeDeliveryShow: false,// 修改物流单号二次确认标记
              writeOffValue: '',// 核销码
              writeOffOrderId: '0',//核销虚拟订单的订单id
              writeOffShow: false,//虚拟订单核销二次确认
              deliveryRules: {
                companyId: [
                  {
                    required: true,
                    message: '请选择物流公司!',
                    trigger: 'blur'
                  }
                ],
                waybillCode: [
                  {
                    required: true,
                    message: '请填写运单号!',
                    trigger: 'blur'
                  }
                ]
              },  // 发货验证规则
              deliveryForm: {
                id: 0,
                companyId: '',//选择的物流公司id
                waybillCode: '',
              },// 发货提交表单
              deliveryShow: false,//发货二次确认标记
              changePriceRules: {
                reason: [
                  {
                    required: true,
                    message: '请填写原因!',
                    trigger: 'blur'
                  }
                ],
                price: [
                  {
                    required: true,
                    trigger: 'blur',
                    validator: priceValidator
                  }
                ]
              },  // 修改价格验证规则
              changePriceForm: {
                price: '',
                reason: ''
              },// 修改价格的表单
              changePriceOrderId: 0,// 修改价格订单id
              changePriceShow: false,// 修改价格二次确认标记
              confirmRules: {
                reason: [
                  {
                    required: true,
                    message: '请填写原因!',
                    trigger: 'blur'
                  }
                ]
              },  // 验证规则
              confirmReason: {reason: ''},// 确认付款的原因
              confirmOrderId: 0,// 确认付款时候的订单id
              confirmShow: false, // 确认付款二次确认标记
               searchTimeValue: null,// 搜索时间

                      oldPrice: 0, // 原始价格 修改订单金额的时候使用
                      freightPrice: 0, //运费 修改订单金额的时候使用
                      selectedIds: [],// 选中的订单id
                      companys: [],// 物流公司

              listLoading: true, // 加载中
      // 总条数
      total: 0,
      // 订单表格数据
      OmsOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 1:待付款  字典
      statusOptions: [],
      // 预售订单状态   普通订单 该状态没作用

      presaleStatusOptions: [],
      // 支付类型  0在线支付  1货到付款 字典
      payTypeOptions: [],
      // 订单来源 1pc  2 h5   3 app字典
      sourceOptions: [],
      // 订单类型 0 普通订单 1 定金预售订单 2全款预售订单 3 拼团订单 4 众筹全款 5 众筹1元 6 众筹无回报 7 虚拟商品订单 8 社区团购订单 默认0 普通订单字典
      orderTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        orderCode: undefined,
        customerId: undefined,
        status: undefined,
        presaleStatus: undefined,
        source: undefined,
        waybillCode: undefined,
        orderType: undefined,
        logisticsCode: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderCode: [
          { required: true, message: "订单code不能为空", trigger: "blur" }
        ],
        masterOrderCode: [
          { required: true, message: "主订单code  下单可能会同时下多个订单 ，一个master_order_code 可以对应多个order_code 即多个订单不能为空", trigger: "blur" }
        ],
        customerId: [
          { required: true, message: "订单所属的会员id不能为空", trigger: "blur" }
        ],
        price: [
          { required: true, message: "订单的最终价格(订单的最终成交价格)不能为空", trigger: "blur" }
        ],
        freightPrice: [
          { required: true, message: "运费不能为空", trigger: "blur" }
        ],

        presaleStatus: [
          { required: true, message: "预售订单状态不能为空", trigger: "blur" }
        ],
        payType: [
          { required: true, message: "支付类型  0在线支付  1货到付款 不能为空", trigger: "blur" }
        ],
        storeId: [
          { required: true, message: "订单店铺id  平台的订单id为0不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("order_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("pre_sale_status").then(response => {
      this.presaleStatusOptions = response.data;
    });
    this.getDicts("pay_type").then(response => {
      this.payTypeOptions = response.data;
    });
    this.getDicts("order_source").then(response => {
      this.sourceOptions = response.data;
    });
    this.getDicts("order_type").then(response => {
      this.orderTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询订单列表 */
    getList() {
      this.loading = true;
      listOmsOrder(this.queryParams).then(response => {
        this.OmsOrderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 1:待付款  字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 预售订单状态   普通订单 该状态没作用
    presaleStatusFormat(row, column) {
      return this.selectDictLabel(this.presaleStatusOptions, row.presaleStatus);
    },
    // 支付类型  0在线支付  1货到付款 字典翻译
    payTypeFormat(row, column) {
      return this.selectDictLabel(this.payTypeOptions, row.payType);
    },
    // 订单来源 1pc  2 h5   3 app字典翻译
    sourceFormat(row, column) {
      return this.selectDictLabel(this.sourceOptions, row.source);
    },
    // 订单类型 0 普通订单 1 定金预售订单 2全款预售订单 3 拼团订单 4 众筹全款 5 众筹1元 6 众筹无回报 7 虚拟商品订单 8 社区团购订单 默认0 普通订单字典翻译
    orderTypeFormat(row, column) {
      return this.selectDictLabel(this.orderTypeOptions, row.orderType);
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
        price: undefined,
        presalePrice: undefined,
        originalPrice: undefined,
        freightPrice: undefined,
        modifyPrice: undefined,
        pointPrice: undefined,
        couponPrice: undefined,
        redEnvelopePrice: undefined,
        concessionalRate: undefined,
        status: undefined,
        presaleStatus: undefined,
        evaluationStatus: "0",
        redEnvelopeCode: undefined,
        couponNo: undefined,
        usePoint: undefined,
        payType: undefined,
        storeId: undefined,
        cancelReson: undefined,
        predepositPay: undefined,
        source: undefined,
        waybillCode: undefined,
        orderType: undefined,
        createTime: undefined,
        payTime: undefined,
        deliveryTime: undefined,
        receivingTime: undefined,
        cancelTime: undefined,
        modifyTime: undefined,
        evaluationTime: undefined,
        presaleTime: undefined,
        groupHead: undefined,
        groupId: undefined,
        groupMarketingId: undefined,
        groupSkuId: undefined,
        groupStatus: "0",
        groupNum: undefined,
        openGroupTime: undefined,
        autoHandleStatus: "0",
        recommended: undefined,
        sRecommended: undefined,
        crowdfundingId: undefined,
        lotteryStatus: "0",
        writeOffCode: undefined,
        communityBuyCustomerId: undefined,
        communityBuyId: undefined,
        profit: undefined,
        communityName: undefined,
        communityBuyName: undefined,
        logisticsCompany: undefined,
        logisticsCode: undefined
      };
      this.resetForm("form");
    },
    // 改变订单状态
          changeStatus() {
            this.handleQuery();
          },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 0;
       this.queryParams.status = this.orderStatus;
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

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除订单编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOmsOrder(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    }, // 是否显示取消按钮
            cancelBtnShow(order) {
              // 订单状态是待付款 并且 订单类型不是定金预售 定金预售订单不能取消
              return order.status == '1' && order.orderType != '1';
            },
            // 是否显示修改价格按钮
            changePriceBtnShow(order) {
              // 订单状态是待发货 并且 订单类型不是预售订单 预售订单不能改价
              return order.status == '1' && order.orderType != '1' && order.orderType != '2';
            },
            // 是否显示确认付款按钮
            confirmBtnShow(order) {
              // 待付款状态 并且可以确认付款的时候（定金预算第二次如果过了付款时间则不能确认付款）
              return order.status == '1' && order.presaleOrderCanPay;
            },
            //  取消订单二次确认
            toCancelOrder(id) {
              this.$confirm('订单不能随意中断，你确定要修改吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                // 处理中则直接返回
                if (this.isProcess) {
                  return;
                }
                this.isProcess = true;
                cancelOrder(id).then(res => {
                  this.isProcess = false;
                  if (res > 0) {
                    this.$message({
                      type: 'success',
                      message: '取消订单成功!'
                    })
                  } else {
                    this.$message({
                      type: 'error',
                      message: '取消订单失败!'
                    })
                  }
                  this.handleQuery();
                })
              })
            },
            // 确认付款二次确认
            toConfirmOrder(id) {
              this.confirmShow = true;
              this.confirmOrderId = id;
              if (this.$refs["confirmForm"]) {
                this.$refs["confirmForm"].resetFields();
              }
              this.confirmReason = {reason: ''};
            },
            // 确认付款
            confrimOrder() {
              this.$refs['confirmForm'].validate(valid => {
                if (valid && !this.isProcess) {
                  this.isProcess = true;
                  confirmOrder(this.confirmOrderId, this.confirmReason).then(res => {
                    this.isProcess = false;
                    if (res > 0) {
                      this.$message({
                        type: 'success',
                        message: '确认付款成功!'
                      });
                    } else {
                      this.$message({
                        type: 'error',
                        message: '确认付款失败!'
                      });
                    }
                    this.confirmShow = false;
                    this.confirmOrderId = 0;
                    this.handleQuery();
                  })
                }
              })
            },
            // 改价二次确认
            toChangePrice(id, price, freightPrice) {
              this.oldPrice = price;
              this.freightPrice = freightPrice;
              this.changePriceOrderId = id;
              this.changePriceShow = true;
              if (this.$refs["changePriceForm"]) {
                this.$refs["changePriceForm"].resetFields();
              }
              this.changePriceForm = {
                price: '',
                reason: ''
              }
            },
            // 修改价格
            changePrice() {
              this.$refs['changePriceForm'].validate(valid => {
                if (!this.isProcess && valid && !this.checkModifyPriceError(this.changePriceForm.price, this.oldPrice, this.freightPrice)) {
                  this.isProcess = true;
                  changePrice(this.changePriceOrderId, this.changePriceForm).then(res => {
                    this.isProcess = false;
                    if (res > 0) {
                      this.$message({
                        type: 'success',
                        message: '修改价格成功!'
                      });
                    } else if (res == -1) {
                      this.$message({
                        type: 'error',
                        message: '修改后订单金额必须大0.1!!'
                      });
                    } else if (res == -2) {
                      this.$message({
                        type: 'error',
                        message: '此订单不能修改价格!'
                      });
                    } else {
                      this.$message({
                        type: 'error',
                        message: '修改价格失败!'
                      });
                    }
                    this.changePriceShow = false;
                    this.changePriceOrderId = 0;
                    this.oldPrice = 0;
                    this.freightPrice = 0;
                    this.handleQuery();
                  })
                }
              })
            },
            //校验修改后的价格
            checkModifyPriceError(price, oldPrice, freightPrice) {
              var errorFlag = false;
              if ((oldPrice * 100 - price * 100) / 100 < 0.1) {
                errorFlag = true;
                this.$message({
                  type: 'error',
                  message: '修改后金额必须大于0.1!!'
                });
              } else if ((oldPrice * 100 - price * 100 - freightPrice * 100) / 100 < 0.1) {
                errorFlag = true;
                this.$message({
                  type: 'error',
                  message: '修改后金额必须大于运费'
                });
              }
              return errorFlag;
            },
            // 发货二次确认
            toDelivery(id) {
              this.deliveryForm = {
                id: id,
                waybillCode: '',
                company: '',
              }
              if (this.$refs["deliveryForm"]) {
                this.$refs["deliveryForm"].resetFields();
              }

              queryLogisticsCompanys().then(res => {
                this.deliveryShow = true;
                this.companys = res;
              });
            },
            // 发货
            delivery() {
              this.$refs['deliveryForm'].validate(valid => {
                if (valid && !this.isProcess) {
                  this.isProcess = true;
                  deliverOrder(this.deliveryForm).then(res => {
                    this.isProcess = false;
                    if (res > 0) {
                      this.$message({
                        type: 'success',
                        message: '发货成功'
                      });
                    } else {
                      this.$message({
                        type: 'error',
                        message: deliverErrorMap[res]
                      });
                    }
                    this.deliveryShow = false;
                    this.handleQuery();
                  })
                }
              })
            },
            digitalButton(num) {
              if (this.writeOffValue.length < 6) {
                this.writeOffValue = this.writeOffValue + num
              }
            },
            clearButton() {
              this.writeOffValue = ''
            },
            backspaceButton() {
              this.writeOffValue = this.writeOffValue.slice(0, this.writeOffValue.length - 1)
            },
            // 核销二次确认
            toWriteOff(id) {
              this.writeOffOrderId = id;
              this.writeOffShow = true;
              this.writeOffValue = '';
            },
            // 核销虚拟订单
            writeOff() {
              if (!this.writeOffValue) {
                this.$message({
                  type: 'error',
                  message: '请输入核销码'
                });
                return;
              }

              if (this.isProcess) {
                return;
              }
              this.isProcess = true;
              chargeOffVirtualOrder(this.writeOffOrderId, this.writeOffValue).then(res => {
                this.isProcess = false;
                if (res == -1) {
                  this.$message({
                    type: 'error',
                    message: '核销码错误'
                  });
                } else if (res > 0) {
                  this.$message({
                    type: 'success',
                    message: '核销成功'
                  });
                  this.writeOffOrderId = 0;
                  this.writeOffShow = false;
                  this.handleQuery();
                } else {
                  this.$message({
                    type: 'error',
                    message: '核销失败'
                  });
                }
              });
            },
            // 修改物流单号二次确认
            toChangeDelivery(id) {
              this.deliveryForm = {
                id: id,
                waybillCode: '',
                company: '',
              }
              if (this.$refs["deliveryForm"]) {
                this.$refs["deliveryForm"].resetFields();
              }
              queryLogisticsCompanys().then(res => {
                this.changeDeliveryShow = true;
                this.companys = res;
              });
            },
            // 修改物流单号
            changeDelivery() {
              this.$refs['deliveryForm'].validate(valid => {
                if (valid && !this.isProcess) {
                  this.isProcess = true;
                  changeExpressNo(this.deliveryForm).then(res => {
                    this.isProcess = false;
                    if (res > 0) {
                      this.$message({
                        type: 'success',
                        message: '修改物流单号成功'
                      });
                    } else {
                      this.$message({
                        type: 'error',
                        message: deliverErrorMap[res],
                      });
                    }
                    this.changeDeliveryShow = false;
                    this.handleQuery();
                  })
                }
              })
            },
            // 导出所有订单
            exportAll() {
              let status = this.orderStatus == 0 ? '' : this.orderStatus;
              exportAllOrder(status).then(res => {
                let blob = new Blob([res], {type: "application/vnd.ms-excel"});
                let objectUrl = URL.createObjectURL(blob);
                window.location.href = objectUrl;
              });
            },
            // 导出选中的订单
            exportSelected() {
              if (this.selectedIds && this.selectedIds.length > 0) {
                let status = this.orderStatus == 0 ? '' : this.orderStatus;
                exportCheckedOrder(status, this.selectedIds.toString()).then(res => {
                  let blob = new Blob([res], {type: "application/vnd.ms-excel"});
                  let objectUrl = URL.createObjectURL(blob);
                  window.location.href = objectUrl;
                });
              } else {
                this.$message({
                  message: '请至少选择一条订单！',
                  type: 'warning'
                })
              }
            },
            // 批量打印订单
            toPrint() {
              if (this.selectedIds && this.selectedIds.length > 0) {
                this.$router.push({
                  path: '/ordermanager/ordermanager/printorders',
                  query: {ids: this.selectedIds.toString()}
                });
              } else {
                this.$message({
                  message: '请至少选择一条订单！',
                  type: 'warning'
                })
              }
            },


    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有订单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOmsOrder(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
