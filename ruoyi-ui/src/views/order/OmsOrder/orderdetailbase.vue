<template>
  <div class="app-container">
    <el-steps :active="activeStatus" align-center finish-status="success">
      <el-step title="已下单" :description="order.createTime"></el-step>
      <el-step title="已付款" :description="order.payTime"></el-step>
      <el-step title="已发货" :description="order.deliveryTime"></el-step>
      <el-step title="已完成" :description="order.receivingTime"></el-step>
    </el-steps>
    <div id="print_box">
      <div style="line-height: 50px">订单概况</div>
      <div class="detailsTable">
        <el-row>
          <el-col :span="8">订单编号：{{order.orderCode}}</el-col>
          <el-col :span="8">下单时间：{{order.createTime}}</el-col>
          <el-col :span="8">订单状态：{{orderStatus}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="8">是否使用预存款支付：{{order.predepositPay=="0"?'否':'是'}}</el-col>
          <el-col :span="8">满优惠金额：{{(order.concessionalRate - order.pointPrice -
            order.couponPrice-order.redEnvelopePrice).toFixed(2)}}
          </el-col>
          <el-col :span="8">优惠券金额：{{order.couponPrice == 0?order.couponPrice.toFixed(2):order.couponPrice.toFixed(2)+'('
            + order.couponNo + ')'}}
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">积分金额：{{order.pointPrice == 0?order.pointPrice.toFixed(2):order.pointPrice.toFixed(2) +
            '('+order.usePoint + ')'}}
          </el-col>
          <el-col :span="8">
            红包金额：{{order.redEnvelopePrice==0?order.redEnvelopePrice.toFixed(2):order.redEnvelopePrice.toFixed(2) + '(' +
            order.redEnvelopeCode +')'}}
          </el-col>
          <el-col :span="8">订单原始金额：{{order.originalPrice.toFixed(2)}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="8">订单总优惠金额：{{order.concessionalRate.toFixed(2)}}</el-col>
          <el-col :span="8">订单修改金额：{{order.modifyPrice.toFixed(2)}}</el-col>
          <el-col :span="8">订单实际交易金额：{{order.price.toFixed(2)}}</el-col>
        </el-row>
      </div>

      <div v-if="order.orderType == '1'" style="line-height: 50px">预售流程</div>
      <div v-if="order.orderType == '1' && order.presaleStatus == '0'" class="detailsTable">
        <el-row>
          <el-col :span="24">阶段一：支付定金(待付款)&nbsp;&nbsp;&nbsp;&nbsp;金额：¥{{order.presalePrice.toFixed(2)}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="24">阶段二：支付尾款(待付款)&nbsp;&nbsp;&nbsp;&nbsp;金额：¥{{(order.price -
            order.presalePrice).toFixed(2)}}
          </el-col>
        </el-row>
      </div>
      <div v-if="order.orderType == '1' && order.presaleStatus == '1'" class="detailsTable">
        <el-row>
          <el-col :span="24">阶段一：支付定金(已完成)&nbsp;&nbsp;&nbsp;&nbsp;金额：¥{{(order.price -
            order.presalePrice).toFixed(2)}}
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">阶段二：支付尾款(待付款)&nbsp;&nbsp;&nbsp;&nbsp;金额：¥{{order.presalePrice.toFixed(2)}}</el-col>
        </el-row>
      </div>
      <div v-if="order.orderType == '1' && order.presaleStatus == '2'" class="detailsTable">
        <el-row>
          <el-col :span="24">阶段一：支付定金(已完成)&nbsp;&nbsp;&nbsp;&nbsp;金额：¥{{(order.price - order.presalePrice).toFixed(2)
            }}
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">阶段二：支付尾款(已完成)&nbsp;&nbsp;&nbsp;&nbsp;金额：¥{{order.presalePrice.toFixed(2)}}</el-col>
        </el-row>
      </div>

      <div v-if="order.orderType != '6'">
        <div style="line-height: 50px">物流信息</div>
        <div class="detailsTable">
          <el-row>
            <el-col v-if="order.orderType != '7'" :span="8">物流公司：{{order.logisticsCompany}}
            </el-col>
            <el-col v-if="order.orderType != '7'" :span="8">物流单号：{{order.waybillCode}}</el-col>
            <el-col v-if="order.orderType != '7'" :span="8">配送方式：快递配送</el-col>
          </el-row>
          <el-row>
            <el-col v-if="order.orderType != '7'" :span="8">运费：{{order.freightPrice.toFixed(2)}}</el-col>
            <el-col :span="8">收货地址：{{order.orderAttr.receiptAddress}}</el-col>
            <el-col :span="8">详细地址：{{order.orderAttr.receiptDetailAddress}}</el-col>
          </el-row>
          <el-row>
            <el-col :span="8">收货人：{{order.orderAttr.receiptName}}</el-col>
            <el-col :span="8">联系电话：{{order.orderAttr.receiptPhone}}</el-col>
            <el-col :span="8">手机：{{order.orderAttr.receiptMobile}}</el-col>
          </el-row>
          <el-row>
            <el-col :span="8">邮编：{{order.orderAttr.receiptZipCode}}</el-col>
            <el-col :span="16">客户留言：{{order.orderAttr.remark}}</el-col>
          </el-row>
        </div>
      </div>
      <div v-if="order.orderType != '6'">
        <div style="line-height: 50px">发票信息</div>
        <div v-if="order.orderAttr.invoiceType == '0'" class="detailsTable">
          <el-row>
            <el-col :span="24">不需要发票</el-col>
          </el-row>
        </div>
        <div v-if="order.orderAttr.invoiceType == '1'" class="detailsTable">
          <el-row>
            <el-col :span="8">发票类型: 增值税普票</el-col>
            <el-col :span="8">发票抬头：{{order.orderAttr.invoiceTitle}}</el-col>
            <el-col :span="8">抬头类型：{{invoiceTitleType[order.orderAttr.invoiceTitleType]}}</el-col>
          </el-row>
          <el-row>
            <el-col :span="8">发票内容：{{invoiceContentMap[order.orderAttr.invoiceContent]}}</el-col>
            <el-col :span="8">税号：{{order.orderAttr.invoiceTaxid}}</el-col>
          </el-row>
        </div>
        <div v-if="order.orderAttr.invoiceType == '2'" class="detailsTable">
          <el-row>
            <el-col :span="8">发票类型：增值税专票</el-col>
            <el-col :span="8">单位名称：{{order.orderAttr.invoiceCompanyName}}</el-col>
            <el-col :span="8">税号：{{order.orderAttr.invoiceTaxid}}</el-col>
          </el-row>
          <el-row>
            <el-col :span="8">注册地址：{{order.orderAttr.invoiceRegisterAddress}}</el-col>
            <el-col :span="8">注册电话：{{order.orderAttr.invoiceRegisterMobile}}</el-col>
            <el-col :span="8">开户银行：{{order.orderAttr.invoiceOpenBank}}</el-col>
          </el-row>
          <el-row>
            <el-col :span="8">银行账户：{{order.orderAttr.invoiceBankAccount}}</el-col>
            <el-col :span="16">发票内容：{{invoiceContentMap[order.orderAttr.invoiceContent]}}</el-col>
          </el-row>
        </div>
      </div>

      <div v-if="order.orderType == '6'">
        <div style="line-height: 50px">捐赠寄语</div>
        <div class="detailsTable">
          <el-row>
            <el-col :span="24">{{order.orderAttr.donationMessage}}</el-col>
          </el-row>
        </div>
      </div>

      <div style="line-height: 50px">商品列表</div>
      <table cellspacing="0" cellpadding="0" border="0" class="el-table"
             style="margin-top: 5px; border: 1px solid #EBEEF5;" width="100%">
        <thead>
        <tr>
          <th width="80" class="cell is-leaf">商品图片</th>
          <th class="cell is-leaf">商品名称</th>
          <th width="100" class="cell is-leaf">销售价</th>
          <th width="60" class="cell is-leaf">数量</th>
          <th class="cell is-leaf">商品规格</th>
          <th width="100" class="cell is-leaf">商品总价</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="sku in order.orderSkus">
          <td class="cell"><img :src="sku.skuImage" width="50" height="50" alt="">
          </td>
          <td class="cell">{{sku.skuName}}</td>
          <td class="cell">{{sku.skuPrice.toFixed(2)}}</td>
          <td class="cell">{{sku.num}}</td>
          <td class="cell">{{sku.skuSpecs}}</td>
          <td class="cell">{{sku.price.toFixed(2)}}</td>
        </tr>
        <tr v-for="gift in order.orderGifts">
          <td class="cell"><img :src="gift.url" width="50" height="50" alt="">
          </td>
          <td class="cell">
            <el-tag size="small" type="warning">赠品</el-tag>
            {{gift.skuName}}
          </td>
          <td class="cell">{{parseFloat(0).toFixed(2)}}</td>
          <td class="cell">{{gift.num}}</td>
          <td class="cell">{{gift.specs}}</td>
          <td class="cell">{{parseFloat(0).toFixed(2)}}</td>
        </tr>
        </tbody>
      </table>
      <div style="line-height: 50px">订单操作日志</div>
      <table cellspacing="0" cellpadding="0" border="0" class="el-table"
             style="margin-top: 5px; border: 1px solid #EBEEF5;" width="100%">
        <thead>
        <tr>
          <th class="cell is-leaf">操作类型</th>
          <th class="cell is-leaf">操作人</th>
          <th class="cell is-leaf">操作时间</th>
          <th class="cell is-leaf">操作原因</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="oper in order.orderOperatonLogs">
          <td class="cell">{{operType[oper.type]}}</td>
          <td class="cell">{{oper.operationName}}</td>
          <td class="cell">{{oper.createTime}}</td>
          <td class="cell">{{oper.remark?oper.remark:''}}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div style="text-align: center; margin-top: 20px">
      <el-button type="info" icon="el-icon-back" @click="goBack">返回订单列表</el-button>
      <el-button type="primary" icon="el-icon-printer" v-print="'#print_box'">打印</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    props: ["url"],
    data() {
      return {
        invoiceContentMap: {
          "1": "商品明细",
          "2": "商品类别"
        }, // 发票明细
        operType: {
          '1': '确认付款',
          '2': '修改金额',
          '3': '发货',
          '4': '取消订单',
          '5': '修改物流单号',
          '6': '核销虚拟商品订单'
        },// 操作类型
        invoiceTitleType: {'1': '企业', '2': '个人'}, // 发票抬头类型
        order: {
          freightPrice: 0,
          modifyPrice: 0,
          concessionalRate: 0,
          pointPrice: 0,
          couponPrice: 0,
          redEnvelopePrice: 0,
          originalPrice: 0,
          price: 0,
          orderAttr: {},
        }
      }
    },
    created() {
      this.queryDetail()
    },
    methods: {
      queryDetail() {
        this.url(this.$route.query.id).then(resp => {
        var res= resp.data
          res.redEnvelopePrice = res.redEnvelopePrice ? res.redEnvelopePrice : 0;
          res.modifyPrice = res.modifyPrice ? res.modifyPrice : 0;
          res.concessionalRate = res.concessionalRate ? res.concessionalRate : 0;
          this.order = res;
        })
      },
      // 返回
      goBack() {
        this.$router.back(-1);
      }
    },
    computed: {
      activeStatus: function () {
        if (this.order.status == '1') {
          return 1;
        } else if (this.order.status == '2') {
          return 2;
        } else if (this.order.status == '3') {
          return 3;
        } else {
          return 4;
        }
      },// 头部的激活状态
      // 订单状态
      orderStatus: function () {
        let msg = "";
        //判断状态
        if (this.order.status == '1') {
          msg = "未付款";
        } else if (this.order.status == '2') {
          msg = "已付款未发货";
        } else if (this.order.status == '3') {
          msg = "已发货";
        } else if (this.order.status == '4') {
          msg = "已完成";
        } else if (this.order.status == '5') {
          msg = "关闭交易";
        } else if (this.order.status == '6') {
          msg = "关闭交易";
        } else if (this.order.status == '7') {
          msg = "关闭交易";
        }
        if (this.order.status == '2' && this.order.orderType == '5' && this.order.lotteryStatus == '0') {
          msg = "待抽奖";
        }
        return msg;
      }
    }
  }
</script>
