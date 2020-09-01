<template>
  <div class="app-container">
    <el-radio-group v-model="orderStatus" @change="changeStatus" style="margin-bottom: 20px">
      <el-radio-button :label="0">全部</el-radio-button>
      <el-radio-button :label="1">拼团中</el-radio-button>
      <el-radio-button :label="2">已成团</el-radio-button>
      <el-radio-button :label="3">已失效</el-radio-button>
      <el-radio-button :label="4">未付款</el-radio-button>
    </el-radio-group>
    <div class="filter-container">
      <el-input maxlength="34" v-model="listQuery.groupId" placeholder="拼团编号" size="medium" style="width: 150px;"
                class="filter-item"/>
      <el-input maxlength="15" v-model="listQuery.customerName" placeholder="团长用户名" size="medium" style="width: 150px;"
                class="filter-item"/>
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
        format="yyyy-MM-dd HH:mm:ss"
        value-format="yyyy-MM-dd HH:mm:ss"
        align="right">
      </el-date-picker>
      <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleQuery">搜索
      </el-button>
      <br/>

    </div>

    <el-table
      class="orderExpandTable"
      :data="list"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-table
            :data="scope.row.groupMemberOrders"
            border
            fit
            highlight-current-row
          >
            <el-table-column label="订单编号" min-width="162">
              <template slot-scope="scope">{{ scope.row.orderCode }}</template>
            </el-table-column>
            <el-table-column label="拼团用户">
              <template slot-scope="scope">{{ scope.row.customerName }}</template>
            </el-table-column>
            <el-table-column label="拼团角色">
              <template slot-scope="scope">
                <span v-if="scope.row.groupHead == '0'" style="color: #FCB322">团长</span>
                <span v-if="scope.row.groupHead == '1'">团员</span>
              </template>
            </el-table-column>
            <el-table-column label="下单时间" width="160">
              <template slot-scope="scope">{{ scope.row.createTime }}</template>
            </el-table-column>
            <el-table-column label="订单总额">
              <template slot-scope="scope">{{ scope.row.price.toFixed(2) }}</template>
            </el-table-column>
            <el-table-column label="订单状态" min-width="82">
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
            <el-table-column label="操作">
              <template slot-scope="scope">
                <router-link :to="{path:'/marketingManager/marketingGoods/grouporderdetail',query:{id:scope.row.id}}">
                  <el-button type="success" style="margin: 0 10px 5px 0" size="mini">详情</el-button>
                </router-link>
                <el-button v-if="scope.row.status == '2' && scope.row.groupStatus == '1'" type="primary"
                           style="margin: 0" size="mini"
                           @click="toDelivery(scope.row.id)">发货
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column type="selection" align="center" width="45"/>
      <el-table-column label="拼团编号" min-width="162">
        <template slot-scope="scope">{{ scope.row.groupId }}</template>
      </el-table-column>
      <el-table-column label="商品名称" min-width="250">
        <template slot-scope="scope">{{ scope.row.orderSkus[0].skuName }}</template>
      </el-table-column>
      <el-table-column label="成团人数">
        <template slot-scope="scope">{{ scope.row.groupNum }}</template>
      </el-table-column>
      <el-table-column label="来源" width="50">
        <template slot-scope="scope">
          <span v-if="scope.row.source == '1'">PC</span>
          <span v-if="scope.row.source == '2'">H5</span>
          <span v-if="scope.row.source == '3'">APP</span>
          <span v-if="scope.row.source == '4'">小程序</span>
        </template>
      </el-table-column>
      <el-table-column label="拼团状态" width="82">
        <template slot-scope="scope">
          <el-tag v-if="groupStatus(scope.row)=='2'" type="warning">拼团中</el-tag>
          <el-tag v-if="groupStatus(scope.row)=='1'" type="success">已成团</el-tag>
          <el-tag v-if="groupStatus(scope.row)=='3'" type="info">已失效</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="开团时间" width="160">
        <template slot-scope="scope">{{ scope.row.payTime }}</template>
      </el-table-column>
      <el-table-column label="结束时间" width="160">
        <template slot-scope="scope">{{groupEndTime( scope.row.payTime)}}
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="listQuery.pageNum+1" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog title="发货" :visible.sync="deliveryShow">
      <el-form :model="deliveryForm" :rules="deliveryRules" ref="deliveryForm" label-position="right"
               label-width="100px">
        <el-form-item prop="companyId">
          <span slot="label">物流公司</span>
          <el-select v-model="deliveryForm.companyId" filterable placeholder="请选择物流公司">
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
  </div>
</template>

<script>
  import {
    queryGroupOrders,
    exportAllOrder,
    exportCheckedOrder,
    queryLogisticsCompanys,
    deliverOrder
  } from '@/api/order/grouplist';
  import {parseTime} from '@/utils/index';


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
    data() {
      return {
        isProcess: false,// 接口是否处理中
        logisticsName: '',// 物流公司名称
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
        searchTimeValue: null,// 搜索时间
        orderStatus: '0',//拼团订单状态
        listLoading: true, // 加载中
        list: null, // 拼团订单数据
        total: null, // 数据总数
        listQuery: {
          filterPayTime: '',
          groupStatus: '0',
          pageNum: 0,
          pageSize: 10,
          groupId: '',
          customerName: '',
          startTime: '',
          endTime: ''
        }, // 查询参数
        selectedIds: [],// 选中的订单id
        companys: [],// 物流公司
      }
    },
    created() {
      if (this.$route.query.status) {
        this.orderStatus = this.$route.query.status;
      }
      this.handleQuery()
    },
    methods: {
      getList() {
        this.listLoading = true
        queryGroupOrders(this.listQuery).then((response) => {
          // 设置团长订单
          if (response.data && response.data.length > 0) {
            response.data.forEach(groupOrder => {
              groupOrder.groupMemberOrders.unshift({
                id: groupOrder.id,
                orderCode: groupOrder.orderCode,
                customerName: groupOrder.customerName,
                groupHead: groupOrder.groupHead,
                createTime: groupOrder.createTime,
                price: groupOrder.price,
                status: groupOrder.status,
                groupStatus: groupOrder.groupStatus
              });
            })
          }
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

        // 设置时间
        if (this.searchTimeValue) {
          this.listQuery.startTime = this.searchTimeValue[0];
          this.listQuery.endTime = this.searchTimeValue[1];
        } else {
          this.listQuery.startTime = '';
          this.listQuery.endTime = '';
        }

        // 根据查询条件设置不同的状态
        if (this.orderStatus == '0') {
          delete this.listQuery.filterPayTime;
          delete this.listQuery.groupStatus;
        } else if (this.orderStatus == '1') {
          this.listQuery.filterPayTime = '1';
          this.listQuery.groupStatus = '0';
        } else if (this.orderStatus == '2') {
          delete this.listQuery.filterPayTime;
          this.listQuery.groupStatus = '1';
        } else if (this.orderStatus == '3') {
          this.listQuery.filterPayTime = '-1';
          this.listQuery.groupStatus = '0';
        }
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.getList();
      },
      // 改变查询条件
      changeStatus() {
        if (this.orderStatus == '4') {
          this.$router.push({path: '/marketingManager/marketingGoods/nopaylist'})
        } else {
          this.handleQuery();
        }
      },
      // 导出所有订单
      exportAll() {
        exportAllOrder(this.getExportStatus()).then(res => {
          let blob = new Blob([res], {type: "application/vnd.ms-excel"});
          let objectUrl = URL.createObjectURL(blob);
          window.location.href = objectUrl;
        });
      },
      // 获得订单导出的状态
      getExportStatus() {
        if (this.orderStatus == '0') {
          return '';
        } else if (this.orderStatus == '1') {
          return '0';
        } else if (this.orderStatus == '2') {
          return '1';
        } else if (this.orderStatus == '3') {
          return '-2';
        }
      },
      // 导出选中的订单
      exportSelected() {
        if (this.selectedIds && this.selectedIds.length > 0) {
          exportCheckedOrder(this.getExportStatus(), this.selectedIds.toString()).then(res => {
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

        queryLogisticsCompanys(id).then(res => {
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
                  message: deliverErrorMap[res],
                });
              }
              this.deliveryShow = false;
              this.handleQuery();
            })
          }
        })
      },
    },
    computed: {
      // 拼团订单状态
      groupStatus() {
        return function (groupOrder) {
          // 已成团
          if (groupOrder.groupStatus == '1') {
            return '1';
          } else {
            if (new Date(groupOrder.payTime) - new Date() + 86400000 > 0) {
              // 拼团中
              return '2';
            } else {
              //已失效
              return '3';
            }
          }
        }
      },
      // 团购结束时间
      groupEndTime() {
        return function (time) {
          return parseTime(new Date(new Date(time).getTime() + 86400000), 'yyyy-MM-dd hh:mm:ss');
        }
      }
    }
  }
</script>
