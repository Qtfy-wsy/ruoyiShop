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
    <div class="filter-container">
     <el-form :model="listQuery" ref="queryForm" :inline="true" label-width="68px">
          <el-form-item label="订单号" prop="orderCode">
            <el-input
              v-model="listQuery.orderCode"
              placeholder="请输入订单code"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="订单状态" prop="status">
            <el-select v-model="listQuery.status" placeholder="请选择 " clearable size="small">
              <el-option
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="预售状态" prop="presaleStatus">
            <el-select v-model="listQuery.presaleStatus" placeholder="请选择预售订单状态" clearable size="small">
              <el-option
                v-for="dict in presaleStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="订单来源" prop="source">
            <el-select v-model="listQuery.source" placeholder="请选择订单来源" clearable size="small">
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
              v-model="listQuery.waybillCode"
              placeholder="请输入运单号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="订单类型" prop="orderType">
            <el-select v-model="listQuery.orderType" placeholder="请选择订单类型" clearable size="small">
              <el-option
                v-for="dict in orderTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="下单时间" prop="logisticsCode">
           <el-date-picker
                             style="width: 355px"
                             class="search-datepicker"
                             size="medium"
                             :clearable="false"
                             v-model="searchTimeValue"
                             type="datetimerange"
                             range-separator="至"
                             start-placeholder="开始日期"
                             end-placeholder="结束日期"
                             align="right">
                           </el-date-picker>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>

          </el-form-item>
        </el-form>


      <br/>
      <el-dropdown class="filter-item" style="margin-left: 5px;">
        <el-button size="medium" type="primary">导出订单<i class="el-icon-arrow-down el-icon--right"></i></el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="exportAll()">导出所有</el-dropdown-item>
          <el-dropdown-item @click.native="exportSelected()">导出选中</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-button @click="toPrint()" class="filter-item" size="medium" style="margin-left: 5px;" type="primary"
                 icon="el-icon-printer">
        批量打印
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
      <el-table-column type="selection" align="center" width="40"/>
      <el-table-column label="订单号" min-width="162">
        <template slot-scope="scope">{{ scope.row.orderCode }}</template>
      </el-table-column>
      <el-table-column label="买家" min-width="110">
        <template slot-scope="scope">{{ scope.row.customerName }}</template>
      </el-table-column>
      <el-table-column label="下单时间" width="160">
        <template slot-scope="scope">{{ scope.row.createTime }}</template>
      </el-table-column>
      <el-table-column label="订单类型" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.orderType == '0'">普通订单</span>
          <span v-if="scope.row.orderType == '1'">定金预售</span>
          <span v-if="scope.row.orderType == '2'">全款预售</span>
          <span v-if="scope.row.orderType == '3'">拼团订单</span>
          <span v-if="scope.row.orderType == '7'">虚拟商品订单</span>
        </template>
      </el-table-column>
      <el-table-column label="订单总额" min-width="80">
        <template slot-scope="scope">{{ scope.row.price.toFixed(2) }}</template>
      </el-table-column>
      <el-table-column label="订单状态" width="82">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '1'" type="warning">待付款</el-tag>
          <el-tag v-if="scope.row.status == '2'" type="warning">待发货</el-tag>
          <el-tag v-if="scope.row.status == '3'" type="warning">待收货</el-tag>
          <el-tag v-if="scope.row.status == '4'" type="success">已完成</el-tag>
          <el-tag v-if="scope.row.status == '5'" type="info">已关闭</el-tag>
          <el-tag v-if="scope.row.status == '6'" type="info">已关闭</el-tag>
          <el-tag v-if="scope.row.status == '7'" type="info">已关闭</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="店铺名称" min-width="150">
        <template slot-scope="scope">{{ scope.row.storeName }}</template>
      </el-table-column>
      <el-table-column label="来源" width="50">
        <template slot-scope="scope">
          <span v-if="scope.row.source == '2'">H5</span>
          <span v-if="scope.row.source == '1'">PC</span>
          <span v-if="scope.row.source == '3'">APP</span>
          <span v-if="scope.row.source == '4'">小程序</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="95">
        <template slot-scope="scope">
          <router-link :to="{path:'/storemanager/storeorder/storeorderdetail',query:{id:scope.row.id}}">
            <el-button type="success" style="margin: 0 5px 5px 0" size="mini">详情</el-button>
          </router-link>
          <el-button v-if="confirmBtnShow(scope.row)" type="primary" style="margin: 0 0 5px 0" size="mini"
                     @click="toConfirmOrder(scope.row.id,scope.row.storeId)">确认付款
          </el-button>
          <el-button v-if="changePriceBtnShow(scope.row)" type="primary" style="margin: 0" size="mini"
                     @click="toChangePrice(scope.row.id,scope.row.storeId,scope.row.price,scope.row.freightPrice)">改价
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="listQuery.pageNum+1" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog title="确认付款" :visible.sync="confirmShow">
      <el-alert title="注意！ 确认付款后订单状态将修改为待发货，你确定要修改吗？" style="margin-bottom: 20px" type="warning" :closable="false"
                show-icon/>
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

  </div>
</template>

<script>

  import {queryStoreOrders, confirmOrder, changePrice, exportAllOrder, exportCheckedOrder} from '@/api/store/storeorderlist';
  import {priceValidator} from '@/utils/validate';

  export default {
    data() {
      return {
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
        changePriceStoreId: 0,// 修改价格订单店铺id
        changePriceShow: false,// 修改价格二次确认标记
        confirmOrderId: 0,// 确认付款时候的订单id
        confirmStoreId: 0,// 确认付款时候的订单店铺id
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
        confirmShow: false, // 确认付款二次确认标记
        orderStatus: '0',// 搜索条件订单状态
        isProcess: false, // 接口是否处理中
        listLoading: true, // 加载中
        list: null, // 订单数据
        total: null, // 数据总数
        listQuery: {
          pageNum: 0,
          pageSize: 10,
          status: '',
          orderCode: '',
          customerName: '',
          storeName: '',
          orderType: '',
          startTime: '',
          endTime: ''
        }, // 查询参数
        selectedIds: [],// 选中的订单id
        searchTimeValue: null,// 搜索时间
        oldPrice: 0, // 原始价格 修改订单金额的时候使用
        freightPrice: 0, //运费 修改订单金额的时候使用
        //订单类型
        orderTypeOptions: [],
        //订单来源
        sourceOptions: [],
        //预售订单状态
        presaleStatusOptions: [],
        //订单状态
        statusOptions: []
      }
    },
    created() {
      this.getList();
      this.getDicts("order_type").then(response => {
        this.orderTypeOptions = response.data;
      });
      this.getDicts("order_source").then(response => {
        this.sourceOptions = response.data;
      });
      this.getDicts("pre_sale_status").then(response => {
        this.presaleStatusOptions = response.data;
      });
      this.getDicts("order_status").then(response => {
        this.statusOptions = response.data;
      });
    },
    methods: {
      getList() {
        this.listLoading = true
        queryStoreOrders(this.listQuery).then((response) => {
          this.list = response.data;
          this.total = response.recordsTotal;
          this.listLoading = false
        })
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val - 1;
        this.getList();
      },
      handleSelectionChange(val) {
        if (val && val.length > 0) {
          this.selectedIds = val.map(row => row.id);
        } else {
          this.selectedIds = [];
        }
      },
      // 过滤查询
      handleQuery() {
        this.listQuery.pageNum = 0;
        if (this.searchTimeValue) {
          this.listQuery.startTime = this.searchTimeValue[0];
          this.listQuery.endTime = this.searchTimeValue[1];
        } else {
          this.listQuery.startTime = '';
          this.listQuery.endTime = '';
        }

        this.listQuery.status = this.orderStatus;

        if (this.listQuery.status == 0) {
          this.listQuery.status = '';
        }

        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.getList();
      },
      // 改变订单状态
      changeStatus() {
        this.handleQuery();
      },
      // 是否显示确认付款按钮
      confirmBtnShow(order) {
        // 待付款状态 并且可以确认付款的时候（定金预算第二次如果过了付款时间则不能确认付款）
        return order.status == '1' && order.presaleOrderCanPay;
      },
      // 是否显示修改价格按钮
      changePriceBtnShow(order) {
        // 订单状态是待发货 并且 订单类型不是预售订单 预售订单不能改价
        return order.status == '1' && order.orderType != '1' && order.orderType != '2';
      },
      // 确认付款二次确认
      toConfirmOrder(id, storeId) {
        this.confirmShow = true;
        this.confirmOrderId = id;
        this.confirmStoreId = storeId;
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
            confirmOrder(this.confirmOrderId, this.confirmStoreId, this.confirmReason).then(res => {
              this.isProcess = false;
              if (res > 0) {
                this.$message({
                  type: 'success',
                  message: '确认付款成功'
                });
              } else {
                this.$message({
                  type: 'error',
                  message: '确认付款失败'
                });
              }
              this.confirmShow = false;
              this.confirmOrderId = 0;
              this.confirmStoreId = 0;
              this.handleQuery();
            })
          }
        })
      },
      // 改价二次确认
      toChangePrice(id, storeId, price, freightPrice) {
        this.oldPrice = price;
        this.freightPrice = freightPrice;
        this.changePriceOrderId = id;
        this.changePriceStoreId = storeId;
        this.changePriceShow = true;
        if (this.$refs["changePriceForm"]) {
          this.$refs["changePriceForm"].resetFields();
        }
        this.changePriceForm = {
          price: '',
          reason: ''
        }
      },
      //校验修改后的价格
      checkModifyPriceError(price, oldPrice, freightPrice) {
        let errorFlag = false;
        if ((oldPrice * 100 - price * 100) / 100 < 0.1) {
          errorFlag = true;
          this.$message({
            type: 'error',
            message: '修改后金额必须大于0.1!'
          });
        } else if ((oldPrice * 100 - price * 100 - freightPrice * 100) / 100 < 0.1) {
          errorFlag = true;
          this.$message({
            type: 'error',
            message: '修改后金额必须大于运费!'
          });
        }
        return errorFlag;
      },
      changePrice() {
        this.$refs['changePriceForm'].validate(valid => {
          if (!this.isProcess && valid && !this.checkModifyPriceError(this.changePriceForm.price, this.oldPrice, this.freightPrice)) {
            this.isProcess = true;
            changePrice(this.changePriceOrderId, this.changePriceStoreId, this.changePriceForm).then(res => {
              this.isProcess = false;
              if (res > 0) {
                this.$message({
                  type: 'success',
                  message: '修改价格成功'
                });
              } else if (res == -1) {
                this.$message({
                  type: 'error',
                  message: '修改后订单金额必须大0.1!'
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
              this.changePriceStoreId = 0;
              this.oldPrice = 0;
              this.freightPrice = 0;
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
            path: '/storemanager/storeorder/pringstoreorders',
            query: {ids: this.selectedIds.toString()}
          });
        } else {
          this.$message({
            message: '请至少选择一条订单！',
            type: 'warning'
          })
        }
      }
    }
  }
</script>
